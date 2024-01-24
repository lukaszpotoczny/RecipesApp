package com.example.recipesapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Description(
    val name: String? = null,
    val steps: List<String>? = null
) : Parcelable