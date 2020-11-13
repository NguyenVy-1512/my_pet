package com.nhat.boiterplate.ui.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.nhat.boiterplate.R
import com.nhat.presentation.module.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_shop_fragment.*

class ImageViewHolder (inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_image, parent, false)) {
        private var ImageShop: ImageView? = null

        init {
            ImageShop = itemView.findViewById(R.id.imageshop)
        }

        fun bind(Image: Image) {
            Picasso.get().load(Image.src).placeholder(R.drawable.ic_launcher_background).error(R.drawable.logo).fit().into(ImageShop)
        }

}