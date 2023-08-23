package com.tucto.ec_final_lisbeth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tucto.ec_final_lisbeth.databinding.ItemApiBinding
import com.tucto.ec_final_lisbeth.model.Api

class RVApiListAdapter (var results: List<Api>,val onClick: (Api)->Unit): RecyclerView.Adapter<ApiVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiVH {
        val binding = ItemApiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApiVH(binding, onClick)
    }

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ApiVH, position: Int) {
        holder.bind(results[position])
    }


}

class ApiVH(private val binding: ItemApiBinding,val onClick: (Api)->Unit): RecyclerView.ViewHolder(binding.root) {
    fun bind(api: Api) {
        binding.txtNameStore.text = api.name
        binding.txtDetail.text = api.gender
        binding.txtExpiredOn.text = api.height
        binding.root.setOnClickListener {
            onClick(api)
        }
    }
}