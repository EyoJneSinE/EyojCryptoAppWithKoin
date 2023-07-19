package com.eniskaner.eyojcryptoappwithkoin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eniskaner.eyojcryptoappwithkoin.databinding.RecyclerRowBinding
import com.eniskaner.eyojcryptoappwithkoin.data.model.CryptoAllListItem

class CryptoListAdapter(
    private val cryptoList: List<CryptoAllListItem>,
    private val onItemClick: (CryptoAllListItem) -> Unit
) : RecyclerView.Adapter<CryptoListAdapter.CryptoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = RecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = cryptoList[position]
        holder.bind(crypto)
    }

    override fun getItemCount(): Int = cryptoList.size

    inner class CryptoViewHolder(
        private val binding: RecyclerRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val crypto = cryptoList[position]
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
}

