package com.eniskaner.eyojcryptoappwithkoin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eniskaner.eyojcryptoappwithkoin.R
import com.eniskaner.eyojcryptoappwithkoin.base.BaseFragment
import com.eniskaner.eyojcryptoappwithkoin.databinding.FragmentCryptoListBinding

class CryptoListFragment : BaseFragment<FragmentCryptoListBinding>() {

    override fun setBinding(): FragmentCryptoListBinding =
        FragmentCryptoListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}