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
    fun testOnViewReadyOnSuccess() = runBlockingTest {
        MainViewModelTestRobot.apply {
            arrange {
                mockGetContactsWithSuccess()
                mockUpdateLocalContacts()
            }
            act {
                callOnViewReady()
            }
            assert {
                verifyGetAndUpdateContactsCall()
                verifyShowUsersViewState()
            }
        }
    }

    @Test
    fun testOnViewReadyOnFailure() = runBlockingTest {
        MainViewModelTestRobot.apply {
            arrange {
                mockGetContactsWithFailure()
            }
            act {
                callOnViewReady()
            }
            assert {
                verifyErrorViewState()
            }
        }
    }
}
