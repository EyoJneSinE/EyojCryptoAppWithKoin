package com.eniskaner.eyojcryptoappwithkoin.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eniskaner.eyojcryptoappwithkoin.R
import com.eniskaner.eyojcryptoappwithkoin.ui.adapter.CryptoListAdapter
import com.eniskaner.eyojcryptoappwithkoin.base.BaseFragment
import com.eniskaner.eyojcryptoappwithkoin.databinding.FragmentCryptoListBinding
import com.eniskaner.eyojcryptoappwithkoin.network.model.CryptoAllListItem
import com.eniskaner.eyojcryptoappwithkoin.network.model.toLocalCryptoList
import com.eniskaner.eyojcryptoappwithkoin.ui.viewmodel.CryptoListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CryptoListFragment : BaseFragment<FragmentCryptoListBinding>() {
    private val viewModel : CryptoListViewModel by viewModel()
    private val localCryptoList: MutableLiveData<List<CryptoAllListItem>> = MutableLiveData()
    val navController: NavController by lazy {
        findNavController()
    }
    override fun setBinding(): FragmentCryptoListBinding = FragmentCryptoListBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
        refreshData()
    }
    private fun setupViews() {
        binding.apply {
            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val text = s.toString()
                    val isHintDisplayed = text.isEmpty()

                    if (isHintDisplayed) {
                        binding.searchEditText.setHint(R.string.search_hint)
                    } else {
                        binding.searchEditText.setHint("")
                    }
                    viewModel.searchCryptoList(text)
                }
                override fun afterTextChanged(s: Editable?) {}
            })
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.apply {
            launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.apply {
                        cryptoList.collect { resource ->
                            val cryptoList = resource.data
                            cryptoList?.let {
                                binding.recyclerView.adapter = CryptoListAdapter { crypto ->
                                    navigateToCryptoDetail(crypto)
                                }.apply { submitList(cryptoList) }
                            }
                        }
                    }
                }
            }
            launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isLoading.collect {isLoading ->
                        binding.cryptoProgressBar.isVisible = isLoading
                    }
                }
            }
            launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.errorMessage.collect {errorMessage ->
                        binding.apply {
                            cryptoErrorText.isVisible = errorMessage.isNotEmpty()
                            cryptoErrorText.text = errorMessage
                        }
                    }
                }
            }
        }
    }
    private fun navigateToCryptoDetail(crypto: CryptoAllListItem) {
        val bundle = bundleOf(
            "cryptoId" to crypto.id,
            "price" to crypto.quotes.USD.price
        )
        val localCrypto = localCryptoList.value?.find { it.id == crypto.id }?.toLocalCryptoList()
        if (localCrypto != null) {
            bundle.putParcelable("localCrypto" , localCrypto)
        }
        navController.navigate(R.id.action_cryptoListFragment_to_cryptoDetailFragment, bundle)
    }
    private fun refreshData() {
        viewModel.refreshCryptoList()
    }
}