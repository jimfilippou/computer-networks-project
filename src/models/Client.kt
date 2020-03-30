/*
 * Copyright (c) 2020
 * Kougioumtzi Chrysa - p3150078@aueb.gr
 * Filippou Dimitrios - p3160253@aueb.gr
 * Stergiou Nikolaos - p3120176@aueb.gr
 */

package models

class Client {

    private var _clientID: Int? = null;
    private val _followerIDs: MutableList<Int> = mutableListOf<Int>();

    constructor(id: Int) {
        this._clientID = id;
    }

    public fun addFollower(followerID: Int) {
        this._followerIDs.add(followerID);
    }

}