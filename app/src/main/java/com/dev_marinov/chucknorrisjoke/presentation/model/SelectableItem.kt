package com.dev_marinov.chucknorrisjoke.presentation.model

import com.dev_marinov.chucknorrisjoke.domain.Category

//data class SelectableCategory(
//    val name: String,
//    val isSelected: Boolean
//) {
//    fun mapToDomain(): Category = Category(name = this.name)
//}
//

data class SelectableItem<T>(
    val item: T,
    val isSelected: Boolean
){
    fun mapToDomain(): Category = Category(name = this.item.toString())
}