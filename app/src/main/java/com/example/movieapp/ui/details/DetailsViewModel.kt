package com.example.movieapp.ui.details

import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.ApiClient
import com.example.movieapp.data.AppServices
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.pojo.DetailsResponse
import com.example.movieapp.pojo.ImagesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DetailsViewModel(val appServices: AppServices): ViewModel()
{
    val TAG = DetailsViewModel::class.java.simpleName
    val detailsData = MutableLiveData<DetailsResponse>()
    val images = MutableLiveData<ImagesResponse>()

    suspend fun getDetails(binding: FragmentDetailsBinding,id: String){
        delay(2000)
        val context = binding.root.context
        withContext(CoroutineScope(Dispatchers.Main).coroutineContext){
            kotlin.runCatching {
                appServices.getDetails(id,ApiClient.api_key)
            }.onSuccess {
                val data = it.body()!!
                detailsData.value = data

            }.onFailure {
                Log.e(TAG, "getDetails: ", it )
            }
        }
    }

    suspend fun getImages(binding: FragmentDetailsBinding,id : String){
        delay(1000)
        val context = binding.root.context


        withContext(CoroutineScope(Dispatchers.Main).coroutineContext){
            kotlin.runCatching {
                appServices.getImages(id,ApiClient.api_key)
            }.onSuccess {
                val data = it.body()!!
                images.value = data

            }.onFailure {
                Log.e(TAG, "getDetails: ", it )
            }

        }

    }

}