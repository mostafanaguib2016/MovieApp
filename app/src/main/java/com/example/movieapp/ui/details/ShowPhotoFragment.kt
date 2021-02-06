package com.example.movieapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.data.ApiClient
import com.example.movieapp.databinding.FragmentShowPhotoBinding
import com.squareup.picasso.Picasso


class ShowPhotoFragment : Fragment() {

    lateinit var binding: FragmentShowPhotoBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_show_photo, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)

        val args = requireArguments()
        val image = if (!args.isEmpty) args.getString("image") else ""

        Picasso.get().load(ApiClient.IMAGE_URL_ORIGINAL + image).into(binding.posterImg)
        binding.cancelButton.setOnClickListener {
            navController.popBackStack()
        }

    }

}