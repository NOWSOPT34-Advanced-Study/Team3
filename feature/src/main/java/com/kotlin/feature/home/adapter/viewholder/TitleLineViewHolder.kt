package com.kotlin.feature.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kotlin.feature.databinding.ItemTitleLineBinding
import com.kotlin.feature.model.HomeSealedItem

class TitleLineViewHolder(private val binding: ItemTitleLineBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(titleData: HomeSealedItem.TitleLine) {
        binding.tvTitle.text = titleData.title
    }
}
