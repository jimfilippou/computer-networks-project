/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package helpers

import models.Client
import models.Packet
import models.Server
import java.io.ObjectOutputStream
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
        System.err.println("$sender Tried to connect to -> $receiver But an error occurred.")
        err.printStackTrace()
    }

}