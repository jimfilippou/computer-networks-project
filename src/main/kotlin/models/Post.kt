/*
 * Copyright (c) 2020
 * Dimitrios Filippou・p3160253@aueb.gr
 * File: Post.kt
 */

package models

import enums.PostType
import java.io.Serializable

class Post(
        val author: Client,
        val type: PostType,
        val image: String?,
        val text: String,
        val comments: MutableList<Comment>? = mutableListOf<Comment>()
) : Serializable {

}