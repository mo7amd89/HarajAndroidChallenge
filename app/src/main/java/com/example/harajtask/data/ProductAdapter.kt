package com.example.harajtask.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harajtask.R
import com.example.harajtask.databinding.ProductItemsBinding
import com.example.harajtask.util.setDateFormat
import com.example.harajtask.util.setDateTime


class ProductAdapter(
    private var productList: List<Product>,
    private val listener: ProductOnClickListener
) :
    RecyclerView.Adapter<ProductAdapter.ProductHolder>(), Filterable {

    var filterList = emptyList<Product>()

    init {
        filterList = productList
    }


    override fun getItemCount(): Int {
        return filterList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding =
            ProductItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(filterList[position], listener)
    }


    class ProductHolder(var viewBinding: ProductItemsBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(product: Product, listener: ProductOnClickListener) {
            viewBinding.apply {

                if (product.commentCount < 1) {

                    commentCount.visibility = View.INVISIBLE
                    cardViewItem.setBackgroundColor(
                        ContextCompat.getColor(
                            cardViewItem.context,
                            R.color.card_dark
                        )
                    )


                }

                title.text = product.title
                username.text = product.username
                city.text = product.city
                date.text = setDateFormat(product.date)
                commentCount.text = "(${product.commentCount})"

                cardViewItem.setOnClickListener {
                    listener.onProductClicked(product)
                }
                Glide.with(itemView)
                    .load(product.thumbURL)
                    .into(imageView)
            }
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = productList
                } else {
                    val resultList = ArrayList<Product>()
                    for (row in productList) {
                        if (
                            row.title.toLowerCase().contains(constraint.toString().toLowerCase())
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<Product>
                notifyDataSetChanged()
            }

        }
    }


}