package com.example.movieapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.ApiClient
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.pojo.DetailsResponse
import com.example.movieapp.pojo.ImagesResponse
import com.example.movieapp.pojo.Profile
import com.example.movieapp.ui.posters.PersonsViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime


class DetailsFragment : Fragment(), CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    lateinit var binding: FragmentDetailsBinding
    lateinit var navController: NavController
    lateinit var imagesAdapter: ImagesAdapter

    private val viewModel: DetailsViewModel by inject()

    val TAG = DetailsFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        imagesAdapter = ImagesAdapter(navController)

        val args = requireArguments()
        val id = if (!args.isEmpty) args.getString("id") else ""

        binding.loader.visibility = VISIBLE

        GlobalScope.launch {
            val time = measureTimeMillis {
                val getDetails = async {
                    viewModel.getDetails(binding,id!!)
                }
                val getImages = async {
                    viewModel.getImages(binding,id!!)
                }

                getDetails.await()
                getImages.await()
            }


            binding.loader.visibility = GONE
            Log.e(TAG, "onViewCreated: $time" )
        }


        setUI()

    }

    fun setUI(){
        viewModel.detailsData.observe(viewLifecycleOwner, {
            Picasso.get().load(ApiClient.IMAGE_URL_ORIGINAL+it.profile_path).into(binding.posterImg)
            binding.nameTv.text = it.name
            binding.titleTv.text = it.known_for_department
            binding.bioTv.text = it.biography
            binding.birthTv.text = it.birthday
            binding.placeBirthTv.text = it.place_of_birth

        })

        viewModel.images.observe(viewLifecycleOwner,{
            imagesAdapter.submitData(it.profiles as ArrayList<Profile>)
        })

        binding.imagesRv.layoutManager =
            GridLayoutManager(binding.root.context,3, RecyclerView.VERTICAL,false)

        binding.imagesRv.adapter = imagesAdapter

    }

}