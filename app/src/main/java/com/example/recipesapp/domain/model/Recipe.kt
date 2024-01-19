package com.example.recipesapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
data class Recipe(
    @PrimaryKey val id: String,
    val title: String,
    val description: List<Description>,
    val summary: List<Summary>,
    val author: String?
)