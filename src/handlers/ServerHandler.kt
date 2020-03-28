/*
 * Copyright (c) 2020
 * Kougioumtzi Chrysa - p3150078@aueb.gr
 * Filippou Dimitrios - p3160253@aueb.gr
 * Stergiou Nikolaos - p3120176@aueb.gr
 */

package handlers

import models.Server
import models.Wrapper
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
            println(this.server.toString() + " Server started");
            connection = providerSocket.accept();
            val out = ObjectOutputStream(connection.getOutputStream());
            val input = ObjectInputStream(connection.getInputStream());
            val incoming = input.readObject() as Wrapper<*>;
            println("$server Received -> ${incoming.data!!}");
            synchronized(this) {
//                if (incoming.data is Broker) {
//                    this.publisher.brokers.add(incoming.data as Broker)
//                }
            }
            input.close()
            out.close()
            connection.close()
        } catch (err: Exception) {
            err.printStackTrace();
        }
    }

}