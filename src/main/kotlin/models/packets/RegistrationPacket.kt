/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: RegistrationPacket.kt
 */

package models.packets

import aliases.rp
import interfaces.Packet
import java.io.Serializable

/**
 * The registration packet
 *
 * @since 0.0.4
 */
class RegistrationPacket: Serializable, Packet {

    data class RegistrationPayload(val sender: Any, val id: Int = -1) : Serializable

    override var payload: Any? = null
    override var response: Any? = null

    override fun toString(): String {
        return "RegistrationPacket(sender=${(payload as rp).sender}, id=${(payload as rp).id})"
    }

}