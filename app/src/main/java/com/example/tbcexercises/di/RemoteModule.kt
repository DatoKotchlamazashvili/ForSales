package com.example.tbcexercises.di

import com.example.tbcexercises.BuildConfig
import com.example.tbcexercises.data.remote.service.CartProductService
import com.example.tbcexercises.data.remote.service.CategoryService
import com.example.tbcexercises.data.remote.service.CompanyService
import com.example.tbcexercises.data.remote.service.DetailProductService
import com.example.tbcexercises.data.remote.service.FavouriteProductService
import com.example.tbcexercises.data.remote.service.HomeProductService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideJsonSerialization(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(logger).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json, client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL).apply {
                if (BuildConfig.DEBUG){
                    client(client)
                }
            }
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit
    }

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): HomeProductService {
        return retrofit.create(HomeProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideFavouriteProductService(retrofit: Retrofit): FavouriteProductService {
        return retrofit.create(FavouriteProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideCompanyService(retrofit: Retrofit): CompanyService {
        return retrofit.create(CompanyService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartProductService(retrofit: Retrofit): CartProductService {
        return retrofit.create(CartProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailProductService(retrofit: Retrofit): DetailProductService {
        return retrofit.create(DetailProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

}