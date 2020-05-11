package models

import java.io.Serializable

data class Comment(val text: String, val approved: Boolean, val author: Client) : Serializable