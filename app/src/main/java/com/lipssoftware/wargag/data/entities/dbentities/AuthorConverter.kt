package com.lipssoftware.wargag.data.entities.dbentities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.lipssoftware.wargag.data.entities.Author

class AuthorConverter {

    private val gson = Gson()

    @TypeConverter
    fun authorToJson(author: Author?): String?{
        return  gson.toJson(author)
    }

    @TypeConverter
    fun jsonToAuthor(json: String?): Author?{
        return gson.fromJson(json, Author::class.java)
    }
}