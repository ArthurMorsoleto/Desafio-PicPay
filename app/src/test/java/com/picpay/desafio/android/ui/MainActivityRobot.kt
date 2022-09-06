package com.picpay.desafio.android.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.usecases.GetContactsUseCase
import com.picpay.desafio.android.data.usecases.UpdateLocalContactsUseCase
import com.picpay.desafio.android.ui.MainActivityRobot.Assert.RecyclerViewMatchers.atPosition
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.delay
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object MainActivityRobot {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var activity: MainActivity

    private val updateLocalContactsUseCase = mockk<UpdateLocalContactsUseCase>()
    private val getContactsUseCase = mockk<GetContactsUseCase>()

    infix fun arrange(f: Arrange.() -> Unit) = Arrange().apply(f)

    infix fun act(f: Act.() -> Unit) = Act().apply(f)

    infix fun assert(f: Assert.() -> Unit) = Assert().apply(f)

    fun setup() {
        loadKoinModules(module {
            viewModel {
                MainViewModel(
                    getContactsUseCase,
                    updateLocalContactsUseCase
                )
            }
        })
    }

    class Arrange {
        fun startScreen() {
            mockContactsList()
            initActivityScenario()
        }

        fun startScreenWithLoading() {
            mockContactsListForLoading()
            initActivityScenario()
        }

        private fun initActivityScenario() {
            scenario = ActivityScenario.launch(MainActivity::class.java)
                .onActivity {
                    activity = it
                }
        }

        private fun mockContactsList() {
            coEvery {
                getContactsUseCase.invoke()
            } returns listOf(
                User(
                    id = 1,
                    name = "name",
                    username = "userName",
                    img = "image"
                )
            )
            coEvery {
                updateLocalContactsUseCase.invoke(any())
            } just Runs
        }

        private fun mockContactsListForLoading() {
            coEvery { getContactsUseCase.invoke() } coAnswers {
                delay(10000)
                listOf(
                    User(
                        id = 1,
                        name = "name",
                        username = "userName",
                        img = "image"
                    )
                )
            }
        }
    }

    class Act

    class Assert {
        fun checkContactsList() {
            onView(withId(R.id.recyclerView))
                .check(matches(atPosition(0, hasDescendant(withText("name")))))
        }

        fun checkLoading() {
            onView(withId(R.id.user_list_progress_bar)).check(matches(isDisplayed()))
        }

        fun checkLoadingNotDisplayed() {
            onView(withId(R.id.user_list_progress_bar)).check(matches(not(isDisplayed())))
        }

        object RecyclerViewMatchers {

            fun atPosition(
                position: Int,
                itemMatcher: Matcher<View>
            ) = object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("has item at position $position: ")
                    itemMatcher.describeTo(description)
                }

                override fun matchesSafely(item: RecyclerView?): Boolean {
                    val viewHolder =
                        item?.findViewHolderForAdapterPosition(position) ?: return false
                    return itemMatcher.matches(viewHolder.itemView)
                }
            }

            fun checkRecyclerViewItem(resId: Int, position: Int, withMatcher: Matcher<View>) {
                Espresso.onView(ViewMatchers.withId(resId)).check(
                    ViewAssertions.matches(
                        atPosition(
                            position,
                            ViewMatchers.hasDescendant(withMatcher)
                        )
                    )
                )
            }
        }
    }
}