/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import interfaces.Packet
import java.io.Serializable

class RegistrationPacket: Serializable, Packet {

    override var payload: Serializable? = null

    override fun toString(): String {
        return "Registration packet: \"${this.hashCode() % 1000}\""
    }

}