/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package helpers

import interfaces.Packet
import models.Client
import models.Server
import java.io.ObjectOutputStream
import java.lang.Exception
import java.net.ConnectException
import java.net.InetAddress
import java.net.Socket

fun sendToServer(payload: Packet, sender: Client, receiver: Server): Unit {

    try {
        val requestSocket = Socket(InetAddress.getByName(receiver.ip), receiver.port)
        val out: ObjectOutputStream
        out = ObjectOutputStream(requestSocket.getOutputStream())
        Logger.debug("Sending $payload to $receiver.")
        out.writeUnshared(payload)
        out.flush()
    } catch (err: Exception) {
        when (err) {
            is ConnectException -> Logger.error("$sender tried to connect to $receiver but connection was refused.")
            else -> Logger.error("Something bad happened :/")
        }
    }

}