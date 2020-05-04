package com.kidx.kidvoo.network.response


data class GetCategoriesResponse(
    val responseStatus: ResponseStatus,
    val responses: List<CategoryResponseItem>
)

data class CategoryResponseItem(val categoryName: String,
                                val categoryCode: String)