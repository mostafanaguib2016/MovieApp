package com.example.movieapp.ui.posters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentPersonsBinding
import com.example.movieapp.pojo.Result
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext


class PostersFragment : Fragment(), CoroutineScope {

    lateinit var binding: FragmentPersonsBinding
    lateinit var postersAdapter: PostersAdapter
    lateinit var navController: NavController

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private val viewModel: PersonsViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_persons, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        postersAdapter = PostersAdapter(navController)

        GlobalScope.launch {
            viewModel.getPosters(binding)
        }
        viewModel.result.observe(viewLifecycleOwner, {
            postersAdapter.submitData(it as ArrayList<Result>)
        })

        setUI()

    }

    fun setUI()
    {
        binding.postersRv.layoutManager =
            GridLayoutManager(binding.root.context,2,RecyclerView.VERTICAL,false)

        binding.postersRv.adapter = postersAdapter
    }

}