package com.eniskaner.eyojcryptoappwithkoin.ui.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.eniskaner.eyojcryptoappwithkoin.R
import com.eniskaner.eyojcryptoappwithkoin.base.BaseFragment
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCrypto
import com.eniskaner.eyojcryptoappwithkoin.databinding.FragmentCryptoDetailBinding
import com.eniskaner.eyojcryptoappwithkoin.network.model.toLocalCrypto
import com.eniskaner.eyojcryptoappwithkoin.ui.viewmodel.CryptoDetailViewModel
import com.eniskaner.eyojcryptoappwithkoin.utils.Resource
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CryptoDetailFragment : BaseFragment<FragmentCryptoDetailBinding>(), ViewModelStoreOwner {
    private val viewModel: CryptoDetailViewModel by viewModel()
    private var selectedCrypto: LocalCrypto? = null
    override fun setBinding(): FragmentCryptoDetailBinding =
        FragmentCryptoDetailBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cryptoId = arguments?.getString("cryptoId")
        val price = arguments?.getDouble("price")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cryptoId?.let { id ->
                    viewModel.getCrypto(id).collect { crypto ->
                        val localCrypto = crypto.data?.toLocalCrypto()
                        if (localCrypto != null) {
                            updateCryptoDetail(localCrypto, price)
                        } else {
                            fetchCryptoData(cryptoId, price)
                        }
                    }
                }
            }
        }
        binding.cryptoDescription.apply {
            setOnClickListener {
                maxLines = Int.MAX_VALUE
                ellipsize = null
            }
        }
    }
    private fun fetchCryptoData(cryptoId: String?, price: Double?) {
        viewLifecycleOwner.lifecycleScope.apply {
            launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    cryptoId?.let { id ->
                        val resource = viewModel.getCrypto(id)
                        resource.collect { result ->
                            when (result) {
                                is Resource.Success -> {
                                    val selectedCrypto = result.data?.toLocalCrypto()
                                    updateCryptoDetail(selectedCrypto, price)
                                }
                                is Resource.Error -> {
                                    binding.apply {
                                        loadingProgressBar.visibility = View.GONE
                                        errorTextView.visibility = View.VISIBLE
                                        errorTextView.text = result.message ?: "Error!"
                                    }
                                }
                                is Resource.Loading -> {
                                    binding.apply {
                                        errorTextView.visibility = View.GONE
                                        loadingProgressBar.visibility = View.VISIBLE
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private fun updateCryptoDetail(selectedCrypto: LocalCrypto?, price: Double?) {
        selectedCrypto?.let { crypto ->
            binding.apply {
                cryptoName.text = crypto.name
                cryptoPrice.text = price.toString()
                cryptoDescription.text = crypto.description

                Glide.with(requireContext())
                    .load(crypto.logo)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_error)
                    .centerCrop()
                    .into(binding.imageLogo)
            }
        }
    }
    fun refreshData() {
        val cryptoId = arguments?.getString("cryptoId")
        val price = arguments?.getDouble("price")

        fetchCryptoData(cryptoId, price)
    }
}