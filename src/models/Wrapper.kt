/*
 * Copyright (c) 2020
 * Kougioumtzi Chrysa - p3150078@aueb.gr
 * Filippou Dimitrios - p3160253@aueb.gr
 * Stergiou Nikolaos - p3120176@aueb.gr
 */

package models

import java.io.Serializable

class Wrapper<T> : Serializable {

    var data: T? = null

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

}