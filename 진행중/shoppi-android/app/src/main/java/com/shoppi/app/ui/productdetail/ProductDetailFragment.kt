package com.shoppi.app.ui.productdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shoppi.app.R
import com.shoppi.app.common.KEY_PRODUCT_ID
import com.shoppi.app.databinding.FragmentProductDetailBinding
import com.shoppi.app.ui.common.ViewModelFactory

class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentProductDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductDetailBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        setNavigation()
        requireArguments().getString(KEY_PRODUCT_ID)?.let {productId ->
            setLayout(productId)
        }

        val productId = requireArguments().getString(KEY_PRODUCT_ID)
        Log.d("ProductDetailFragment", "productId=$productId")
    }

    private fun setLayout(productId: String) {
        viewModel.loadProductDetail(productId)
        val descriptionAdapter = ProductDescriptionAdapter()
        binding.rvProductDetail.adapter = descriptionAdapter
        viewModel.product.observe(viewLifecycleOwner){product ->
            binding.product = product
            descriptionAdapter.submitList(product.descriptions)
        }
    }

    private fun setNavigation() {
        binding.toolbarProductDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

}