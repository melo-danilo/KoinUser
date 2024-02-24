package com.dracco.koinusergithub.di

import com.dracco.koinusergithub.api.service.SearchService
import com.dracco.koinusergithub.api.service.UserService
import com.dracco.koinusergithub.api.service.useCase.SearchServiceImpl
import com.dracco.koinusergithub.api.service.useCase.UserServiceImpl
import com.dracco.koinusergithub.repository.SearchRepository
import com.dracco.koinusergithub.repository.UserRepository
import com.dracco.koinusergithub.utils.Constants
import com.dracco.koinusergithub.viewModels.SearchViewModel
import com.dracco.koinusergithub.viewModels.UserViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIMEOUT = 30 * 1000

val netWorkModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .header("accept", "application/json")
                .build()
            chain.proceed(newRequest)
        }.connectTimeout(
            CONNECTION_TIMEOUT.toLong(),
            TimeUnit.MINUTES
        ).readTimeout(1, TimeUnit.MINUTES).build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

}
val databaseModule = module {

}
val serviceModule = module{
    single {
        get<Retrofit>(Retrofit::class).create(UserService::class.java)
    }
    single {
        get<Retrofit>(Retrofit::class).create(SearchService::class.java)
    }
}
val useCase = module {
    single {
        UserServiceImpl(get())
    }
    single {
        SearchServiceImpl(get())
    }

}
val repositoryModule = module{

    single<CoroutineDispatcher> { Dispatchers.Default }

    factory {
        UserRepository(get(), get())
    }
    factory {
        SearchRepository(get(), get())
    }
}
val viewModelModule = module{
    viewModel {
        UserViewModel(get(), get())
    }
    viewModel {
        SearchViewModel(get())
    }
}


val listModules = listOf(netWorkModule, useCase, databaseModule, serviceModule, repositoryModule, viewModelModule)