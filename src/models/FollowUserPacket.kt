/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import interfaces.Packet
import java.io.Serializable

class FollowUserPacket : Serializable, Packet {

    data class FollowUserPayload(val sender: Any, val uid: Int? = null, var success: Boolean? = false) : Serializable

    override var payload: Any? = null

}