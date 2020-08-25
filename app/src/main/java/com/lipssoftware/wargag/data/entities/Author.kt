package com.lipssoftware.wargag.data.entities

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Author (
	val status : String,
	val status_image : String,
	val reputation : Int
) : Parcelable