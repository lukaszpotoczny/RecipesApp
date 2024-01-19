package com.example.recipesapp.data.repo

import android.util.Log
import com.example.recipesapp.data.local.RecipeDao
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.domain.repo.RecipeRepository
import com.example.recipesapp.util.Constants.RECIPES_COLLECTION
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class RecipeRepositoryImpl(
    private val fireStore: FirebaseFirestore,
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override fun  getRecipes(): Task<QuerySnapshot> {
        return fireStore.collection(RECIPES_COLLECTION)
            .orderBy("title")
            .get()
    }

    override suspend fun updateRecipes(newList: List<Recipe>) {
        val newRecipes = newList.associateBy { it.id }
        val oldRecipes = recipeDao.getAllRecipes().associateBy { it.id }

        val toAdd = newRecipes.filter { !oldRecipes.containsKey(it.key) }.map { it.value }
        val toDelete = oldRecipes.filter { !newRecipes.containsKey(it.key) }.map { it.value }
        val toUpdate = newRecipes
            .filter { oldRecipes.containsKey(it.key) && it.value != oldRecipes.getValue(it.key) }
            .map { it.value }

        toAdd.forEach{Log.e("dupaAdd", it.id)}
        toDelete.forEach{Log.e("dupaDel", it.id)}
        toUpdate.forEach{Log.e("dupaUpd", it.id)}

        recipeDao.commit(
            deleted = toDelete,
            updated = toUpdate,
            added = toAdd
        )
    }
}