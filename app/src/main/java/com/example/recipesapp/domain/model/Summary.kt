package com.example.recipesapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Summary(
    val name: String? = null,
    val ingredients: List<String>? = null
): Parcelable
