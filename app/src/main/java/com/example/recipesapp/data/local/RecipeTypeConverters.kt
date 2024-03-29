package com.example.recipesapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.recipesapp.domain.model.Description
import com.example.recipesapp.domain.model.Summary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class RecipeTypeConverters {

    @TypeConverter
    fun summaryListToString(list: List<Summary>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToSummaryList(str: String): List<Summary> {
        val type = object : TypeToken<List<Summary>>() {}.type
        return Gson().fromJson(str, type)
    }

    @TypeConverter
    fun descListToString(list: List<Description>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToDescList(str: String): List<Description> {
        val type = object : TypeToken<List<Description>>() {}.type
        return Gson().fromJson(str, type)
    }
}