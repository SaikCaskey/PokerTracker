package com.github.saikcaskey.pokertracker.presentation.venue

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.components.InsertVenueComponent
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

class DefaultInsertVenueComponent(
    private val componentContext: ComponentContext,
    private val existingVenueId: Long? = null,
    private val venueRepository: VenueRepository,
    private val dispatchers: CoroutineDispatchers,
    private val onFinished: () -> Unit,
) : InsertVenueComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val _inputData = MutableStateFlow(InsertVenueComponent.InputData())

    override val uiState: StateFlow<InsertVenueComponent.UiState> = _inputData.map { inputData ->
        InsertVenueComponent.UiState(
            existingVenueId = existingVenueId,
            inputData = inputData,
            isSubmitEnabled = inputData.name.isNotBlank()

        )
    }.stateIn(coroutineScope, Eagerly, InsertVenueComponent.UiState(existingVenueId = existingVenueId))

    init {
        coroutineScope.launch {
            existingVenueId?.let {
                venueRepository.getById(existingVenueId).collect { expense ->
                    _inputData.update {
                        InsertVenueComponent.InputData(
                            name = expense.name,
                            address = expense.address.orEmpty(),
                            description = expense.description.orEmpty(),
                        )
                    }
                }
            }
        }
    }

    override fun onNameChanged(name: String) {
        _inputData.update { it.copy(name = name) }
    }

    override fun onAddressChanged(address: String) {
        _inputData.update { it.copy(address = address) }
    }

    override fun onDescriptionChanged(description: String) {
        _inputData.update { it.copy(description = description) }
    }

    override fun onBackClicked() {
        onFinished()
    }

    override fun onSubmitClicked() {
        val uiState = uiState.value
        coroutineScope.launch {
            runCatching {
                if (existingVenueId != null) {
                    venueRepository.update(
                        venueId = existingVenueId,
                        name = uiState.inputData.name,
                        address = uiState.inputData.address,
                        description = uiState.inputData.description
                    )
                } else {
                    venueRepository.insert(
                        name = uiState.inputData.name,
                        address = uiState.inputData.address,
                        description = uiState.inputData.description
                    )
                }
            }.onSuccess { withContext(dispatchers.main) { onFinished() } }
        }
    }
}
