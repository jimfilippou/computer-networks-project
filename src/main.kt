/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

import handlers.ClientHandler
import handlers.ServerHandler
import helpers.fetchClients
import models.Client
import models.Server

object Main {

    @JvmStatic
    fun main(args: Array<String>) {

        // $ ~ ifconfig | grep "inet " | grep -v 127.0.0.1 | awk '{print $2}'
        val ip = "192.168.1.148"
        val port = 9090

        when (args[0]) {
            "server" -> {
                val serverInfo = Server(ip, port);
                val server = ServerHandler(serverInfo);
                server.start()
            }
            "clients" -> {
                val clients: List<Client> = fetchClients();
                for (client in clients) {
                    val clientInfo = Client()
                    val service = ClientHandler(clientInfo, server = Server(ip, port));
                    service.start()
                }
            }
        }


    }

}
