/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: SendToServer.kt
 */

package helpers

import aliases.*
import interfaces.Packet
import models.*
import models.packets.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ConnectException
import java.net.InetAddress
import java.net.Socket

/**
 * Universal packet sender, used by each client
 *
 * @param payload the object to send
 * @param sender who sends the payload object
 * @param receiver who receives the payload object
 * @since 0.0.4
 */
fun sendToServer(
        payload: Packet,
        sender: Client,
        receiver: Server,
        callback: ((data: Any?) -> Unit)? = null
): Unit {

    try {
        val requestSocket = Socket(InetAddress.getByName(receiver.ip), receiver.port)
        val outgoing: ObjectOutputStream
        val incoming: ObjectInputStream
        outgoing = ObjectOutputStream(requestSocket.getOutputStream())
        incoming = ObjectInputStream(requestSocket.getInputStream())
        Logger.debug("Sending $payload to $receiver.")
        outgoing.writeUnshared(payload)
        outgoing.flush()
        Thread.sleep(300)
        when (val response = incoming.readObject()) {
            is RegistrationPacket -> {
                sender.id = (response.payload as rp).id
                Logger.debug(
                        "Client ID is set to ${(response.payload as rp).id} from ${(response.payload as rp).sender}"
                )
                callback?.invoke(null)
            }
            is FollowUserPacket -> {
                if ((response.payload as fup).success == true) {
                    callback?.invoke(true)
                    return
                }
                callback?.invoke(false)
            }
            is ListUsersPacket -> {
                callback?.invoke((response.payload as lup).userIDs)
            }
            is GetFollowRequestsPacket -> {
                callback?.invoke((response.payload as gfrp).r)
            }
            is RejectFollowRequestPacket -> {
                callback?.invoke((response.payload as rfrp).success)
            }
            is AcceptFollowRequestPacket -> {
                callback?.invoke(response.response)
            }
            else -> print(response)
        }
    } catch (err: Exception) {
        when (err) {
            is ConnectException -> Logger.error("$sender tried to connect to $receiver but connection was refused.")
            else -> err.message?.let { Logger.error(it) }
        }
    }

}