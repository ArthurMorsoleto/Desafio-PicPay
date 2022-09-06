package com.picpay.desafio.android.ui

import android.os.Build
import com.picpay.desafio.android.TestApplication
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Before
    fun setup() {
        MainActivityRobot.setup()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun shouldDisplayContactsList() {
        MainActivityRobot.apply {
            arrange {
                startScreen()
            }
            assert {
                checkContactsList()
                checkLoadingNotDisplayed()
            }
        }
    }

    @Test
    fun shouldDisplayLoading() {
        MainActivityRobot.apply {
            arrange {
                startScreenWithLoading()
            }
            assert {
                checkLoading()
            }
        }
    }
}
