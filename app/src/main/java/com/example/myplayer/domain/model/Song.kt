package com.example.myplayer.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val uri: String,
    val album: String,
    val duration: Long,
    val dateAdded: Long
) : Parcelable
