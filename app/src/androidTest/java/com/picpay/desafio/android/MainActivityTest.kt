package com.picpay.desafio.android

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.RecyclerViewMatchers.checkRecyclerViewItem
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.usecases.GetContactsUseCase
import com.picpay.desafio.android.data.usecases.GetLocalContactsUseCase
import com.picpay.desafio.android.data.usecases.UpdateLocalContactsUseCase
import com.picpay.desafio.android.ui.MainActivity
import com.picpay.desafio.android.ui.MainViewModel
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest : KoinTest {

    private val server = MockWebServer()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

//    private val updateLocalContactsUseCase = mockk<UpdateLocalContactsUseCase>()
//    private val getLocalContactsUseCase = mockk<GetLocalContactsUseCase>()
//    private val getContactsUseCase = mockk<GetContactsUseCase>()

    private lateinit var getContactsUseCase: GetContactsUseCase
    private lateinit var getLocalContactsUseCase: GetLocalContactsUseCase
    private lateinit var updateLocalContactsUseCase: UpdateLocalContactsUseCase

    private val fakeContactsResponse = listOf(
        User(
            id = 1,
            name = "name",
            username = "userName",
            img = "image"
        )
    )
//
//    @Rule
//    @JvmField
//    val rule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        getContactsUseCase = mock(GetContactsUseCase::class.java)
        getLocalContactsUseCase = mock(GetLocalContactsUseCase::class.java)
        updateLocalContactsUseCase = mock(UpdateLocalContactsUseCase::class.java)

//        MockitoAnnotations.initMocks(this)


        loadKoinModules(module {
            viewModel {
                MainViewModel(
                    getContactsUseCase,
                    getLocalContactsUseCase,
                    updateLocalContactsUseCase
                )
            }
        })
    }

    @After
    fun cleanUp() {
        stopKoin()
    }

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItem() {
//        coEvery {
//            getLocalContactsUseCase()
//        } returns fakeContactsResponse

        launchActivity<MainActivity>().apply {
            checkRecyclerViewItem(
                resId = R.id.recyclerView,
                position = 0,
                withMatcher = withText("userName")
            )
        }
    }

//    @Test
//    fun shouldDisplayListItem() {
//        server.dispatcher = object : Dispatcher() {
//            override fun dispatch(request: RecordedRequest): MockResponse {
//                return when (request.path) {
//                    "/users" -> successResponse
//                    else -> errorResponse
//                }
//            }
//        }
//
//        server.start(serverPort)
//
//        launchActivity<MainActivity>().apply {
//            // TODO("validate if list displays items returned by server")
//        }
//
//        server.close()
//    }

    companion object {
        private const val serverPort = 8080

        private val successResponse by lazy {
            val body =
                "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"

            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
    }
}