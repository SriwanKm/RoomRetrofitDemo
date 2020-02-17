package com.sammomichael.retroroomco.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sammomichael.retroroomco.dao.UserDao
import com.sammomichael.retroroomco.data.userdata.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    // abstract function for each of our Dao's
    abstract fun userDao(): UserDao

    // fancy Singleton Pattern for our Database don't memorize please!!!
    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "json.db")
            .build()
    }
}