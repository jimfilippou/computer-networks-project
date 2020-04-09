/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package helpers

import aliases.fup
import aliases.lup
import aliases.rp
import interfaces.Packet
import models.*
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
                }
                callback?.invoke(false)
            }
            is ListUsersPacket -> {
                callback?.invoke((response.payload as lup).userIDs)
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