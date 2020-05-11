/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: PacketFactory.kt
 */

package models.packets

import interfaces.Packet
import models.Client
import java.io.Serializable

class ShowPostOfXPacket : Serializable, Packet {

    data class ShowPostOfXPacketPayload(val sender: Client, val id: Int) : Serializable

    override var payload: Any? = null
    override var response: Any? = null

}