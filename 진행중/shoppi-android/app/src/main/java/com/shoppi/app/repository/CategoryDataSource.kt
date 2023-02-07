package com.shoppi.app.repository

import com.shoppi.app.model.Category

interface CategoryDataSource {

    suspend fun getCategories(): List<Category>
}