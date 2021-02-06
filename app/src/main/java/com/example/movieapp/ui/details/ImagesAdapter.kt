package com.example.movieapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.ApiClient
import com.example.movieapp.databinding.ItemImageBinding
import com.example.movieapp.pojo.ImagesResponse
import com.example.movieapp.pojo.Profile
import com.example.movieapp.pojo.Result
import com.squareup.picasso.Picasso

class ImagesAdapter(val navController: NavController): RecyclerView.Adapter<ImagesAdapter.ViewHolder>()
{
    var list = ArrayList<Profile>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_image,
                parent,false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position],navController)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(var binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Profile, navController: NavController){

            Picasso.get().load(ApiClient.IMAGE_URL+item.file_path).into(binding.posterImg)

            binding.root.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("image",item.file_path)
                navController.navigate(R.id.showPhotoFragment,bundle)

            }

        }
    }

    fun submitData(d: ArrayList<Profile>) {
        this.list = d
        notifyDataSetChanged()
    }

}