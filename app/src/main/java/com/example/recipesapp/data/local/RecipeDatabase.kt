package com.example.recipesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipesapp.domain.model.Recipe

@Database(entities = [Recipe::class], version = 3)
@TypeConverters(RecipeTypeConverters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract val recipeDao: RecipeDao

}