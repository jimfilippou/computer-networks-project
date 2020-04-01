/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package handlers

import helpers.Logger
import models.Packet
import models.Server
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class ServerHandler(private val server: Server) : Thread() {

    override fun run() {
        val providerSocket: ServerSocket;
        var connection: Socket;
        val address: InetAddress;
        try {
            address = InetAddress.getByName(this.server.ip);
            providerSocket = ServerSocket(this.server.port, 50, address);
            Logger.info("$server -> started listening");

            while (true) {
                connection = providerSocket.accept();
                val out = ObjectOutputStream(connection.getOutputStream());
                val input = ObjectInputStream(connection.getInputStream());
                val incoming = input.readObject() as Packet;
                synchronized(this) { server.receivePacket(incoming) }
                input.close()
                out.close()
                connection.close()
            }

        } catch (err: Exception) {
            err.printStackTrace();
        }
    }

}