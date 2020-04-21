/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models.packets

import aliases.fup
import interfaces.Packet
import java.io.Serializable

/**
 * The follow user packet, bi-directionally used between client & server
 * @since 0.0.4
 */
class FollowUserPacket : Serializable, Packet {

    data class FollowUserPayload(val sender: Any, val uid: Int? = null, var success: Boolean? = false) : Serializable

    override var payload: Any? = null

    override fun toString(): String {
        payload = payload as fup
        return "FollowUserPacket: " +
                "sender=${(payload as fup).sender} " +
                "uid=${(payload as fup).uid} " +
                "success=${(payload as fup).success}"
    }

}