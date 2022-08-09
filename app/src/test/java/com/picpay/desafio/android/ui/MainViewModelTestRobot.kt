package com.picpay.desafio.android.ui

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.usecases.GetContactsUseCase
import com.picpay.desafio.android.data.usecases.GetLocalContactsUseCase
import com.picpay.desafio.android.data.usecases.UpdateLocalContactsUseCase
import com.picpay.desafio.android.getValueForTest
import io.mockk.*
import junit.framework.TestCase.assertEquals

object MainViewModelTestRobot {

    private lateinit var subject: MainViewModel

    private val getLocalContactsUseCase = mockk<GetLocalContactsUseCase>()
    private val getContactsUseCase = mockk<GetContactsUseCase>()
    private val updateLocalContactsUseCase = mockk<UpdateLocalContactsUseCase>()

    private val fakeContactsResponse = listOf(
        User(
            id = 1,
            name = "name",
            username = "userName",
            img = "image"
        )
    )
    private val fakeErrorResponse = Throwable()

    infix fun arrange(f: Arrange.() -> Unit) = Arrange().apply(f)

    infix fun act(f: Act.() -> Unit) = Act().apply(f)

    infix fun assert(f: Assert.() -> Unit) = Assert().apply(f)

    fun setup() {
        subject = MainViewModel(
            getContactsUseCase,
            getLocalContactsUseCase,
            updateLocalContactsUseCase
        )
    }


    class Arrange {

        fun mockGetLocalContactsWithSuccess() {
            coEvery { getLocalContactsUseCase() } returns fakeContactsResponse
        }

        fun mockGetContactsWithSuccess() {
            coEvery { getContactsUseCase() } returns fakeContactsResponse
        }

        fun mockGetLocalContactsWithFailure() {
            coEvery { getLocalContactsUseCase() } throws fakeErrorResponse
        }

        fun mockGetLocalContactsWithEmptyResponse() {
            coEvery { getLocalContactsUseCase() } returns emptyList()
        }

        fun mockGetContactsWithFailure() {
            coEvery { getContactsUseCase() } throws fakeErrorResponse
        }

        fun mockUpdateLocalContacts() {
            coEvery { updateLocalContactsUseCase(any()) } just Runs
        }
    }

    class Act {

        fun callGetUsers() {
            subject.getStoredUsers()
        }
    }

    class Assert {

        fun verifyShowUsersViewState() {
            assertEquals(
                MainActivityViewState.ShowUsers(fakeContactsResponse),
                subject.viewState.getValueForTest()
            )
        }

        fun verifyGetAndUpdateContactsCall() {
            coVerifyOrder {
                getContactsUseCase.invoke()
                updateLocalContactsUseCase.invoke(fakeContactsResponse)

            }
        }

        fun verifyErrorViewState() {
            assertEquals(
                MainActivityViewState.Error(fakeErrorResponse),
                subject.viewState.getValueForTest()
            )
        }
    }
}