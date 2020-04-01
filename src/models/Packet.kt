/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import enums.PacketType
import interfaces.Packet
import java.io.Serializable

class Packet(override val type: PacketType) : Serializable, Packet {

    override var payload: Any? = null;

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

}