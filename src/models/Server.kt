/*
 * Copyright (c) 2020
 * Kougioumtzi Chrysa - p3150078@aueb.gr
 * Filippou Dimitrios - p3160253@aueb.gr
 * Stergiou Nikolaos - p3120176@aueb.gr
 */

package models

class Server {

    var port: Int = 0;
    var ip: String = "";

    constructor(ip: String, port: Int){
        this.ip = ip;
        this.port = port;
    }

}