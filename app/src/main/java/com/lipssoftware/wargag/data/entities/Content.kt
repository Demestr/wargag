package com.lipssoftware.wargag.data.entities

data class Content<T> (
	val status : String,
	val meta : Meta,
	val data : List<T>
)