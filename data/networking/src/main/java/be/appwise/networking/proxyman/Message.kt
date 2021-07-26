package be.appwise.networking.proxyman


/**
 * if requestbody is filled in succes ?
 * */
class Message(
    val id: String,
    val messageType: String,
    val buildVersion: String = "0.9.0",
    var content: Data?
) {



    companion object {
        fun buildConnectionMessage(id: String, item: ConnectionPackage): Message {
            return Message(id, "connection", content = item)
        }

        fun buildTrafficMessage(id: String, item: TrafficPackage): Message {
            return Message(id, "traffic", content = item)
        }
    }

}


