package com.eniskaner.eyojcryptoappwithkoin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = this.setBinding()
        setHasOptionsMenu(true)
        return binding.root
    }

    abstract fun setBinding(): T

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}