package com.example.harajtask.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.harajtask.data.Product
import com.example.harajtask.data.ProductAdapter
import com.example.harajtask.data.ProductOnClickListener
import com.example.harajtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ProductOnClickListener {

    lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProducts()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                productAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })


    }

    private fun getProducts() {
        viewModel.products.observe(this, Observer {
            showProducts(it)
        })
    }

    private fun showProducts(list: List<Product>) {
         productAdapter = ProductAdapter(list, this)
        binding.recycleView.apply {
            adapter = productAdapter
        }


    }

    override fun onProductClicked(product: Product) {

        val intent = Intent(this, ProductsDetailsActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)

    }
}