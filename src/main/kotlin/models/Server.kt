/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: Server.kt
 */

package models

import helpers.Logger
import java.io.*
import java.nio.channels.OverlappingFileLockException


/**
 * Server configuration & data object
 *
 * @since 0.0.1
 */
class Server(var ip: String, var port: Int) : Serializable {

    private var graph: File = File("")
    var slaves: Int = 0
    val maxSlaves: Int = 7
    var counter: Int = 0
    val registeredUsers: HashMap<Int, Client> = hashMapOf<Int, Client>()
    val posts: HashMap<Int, Post> = hashMapOf<Int, Post>()

    /**
     * Sets up the server storage files
     * @since 1.0.1
     */
    fun setup(): Unit {
        val storage = File("storage")
        if (storage.exists() && storage.isDirectory) {
            Logger.debug("Found old server storage files, deleting...")
            val children: Array<String> = storage.list()
            for (i in children.indices) {
                File(storage, children[i]).delete()
            }
            Logger.success("Storage was successfully deleted.")
        }
        this.prepareFiles()
    }

    /**
     * @since 1.0.1
     */
    private fun prepareFiles(): Unit {
        Logger.debug("Creating new storage files...")
        File("storage").mkdir()
        File("storage/server").mkdir()
        this.makeGraph()
    }

    /**
     * @since 1.2.0
     */
    private fun makeGraph(): Unit {
        try {
            this.graph = File("storage/server/graph.txt")
            if (this.graph.createNewFile()) {
                Logger.info("File created: " + this.graph.name)
            } else {
                Logger.info("File already exists, resetting")
                FileOutputStream(this.graph).close()
            }
        } catch (e: IOException) {
            Logger.error("An error occurred!")
            e.printStackTrace()
        }
    }

    /**
     * @since 1.2.0
     */
    fun insertUserToGraphWithLock(c: Client): Unit {
        try {
            val raf = RandomAccessFile(this.graph, "rw")
            val chan = raf.channel

            // Use the file channel to create a lock on the file.
            // This method blocks until it can retrieve the lock.
            var lock = chan.lock()

            // Try acquiring the lock without blocking. This method returns
            // null or throws an exception if the file is already locked.
            try {
                lock = chan.tryLock()
            } catch (e: OverlappingFileLockException) {
                // File is already locked in this thread or virtual machine
            }

            val fileLength = this.graph.length()
            raf.seek(fileLength)
            raf.write((c.id.toString() + "\n").toByteArray(Charsets.UTF_8))

            // Release the lock - if it is not null!
            lock?.release()

            // Close the file
            raf.close()
        } catch (e: Exception) {
        }

    }

    @Throws
    fun acceptedRequest(sender: Serializable, found: FollowRequest) {
        // Update in memory
        val user = this.registeredUsers[(sender as Client).id]
        val targ = this.registeredUsers[found.id]
        user?.following?.add(found.id as Int)
        this.registeredUsers[targ?.id]?.followers?.add(found.from.id)
        // Update in file
        // todo update the file!
    }

}