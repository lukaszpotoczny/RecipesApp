package com.example.recipesapp.di

import android.app.Application
import androidx.room.Room
import com.example.recipesapp.data.local.RecipeDao
import com.example.recipesapp.data.local.RecipeDatabase
import com.example.recipesapp.data.local.RecipeTypeConverters
import com.example.recipesapp.data.repo.DataRepositoryImpl
import com.example.recipesapp.domain.repo.DataRepository
import com.example.recipesapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = RecipeDatabase::class.java,
            name = Constants.RECIPE_DATABASE_NAME
        ).addTypeConverter(RecipeTypeConverters())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(recipeDB: RecipeDatabase): RecipeDao = recipeDB.recipeDao

    @Provides
    @Singleton
    fun providesDataRepo(recipeDao: RecipeDao): DataRepository = DataRepositoryImpl(recipeDao)
}