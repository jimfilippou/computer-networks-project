/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package threads

import helpers.Logger
import models.Server
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

/**
 * The master thread of the application.
 *
 * This class handles connections and spawns proper threads for serving clients.
 *
 * @param server the server instance to operate on
 * @constructor Creates a new master thread which can be executed by the start() function.
 * @since 0.0.1
 */
class ServerConnectionReceiver(private val server: Server) : Thread() {

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
                    this.receivePacket(incoming, out)
                }
                input.close()
                connection.close()
            }

        } catch (err: Exception) {
            err.printStackTrace()
        }
    }

    /**
     * Spawn a thread for each request
     *
     * @param packet the packet received from the outside world
     * @param replyTo the stream which the thread will reply to
     * @since 0.0.2
     */
    private fun receivePacket(packet: Any, replyTo: ObjectOutputStream) {
        // TODO: Implement packet queue so that nothing gets lost
        if (server.slaves == 7) {
            Logger.info("Server threads have reached the limit, waiting 2 seconds...")
            Thread.sleep(2000)
            return //! LOSS OF PACKET
        }
        ServerThreadDistribution(this.server, packet, replyTo).start()
    }

}