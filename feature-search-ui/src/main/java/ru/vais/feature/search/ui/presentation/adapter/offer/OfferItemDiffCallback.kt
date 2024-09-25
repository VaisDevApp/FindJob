package ru.vais.feature.search.ui.presentation.adapter.offer

import androidx.recyclerview.widget.DiffUtil
class OfferItemDiffCallback : DiffUtil.ItemCallback<OfferItem>() {
    override fun areItemsTheSame(oldItem: OfferItem, newItem: OfferItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: OfferItem, newItem: OfferItem): Boolean {
        return oldItem == newItem
    }
}