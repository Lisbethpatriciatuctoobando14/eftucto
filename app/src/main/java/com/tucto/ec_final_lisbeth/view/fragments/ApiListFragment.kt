package com.tucto.ec_final_lisbeth.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tucto.ec_final_lisbeth.view.fragments.ApiListFragmentDirections
import com.tucto.ec_final_lisbeth.view.fragments.ApiListViewModel
import com.tucto.ec_final_lisbeth.R
import com.tucto.ec_final_lisbeth.RVApiListAdapter
import com.tucto.ec_final_lisbeth.databinding.FragmentApiListBinding


class ApiListFragment : Fragment() {
    private lateinit var binding: FragmentApiListBinding
    private lateinit var viewModel: ApiListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ApiListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentApiListBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RVApiListAdapter(listOf()){api ->
            //navegar al detalle
            val apiDetailDirection = ApiListFragmentDirections.actionApiListFragmentToApiDetailFragment(api)
            findNavController().navigate(apiDetailDirection)
        }
        binding.rvApi.adapter = adapter
        binding.rvApi.layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        viewModel.cuponList.observe(requireActivity()){
            adapter.results = it
            adapter.notifyDataSetChanged()
        }
        viewModel.getCuponsFromService()
    }

}