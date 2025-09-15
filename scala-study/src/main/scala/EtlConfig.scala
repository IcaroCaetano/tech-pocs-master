import pureconfig.*
import pureconfig.generic.derivation.EnumConfigReader

enum EtlImpl derives EnumConfigReader:
  case StringImpl, IntImpl, JsonImpl

final case class EtlConfig(
    inputFilePath: String,
    outputFilePath: String,
    etlImpl: EtlImpl
) derives ConfigReader
