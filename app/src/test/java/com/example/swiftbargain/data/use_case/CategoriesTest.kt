package com.example.swiftbargain.data.use_case

import com.example.swiftbargain.data.models.CategoryDto
import com.example.swiftbargain.data.repository.FakeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CategoriesTest {

    private lateinit var fakeRepository: FakeRepository
    private lateinit var categories: MutableList<CategoryDto>

    @Before
    fun init() {
        fakeRepository = FakeRepository()
        categories = mutableListOf()
        for (i in 'a'..'g') {
            categories.add(
                CategoryDto(
                    id = i.toString(),
                    url = "image $i",
                    label = "label $i",
                )
            )
        }
        categories.shuffled()
        fakeRepository.categories = categories
    }

    @Test
    fun `get categories, is not empty`() {
        runBlocking {
            val categories = fakeRepository.getAllCategories()
            assertThat(categories).isNotEmpty()
        }
    }

    @Test
    fun `get categories sort by label, ascending order`() {
        runBlocking {
            val categories = fakeRepository.getAllCategories().sortedBy { it.label }
            this@CategoriesTest.categories.sortedBy { it.label }
            assertThat(categories).isEqualTo(this@CategoriesTest.categories)
        }
    }

}
