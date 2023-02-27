package com.shoppi.app.ui.categorydetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shoppi.app.R
import com.shoppi.app.common.KEY_CATEGORY_ID
import com.shoppi.app.common.KEY_CATEGORY_LABEL
import com.shoppi.app.databinding.FragmentCategoryDetailBinding


class CategoryDetailFragment : Fragment() {

    private lateinit var binding: FragmentCategoryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        val categoryId = requireArguments().getString(KEY_CATEGORY_ID)
        val categoryLabel = requireArguments().getString(KEY_CATEGORY_LABEL)
        binding.toolbarCategoryDetail.title = categoryLabel

    }
}