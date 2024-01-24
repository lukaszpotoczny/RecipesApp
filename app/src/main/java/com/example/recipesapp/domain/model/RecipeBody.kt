package com.example.recipesapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeBody(
    val title: String? = null,
    val summary: List<Summary>? = null,
    val description: List<Description>? = null,
    val author: String? = null
): Parcelable