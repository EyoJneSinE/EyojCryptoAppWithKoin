package com.eniskaner.eyojcryptoappwithkoin.ui.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.eniskaner.eyojcryptoappwithkoin.R
import com.eniskaner.eyojcryptoappwithkoin.base.BaseFragment
import com.eniskaner.eyojcryptoappwithkoin.databinding.FragmentCryptoDetailBinding
import com.eniskaner.eyojcryptoappwithkoin.network.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.utils.Status
import com.eniskaner.eyojcryptoappwithkoin.ui.viewmodel.CryptoDetailViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CryptoDetailFragment : BaseFragment<FragmentCryptoDetailBinding>(), ViewModelStoreOwner {
    private val viewModel: CryptoDetailViewModel by viewModel()
    private var selectedCrypto: Crypto? = null
    override fun setBinding(): FragmentCryptoDetailBinding = FragmentCryptoDetailBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cryptoId = arguments?.getString("cryptoId")
        val price = arguments?.getDouble("price")
        fetchCryptoData(cryptoId, price)
        binding.cryptoDescription.setOnClickListener {
            binding.cryptoDescription.maxLines = Int.MAX_VALUE
            binding.cryptoDescription.ellipsize = null
        }
    }
    private fun fetchCryptoData(cryptoId: String?, price: Double?) {
        lifecycleScope.launch {
            cryptoId?.let { id ->
                val resource = viewModel.getCrypto(id)
                when (resource.status) {
                    Status.SUCCESS -> {
                        selectedCrypto = resource.data
                        updateCryptoDetail(price)
                    }
                    Status.ERROR -> {
                        val errorMessage = resource.message ?: "Error!"
                        // Handle error
                    }
                    Status.LOADING -> {
                        val loading = resource.message ?: "Loading!"
                        // Show loading state
                    }
                }
            }
        }
    }
    private fun updateCryptoDetail(price: Double?) {
        selectedCrypto?.let { crypto ->
            binding.cryptoName.text = crypto.name
            binding.cryptoPrice.text = price?.toString()
            binding.cryptoDescription.text = crypto.description

            Glide.with(requireContext())
                .load(crypto.logo)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_error)
                .centerCrop()
                .into(binding.imageLogo)
        }
    }
    fun refreshData() {
        val cryptoId = arguments?.getString("cryptoId")
        val price = arguments?.getDouble("price")

        fetchCryptoData(cryptoId, price)
    }
}