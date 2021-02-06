package com.example.movieapp.data

import com.example.movieapp.ui.details.DetailsViewModel
import com.example.movieapp.ui.posters.PersonsViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient
{
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    const val IMAGE_URL = "https://image.tmdb.org/t/p/w185"
    const val IMAGE_URL_ORIGINAL = "https://image.tmdb.org/t/p/original"

    var api_key = "522296e535c0220ec3451a85d1b50893"
    var category = "person/popular"

    private val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()


    val baseModule = module {
        single {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(AppServices::class.java)
        }
    }

    val postersVM = module { viewModel { PersonsViewModel(get()) } }
    val detailsVM = module { viewModel { DetailsViewModel(get()) } }

}