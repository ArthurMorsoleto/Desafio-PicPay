package com.picpay.desafio.android.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

object CoroutinesModule {

    interface DefaultCoroutineScope : CoroutineScope

    fun provide() = module {
        factory { createDefaultCoroutineScope() }
    }

    private fun createDefaultCoroutineScope(
        job: Job = SupervisorJob()
    ): DefaultCoroutineScope = object : DefaultCoroutineScope {

        override val coroutineContext: CoroutineContext
            get() = job + Dispatchers.Default
    }
}