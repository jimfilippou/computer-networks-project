/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import interfaces.Packet
import java.io.Serializable

/**
 * The registration packet
 *
 * @since 0.0.3
 */
class RegistrationPacket: Serializable, Packet {

    override var payload: Any? = null

    override fun toString(): String {
        return "Registration packet: \"${this.hashCode() % 1000}\""
    }

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

}