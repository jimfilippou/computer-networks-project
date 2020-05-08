/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: UploadImagePacket.kt
 */

package models.packets

import interfaces.Packet
import models.Client
import models.Post
import java.io.Serializable

/**
 *
 * @since 0.0.3
 */
class UploadPostPacket : Serializable, Packet {

    data class UploadPostPayload(val sender: Client, val post: Post) : Serializable

    override var payload: Any? = null
    override var response: Any? = null

}

