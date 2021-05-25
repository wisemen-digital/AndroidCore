package be.appwise.core.networking.proxyman

class Configuration(
    var projectName: String,
    var deviceName: String
) {
    var id: String = "${Message.Project().bundleIdentifier}-${Message.Device().model}"
    var hostName: String = "${Message.Project().bundleIdentifier}-${Message.Device().model}"

    companion object {
        fun default() : Configuration {
            val project = Message.Project.current
            val deviceName = Message.Device.current
            return Configuration(projectName = project.name, deviceName = deviceName.name)
        }
    }



}