package com.eniskaner.eyojcryptoappwithkoin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eniskaner.eyojcryptoappwithkoin.databinding.RecyclerRowBinding
import com.eniskaner.eyojcryptoappwithkoin.model.CryptoAllListItem

class CryptoListAdapter(
    private val cryptoList: List<CryptoAllListItem>,
    private val onItemClick: (CryptoAllListItem) -> Unit
) : RecyclerView.Adapter<CryptoListAdapter.CryptoViewHolder>() {

    private val colors: Array<String> = arrayOf("#13bd27", "#29c1e1", "#b129e1", "#d3df13", "#f6bd0c", "#a1fb93", "#0d9de3", "#ffe48f")

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
        val color = Color.parseColor(colors[position % colors.size])
        holder.bind(crypto, color)
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

        fun bind(crypto: CryptoAllListItem, color: Int) {
            binding.apply {
                cryptoNameText.text = crypto.symbol
                cryptoPriceText.text = crypto.quotes.USD.price.toString()
                root.setBackgroundColor(color)
            }
        }
    }
}
