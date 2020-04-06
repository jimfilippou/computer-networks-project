/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 */

package models

import java.io.Serializable


class Wrapper<T> : Serializable {

    var data: T? = null

    companion object {
        private const val serialVersionUID = 578515438738407941L
    }

}