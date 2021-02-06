package com.example.movieapp.ui.posters

import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.ApiClient
import com.example.movieapp.data.AppServices
import com.example.movieapp.databinding.FragmentPersonsBinding
import com.example.movieapp.pojo.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PersonsViewModel(val appServices: AppServices): ViewModel() {

    val TAG = PersonsViewModel::class.java.simpleName
    val result = MutableLiveData<List<Result>>()


    suspend fun getPosters(binding: FragmentPersonsBinding){

        val context = binding.root.context
        binding.loader.visibility = VISIBLE

        withContext(CoroutineScope(Dispatchers.Main).coroutineContext){
            kotlin.runCatching {
                appServices.getPersons(ApiClient.api_key)
            }.onSuccess {
                val data = it.body()!!

                result.value = data.results
                binding.loader.visibility = View.GONE

            }.onFailure {
                Log.e(TAG, "getPosters: ", it )
                binding.loader.visibility = View.GONE
            }
        }

    }

}