/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package helpers

import models.Client
import java.io.File
import java.util.*

/**
 * Reads data graph and returns clients along with their followers
 *
 * @param dataFile the file which contains the graph.
 * @throws NullPointerException
 * @since 0.0.1
 * @return A list of clients.
 */
fun fetchClients(dataFile: String = "/data/graph.txt"): List<Client> {

    var clients: MutableList<Client> = mutableListOf<Client>()
    val projectDir: String = File("").absolutePath

    val fileToRead: File = File(projectDir + dataFile)
    val scanner: Scanner = Scanner(fileToRead)

    while (scanner.hasNextLine()) {
        val data: String = scanner.nextLine()
        // Example input -> it: "1 3 4 5 45 75"
        val client: Client = Client()
        client.id = data[0].toInt()
        val iterable: String = data.substring(1)
        for (follower in iterable.trim().split(" ")) {
            client.follow(follower.toInt())
        }
        clients.add(client)
    }

    scanner.close()
    return clients

}