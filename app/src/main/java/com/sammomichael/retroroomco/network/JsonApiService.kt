package com.sammomichael.retroroomco.network

import com.sammomichael.retroroomco.data.userdata.User
import io.reactivex.Observable
import retrofit2.http.GET

interface JsonApiService {
    @GET("/users")
    suspend fun getUsers(): List<User>

    @GET("/users")
    fun rxJavaUsers(): Observable<List<User>>
}