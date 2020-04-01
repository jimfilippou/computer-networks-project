/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

class Server {

    var port: Int
    var ip: String
    private var counter: Int = 0

    constructor(ip: String, port: Int) {
        this.ip = ip;
        this.port = port;
    }

    override fun toString(): String {
        return "Server(port=$port, ip='$ip', counter=$counter)"
    }


}