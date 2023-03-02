package com.shoppi.app.repository.productdetail

import com.shoppi.app.model.Product

interface ProductDetailDataSource {

    suspend fun getProductDetail(productId: String): Product
}