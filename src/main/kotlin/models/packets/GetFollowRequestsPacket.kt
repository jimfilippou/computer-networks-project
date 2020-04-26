package models.packets

import interfaces.Packet
import models.Client
import models.FollowRequest
import java.io.Serializable

class GetFollowRequestsPacket : Serializable, Packet {

    data class GetFollowRequestsPayload(val sender: Serializable, val r: MutableList<FollowRequest> = mutableListOf()) : Serializable

    override var payload: Any? = null

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

}