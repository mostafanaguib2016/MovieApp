package com.example.movieapp.ui.posters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.ApiClient
import com.example.movieapp.databinding.ItemPosterBinding
import com.example.movieapp.pojo.PersonsResponse
import com.example.movieapp.pojo.Result
import com.squareup.picasso.Picasso

class PostersAdapter(var navController: NavController): RecyclerView.Adapter<PostersAdapter.ViewHolder>()
{
    var list = ArrayList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_poster,
                parent,false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position],navController)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(var binding: ItemPosterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Result, navController: NavController) {

            Picasso.get().load(ApiClient.IMAGE_URL + item.profile_path).into(binding.posterImg)
            binding.nameTv.text = item.name
            binding.titleTv.text = item.known_for_department

            binding.root.setOnClickListener {
                val id = item.id.toString()
                val bundle = Bundle()
                bundle.putString("id",id)
                navController.navigate(R.id.detailsFragment,bundle)
            }

            binding.executePendingBindings()
        }

    }

    fun submitData(d: ArrayList<Result>) {
        this.list = d
        notifyDataSetChanged()
    }

}