/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package handlers

import helpers.Logger
import models.Server
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class ServerConnectionHandler(private val server: Server) : Thread() {

    override fun run() {
        val providerSocket: ServerSocket
        var connection: Socket
        val address: InetAddress
        try {
            address = InetAddress.getByName(this.server.ip)
            providerSocket = ServerSocket(this.server.port, 50, address)
            Logger.info("$server -> started listening on ${server.ip}:${server.port}")

            while (true) {
                connection = providerSocket.accept()
                val out = ObjectOutputStream(connection.getOutputStream())
                val input = ObjectInputStream(connection.getInputStream())
                val incoming = input.readObject()
                synchronized(this) {
                    server.receivePacket(incoming, out)
                }
                input.close()
                connection.close()
            }

        } catch (err: Exception) {
            err.printStackTrace()
        }
    }

}