package com.lipssoftware.wargag.data.entities

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Comment(
    val comment : String?,
    val account_id : Int,
    val author : Author?,
    val created_at : Long,
    val comment_id : Int,
    val content_id : Int,
    val nickname : String
) : Parcelable