package models.packets

import interfaces.Packet
import models.Client
import models.Post
import java.io.Serializable

class CreatePostPacket : Serializable, Packet {

    data class CreatePostPacketPayload(val client: Client, val post: Post)

    override var payload: Any? = null
    override var response: Any? = null

}