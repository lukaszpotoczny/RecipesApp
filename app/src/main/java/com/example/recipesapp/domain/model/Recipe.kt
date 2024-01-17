package com.example.recipesapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    val summary: List<Summary>
): Parcelable