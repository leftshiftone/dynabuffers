package dynabuffers.header

import dynabuffers.exception.DynabuffersException

// This file is autogenerated from DynabuffersVersion.vm

class DynabuffersVersion(private val version: String) {

    companion object {
        val current = DynabuffersVersion("1.2.0-SNAPSHOT")
    }

    fun getMajor(): Byte {
        val major = version.split('.')[0]
        try {
            return major.toByte()
        } catch (nfe: NumberFormatException) {
            throw DynabuffersException("Dynabuffers major version cannot be extracted from: $version.\nReason: $nfe")
        }
    }
}