/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import enums.PacketType
import interfaces.Packet
import java.io.Serializable

class RegistrationPacket: Serializable, Packet {

    override var payload: Serializable? = null;

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

    override fun toString(): String {
        return "Registration packet: \"${this.hashCode() % 1000}\""
    }

}