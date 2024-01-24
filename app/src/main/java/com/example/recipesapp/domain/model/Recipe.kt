package com.example.recipesapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Recipe(
    @PrimaryKey val id: String,
    val title: String,
    val summary: List<Summary>,
    val description: List<Description>,
    val author: String?,
    val isFavorite: Boolean
): Parcelable