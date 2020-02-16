package com.sammomichael.retroroomco.data.userdata


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class User(
    @PrimaryKey(autoGenerate = false) var id: Int? = 0,
    var body: String? = "",
    var email: String? = "",
    var name: String? = "",
    var postId: Int? = 0
)