package com.eniskaner.eyojcryptoappwithkoin.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eniskaner.eyojcryptoappwithkoin.R
import com.eniskaner.eyojcryptoappwithkoin.adapter.CryptoListAdapter
import com.eniskaner.eyojcryptoappwithkoin.base.BaseFragment
import com.eniskaner.eyojcryptoappwithkoin.databinding.FragmentCryptoListBinding
import com.eniskaner.eyojcryptoappwithkoin.viewmodel.CryptoListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CryptoListFragment : BaseFragment<FragmentCryptoListBinding>() {
    private val viewModel : CryptoListViewModel by viewModel()
    val navController: NavController by lazy {
        findNavController()
    }
    override fun setBinding(): FragmentCryptoListBinding = FragmentCryptoListBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }
    private fun setupViews() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun observeViewModel() {
        viewModel.cryptoList.observe(viewLifecycleOwner) { resource ->
            val cryptoList = resource.data
            binding.recyclerView.adapter = cryptoList?.let {
                CryptoListAdapter(it) { crypto ->
                    navigateToCryptoDetail(crypto.id, crypto.quotes.USD.price)
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.cryptoProgressBar.isVisible = isLoading
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            binding.cryptoErrorText.isVisible = errorMessage.isNotEmpty()
            binding.cryptoErrorText.text = errorMessage
        }
    }
    private fun navigateToCryptoDetail(cryptoId: String, price: Double) {
        val bundle = bundleOf("cryptoId" to cryptoId, "price" to price)
        navController.navigate(R.id.action_cryptoListFragment_to_cryptoDetailFragment, bundle)
    }
}