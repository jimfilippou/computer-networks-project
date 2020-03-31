/*
 * Copyright (c) 2020
 * Kougioumtzi Chrysa - p3150078@aueb.gr
 * Filippou Dimitrios - p3160253@aueb.gr
 * Stergiou Nikolaos - p3120176@aueb.gr
 */

import helpers.fetchClients
import models.Client

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
//
//        val serverInfo = Server("192.168.1.148", 8080);
//        val server = ServerHandler(serverInfo);
//
//        val clientInfo = Client()
//        val client = ClientHandler(clientInfo);
//
//        server.start()

        val clients: List<Client> = fetchClients();

        print(clients)

    }

}
