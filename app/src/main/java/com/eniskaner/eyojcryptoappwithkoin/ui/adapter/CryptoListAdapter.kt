package com.eniskaner.eyojcryptoappwithkoin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eniskaner.eyojcryptoappwithkoin.databinding.RecyclerRowBinding
import com.eniskaner.eyojcryptoappwithkoin.network.model.CryptoAllListItem

class CryptoListAdapter(
    private val onItemClick: (CryptoAllListItem) -> Unit
) : ListAdapter<CryptoAllListItem, CryptoListAdapter.CryptoViewHolder>(CryptoListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = RecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CryptoViewHolder(binding)
    }
    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = getItem(position)
        holder.bind(crypto)
    }
    inner class CryptoViewHolder(
        private val binding: RecyclerRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val crypto = getItem(position)
                    onItemClick(crypto)
                }
            }
        }
        fun bind(crypto: CryptoAllListItem) {
            binding.apply {
                cryptoNameText.text = crypto.symbol
                cryptoPriceText.text = crypto.quotes.USD.price.toString()
            }
        }
    }
    class CryptoListDiffCallback : DiffUtil.ItemCallback<CryptoAllListItem>() {
        override fun areItemsTheSame(oldItem: CryptoAllListItem, newItem: CryptoAllListItem): Boolean {
            return oldItem.quotes.USD.price == newItem.quotes.USD.price
        }
        override fun areContentsTheSame(oldItem: CryptoAllListItem, newItem: CryptoAllListItem): Boolean {
            return oldItem == newItem
        }
    }
}

