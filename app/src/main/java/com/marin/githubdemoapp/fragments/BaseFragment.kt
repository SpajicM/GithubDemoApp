package com.marin.githubdemoapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.marin.githubdemoapp.entities.api.ListResponse
import com.marin.githubdemoapp.entities.api.Repo
import com.marin.githubdemoapp.utils.Result

abstract class BaseFragment(private val layout: Int) : Fragment(layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI();
        setupObservers();
        setupClickHandlers();
    }

    abstract fun setupClickHandlers()

    abstract fun setupObservers()

    abstract fun setupUI()

    fun showError(msg: Result<Nothing>) {
        Toast.makeText(requireContext(), msg.toString(), Toast.LENGTH_SHORT).show()
    }
}