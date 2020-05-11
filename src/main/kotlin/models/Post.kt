/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: Post.kt
 */

package models

import enums.PostType
import java.io.Serializable
import java.util.*

data class Post(
        val author: Client,
        val type: PostType,
        val image: String?,
        val text: String,
        val comments: MutableList<Comment>? = mutableListOf<Comment>(),
        var id: Number = (1..Int.MAX_VALUE).random()
) : Serializable {

    private var calendar: Calendar = Calendar.getInstance()
    val created: Date

    init {
        this.created = this.calendar.time
    }

    override fun toString(): String {
        return "Post(type=$type, text='$text', id=$id)"
    }

}