/*
 * Copyright (c) 2020
 * Kougioumtzi Chrysa - p3150078@aueb.gr
 * Filippou Dimitrios - p3160253@aueb.gr
 * Stergiou Nikolaos - p3120176@aueb.gr
 */

package helpers

import models.Client
import java.io.File
import java.util.*

/**
 * This class has no useful logic; it's just a documentation example.
 *
 * @param file the file which contains the graph.
 * @throws NullPointerException
 * @return A list of clients.
 */
fun FetchClients(dataFile: String = "/data/graph.txt"): List<Client> {

    var clients: MutableList<Client> = mutableListOf<Client>()
    val projectDir: String = File("").absolutePath;

    val fileToRead: File = File(projectDir + dataFile);
    val scanner: Scanner = Scanner(fileToRead);

    while (scanner.hasNextLine()) {
        val data: String = scanner.nextLine();
        // Example input -> it: "1 3 4 5 45 75"
        val client: Client = Client(data[0].toInt());
        val iterable: String = data.substring(1);
        for (follower in iterable.trim().split(" ")) {
            client.addFollower(follower.toInt());
        }
        clients.add(client);
    }

    scanner.close();
    return clients

}