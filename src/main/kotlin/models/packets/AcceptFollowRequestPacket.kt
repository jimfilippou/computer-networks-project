/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: RejectFollowRequest.kt
 */

package main.models.packets

import interfaces.Packet
import models.FollowRequest
import java.io.Serializable

class AcceptFollowRequestPacket : Serializable, Packet {

    data class AcceptFollowRequestPayload(val sender: Serializable, val requestID: Int?) : Serializable

    override var payload: Any? = null
    override var response: Any? = null

}