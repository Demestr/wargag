package com.lipssoftware.wargag.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.lipssoftware.wargag.data.entities.dbentities.DateConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "posts_table")
data class Post (
	@PrimaryKey val content_id : Int,
	val rating : Int,
	val category_id : String?,
	val allowed_to_vote : String?,
	val account_id : Int,
	val media_preview_url : String?,
	val author : Author?,
	val media_url : String?,
	val created_at : Long,
	val description : String?,
	val tag_id : Int,
	val wargag_url : String,
	val type : String,
	val nickname : String?,
	val subject : String?,
	var isFavorite: Boolean = false
) : Parcelable