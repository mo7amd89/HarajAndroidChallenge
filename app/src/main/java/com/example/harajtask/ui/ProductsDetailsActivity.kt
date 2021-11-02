package com.example.harajtask.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.harajtask.data.Product
import com.example.harajtask.databinding.ActivityProductsDetailsBinding
import com.example.harajtask.util.setDateFormat
import com.example.harajtask.util.setDateTime
import kotlinx.android.synthetic.main.activity_products_details.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class ProductsDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductsDetailsBinding
    lateinit var product: Product
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductsDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val mIntent = intent

        //product = mIntent.getParcelableExtra("product")!!
        product = mIntent.getSerializableExtra("product") as Product
        binding.apply {

            Glide.with(this@ProductsDetailsActivity).load(product.thumbURL).into(productImage)

            title.text = product.title
            date.text = setDateFormat(product.date)
            username.text = product.username
            detail.text = product.body
            city.text = product.city

            contactPhone.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:${contactPhone.text.toString()}")
                startActivity(callIntent)
            }

            imageViewShare.setOnClickListener {
                onShareItem(imageViewShare)
            }


        }

    }


    private fun onShareItem(ivImage: ImageView) {
        // Get access to bitmap image from view
        // val ivImage: ImageView = binding.productImage
        // Get access to the URI for the bitmap
        // val bmpUri = getLocalBitmapUri(ivImage)

        val bitmap = Bitmap.createBitmap(ivImage.width, ivImage.height, Bitmap.Config.RGB_565)

        val bmpUri = getBitmapFromDrawable(bitmap)
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, product.title)
            shareIntent.putExtra(Intent.EXTRA_TEXT, product.body)
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
            shareIntent.type = "image/*"
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Product"))
        } else {

            Toast.makeText(this, "product image not loaded", Toast.LENGTH_LONG).show()
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    fun getLocalBitmapUri(imageView: ImageView): Uri? {
        // Extract Bitmap from ImageView drawable
        val drawable: Drawable = imageView.drawable
        var bmp: Bitmap? = null
        bmp = if (drawable is BitmapDrawable) {
            (imageView.drawable as BitmapDrawable).bitmap
        } else {
            return null
        }
        // Store image to default external storage directory
        var bmpUri: Uri? = null
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            val file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.
            bmpUri = Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    // Method when launching drawable within Glide
    private fun getBitmapFromDrawable(bmp: Bitmap): Uri? {

        // Store image to default external storage directory
        var bmpUri: Uri? = null
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            val file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()

            // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
            bmpUri = FileProvider.getUriForFile(
                this@ProductsDetailsActivity,
                "com.example.harajtask",
                file
            ) // use this version for API >= 24

            // **Note:** For API < 24, you may use bmpUri = Uri.fromFile(file);
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }


}