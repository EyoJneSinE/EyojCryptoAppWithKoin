package com.eniskaner.eyojcryptoappwithkoin.view

import android.os.Bundle
import android.view.View
import com.eniskaner.eyojcryptoappwithkoin.base.BaseFragment
import com.eniskaner.eyojcryptoappwithkoin.databinding.FragmentCryptoDetailBinding

class CryptoDetailFragment : BaseFragment<FragmentCryptoDetailBinding>() {

    override fun setBinding(): FragmentCryptoDetailBinding =
        FragmentCryptoDetailBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}