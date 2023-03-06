package com.shoppi.app.model

sealed class CartProduct
data class CartHeader(
    val brandName: String
): CartProduct()

data class CartItem(
    val productId: String,
    val label: String,
    val price: Int,
    val brandName: String,
    val thumbnailImageUrl: String,
    val type: String,
    val amount: Int
): CartProduct()