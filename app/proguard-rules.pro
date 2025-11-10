-printmapping build/outputs/mapping/release/mapping.txt
-printseeds build/outputs/mapping/release/seeds.txt
-printusage build/outputs/mapping/release/usage.txt
-printconfiguration build/outputs/mapping/release/configuration.txt

-keepattributes SourceFile, LineNumberTable, Signature, InnerClasses, EnclosingMethod

-keep class kotlin.Metadata { *; }

-keep class * implements org.koin.core.module.Module { *; }

-keepnames class * { @org.koin.core.annotation.Koin* *; }

-keepnames @kotlinx.serialization.Serializable class * { *; }
