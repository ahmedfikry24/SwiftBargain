package com.example.swiftbargain.data.use_case

import com.example.swiftbargain.data.repository.FakeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserUidTest {

    private lateinit var fakeRepository: FakeRepository
    private lateinit var userUid: String

    @Before
    fun init() {
        fakeRepository = FakeRepository()
        userUid = "123456"
        runBlocking {
            fakeRepository.setUserUid(userUid)
        }
    }

    @Test
    fun `get user uid from dataStore, return string`() {
        runBlocking {
            fakeRepository.getUserUid().collect {
                assertThat(it).isEqualTo(userUid)
            }

        }
    }

    @Test
    fun `set user uid from dataStore, return string`() {
        runBlocking {
            val newUid = "ahmed123456"
            fakeRepository.setUserUid(newUid)
            fakeRepository.getUserUid().collect {
                assertThat(it).isEqualTo(newUid)
            }
        }
    }
}
