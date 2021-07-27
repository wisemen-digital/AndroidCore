package be.appwise.core.networking.proxyman

class Configuration(
    var projectName: String,
    var deviceName: String
) {
    var id: String = "${Project.current.bundleIdentifier}-${Device.current.model}"
    var hostName: String = "${Project.current.bundleIdentifier}-${Device.current.model}"

    companion object {
        fun default() : Configuration {
            val project = Project.current
            val deviceName = Device.current
            return Configuration(projectName = project.name, deviceName = deviceName.name)
        }
    }
}