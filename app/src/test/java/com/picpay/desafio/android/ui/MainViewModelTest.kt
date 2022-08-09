package com.picpay.desafio.android.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        MainViewModelTestRobot.setup()
    }

    @Test
    fun testGetLocalUsersOnSuccess() = runBlockingTest {
        MainViewModelTestRobot.apply {
            arrange {
                mockGetLocalContactsWithSuccess()
            }
            act {
                callGetUsers()
            }
            assert {
                verifyShowUsersViewState()
            }
        }
    }

    @Test
    fun testGetLocalUsersOnFailure() = runBlockingTest {
        MainViewModelTestRobot.apply {
            arrange {
                mockGetLocalContactsWithFailure()
                mockGetContactsWithSuccess()
                mockUpdateLocalContacts()
            }
            act {
                callGetUsers()
            }
            assert {
                verifyShowUsersViewState()
                verifyGetAndUpdateContactsCall()
            }
        }
    }

    @Test
    fun testGetUsersOnSuccess() = runBlockingTest {
        MainViewModelTestRobot.apply {
            arrange {
                mockGetLocalContactsWithEmptyResponse()
                mockGetContactsWithSuccess()
                mockUpdateLocalContacts()
            }
            act {
                callGetUsers()
            }
            assert {
                verifyShowUsersViewState()
                verifyGetAndUpdateContactsCall()
            }
        }
    }

    @Test
    fun testGetUsersOnFailure() = runBlockingTest {
        MainViewModelTestRobot.apply {
            arrange {
                mockGetLocalContactsWithEmptyResponse()
                mockGetContactsWithFailure()
            }
            act {
                callGetUsers()
            }
            assert {
                verifyErrorViewState()
            }
        }
    }
}
