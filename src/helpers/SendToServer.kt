/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package helpers

import interfaces.Packet
import models.Client
import models.Server
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ConnectException
import java.net.InetAddress
import java.net.Socket

/**
 * Sends a packet to the server
 *
 * @param payload the object to send
 * @param sender who sends the payload object
 * @param receiver who receives the payload object
 */
fun sendToServer(payload: Packet, sender: Client, receiver: Server): Unit {

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
            is Int -> {
                sender.id = response
                Logger.debug("Client ID is set to $response from $receiver")
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