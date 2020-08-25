package com.lipssoftware.wargag.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lipssoftware.wargag.data.entities.Author
import com.lipssoftware.wargag.data.entities.Post
import com.lipssoftware.wargag.data.entities.dbentities.AuthorConverter
import com.lipssoftware.wargag.data.entities.dbentities.DateConverter

@Database(entities = [Post::class], version = 2, exportSchema = false)
@TypeConverters(AuthorConverter::class)
abstract class WargagDatabase : RoomDatabase() {

    abstract fun getDao(): WargagDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: WargagDatabase

        fun getInstance(context: Context) : WargagDatabase {
            synchronized(this){
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        WargagDatabase::class.java,
                        "wargag_database").build()
                }
            }
            return INSTANCE
        }
    }
}