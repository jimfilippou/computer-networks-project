/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: UploadImagePacket.kt
 */

package models.packets

import interfaces.Packet
import models.Client
import java.io.Serializable

/**
 *
 * @since 0.0.3
 */
class UploadImagePacket : Serializable, Packet {

    data class UploadImagePayload(val sender: Client, val image: String) : Serializable

    override var payload: Any? = null
    override var response: Any? = null

}

