package com.sammomichael.retroroomco.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sammomichael.retroroomco.data.userdata.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(vararg user: User)

    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>
}