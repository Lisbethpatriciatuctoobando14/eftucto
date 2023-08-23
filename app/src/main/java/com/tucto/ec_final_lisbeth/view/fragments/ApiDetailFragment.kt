package com.tucto.ec_final_lisbeth.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tucto.ec_final_lisbeth.view.fragments.ApiDetailFragmentArgs
import com.tucto.ec_final_lisbeth.R
import com.tucto.ec_final_lisbeth.databinding.FragmentApiDetailBinding
import com.tucto.ec_final_lisbeth.model.Api


class ApiDetailFragment : Fragment() {
    private lateinit var binding: FragmentApiDetailBinding
    val args: ApiDetailFragmentArgs by navArgs()
    private lateinit var api: Api
    private lateinit var viewModel: ApiDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api= args.api
        viewModel= ViewModelProvider(requireActivity()).get(ApiDetailViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentApiDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgStar.setImageResource(R.drawable.yo_soy_tu_padre)
        binding.txtNameStore.text = api.name
        binding.txtDetail.text = api.gender
        binding.txtExpiredOn.text = api.height
        binding.txtStar.text = api.mass
        binding.btnAddFavorite.apply {
            text = if (viewModel.isFavorite(api)) "Eliminar" else "Agregar favoritos"
        }
        binding.btnAddFavorite.setOnClickListener {
            if (!viewModel.isFavorite(api)) {
                api.isFavorite = true
                viewModel.addFavorite(api)
                binding.btnAddFavorite.text = "Eliminar"
            } else {
                api.isFavorite = false
                viewModel.removeFavorite(api)
                binding.btnAddFavorite.text = "Agregar a favoritos"
            }
        }
    }


}