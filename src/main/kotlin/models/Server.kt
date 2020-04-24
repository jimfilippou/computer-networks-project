/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: Server.kt
 */

package models

import helpers.Logger
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.io.Serializable
import java.nio.channels.OverlappingFileLockException


/**
 * Server configuration & data object
 *
 * @since 0.0.1
 */
class Server(var ip: String, var port: Int) : Serializable {

    private var graph: File? = null
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
     * @since 1.0.1
     */
    private fun makeGraph(): Unit {
        try {
            this.graph = File("storage/server/graph.txt")
            if (this.graph!!.createNewFile()) {
                Logger.info("File created: " + this.graph!!.name)
            } else {
                Logger.info("File already exists.")
            }
        } catch (e: IOException) {
            Logger.error("An error occurred!")
            e.printStackTrace()
        }
    }

    /**
     * @since 1.0.1
     * todo: fix an error where file is being overwritten
     */
    fun insertUserToGraphWithLock(c: Client): Unit {
        try {

            val chan = RandomAccessFile(this.graph, "rw")
            var lock = chan.channel.lock()

            try {
                lock = chan.channel.tryLock()
            } catch (e: OverlappingFileLockException) {
                // File is already locked in this thread or virtual machine
            }

            chan.writeBytes("${c.id}\n")

            lock?.release()
            chan.close()
        } catch (err: Error) {

        }
    }

    override fun toString(): String {
        return "The lonely server"
    }

}