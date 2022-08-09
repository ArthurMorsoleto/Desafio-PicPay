package com.picpay.desafio.android.data.usecases

import com.picpay.desafio.android.data.api.repository.ApiRepository
import com.picpay.desafio.android.data.model.User
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetContactsUseCaseTest {

    private lateinit var subject: GetContactsUseCase
    private val apiRepository = mockk<ApiRepository>()
    private val response = listOf(
        User(
            id = 1,
            name = "name",
            username = "userName",
            img = "image"
        )
    )

    @Before
    fun setup() {
        subject = GetContactsUseCaseImpl(apiRepository)
    }

    @Test
    fun testGetUsersFromRepository() {
        runBlockingTest {
            coEvery { apiRepository.getUsers() } returns response

            val result = subject.invoke()

            assertEquals(result, response)
        }
    }
}