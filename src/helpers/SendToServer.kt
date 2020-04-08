/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package helpers

import interfaces.Packet
import models.Client
import models.ListUsersPacket
import models.Server
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ConnectException
import java.net.InetAddress
import java.net.Socket

/**
 * Universal packet sender, used by clients
 *
 * @param payload the object to send
 * @param sender who sends the payload object
 * @param receiver who receives the payload object
 * @since 0.0.3
 * @return **[Unit]**
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
        Thread.sleep(1000)
        when (val response = incoming.readObject()) {
            // TODO: The following line is bad practice, but works, I should tell the server to send a proper object
            is Int -> {
                sender.id = response
                Logger.debug("Client ID is set to $response from $receiver")
            }
            is ListUsersPacket -> {
                callback?.invoke((response.payload as ListUsersPacket.ListUsersPayload).userIDs)
            }
            else -> return
        }
    } catch (err: Exception) {
        when (err) {
            is ConnectException -> Logger.error("$sender tried to connect to $receiver but connection was refused.")
            else -> Logger.error("Something bad happened :/")
        }
    }

}