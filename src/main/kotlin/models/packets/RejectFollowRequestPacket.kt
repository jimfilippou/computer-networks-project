/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: RejectFollowRequest.kt
 */

package models.packets

import interfaces.Packet
import java.io.Serializable

class RejectFollowRequestPacket : Serializable, Packet {

    data class RejectFollowRequestPayload(val sender: Serializable, val requestID: Int?, var success: Boolean = false) : Serializable

    override var payload: Any? = null
    override var response: Any? = null

}