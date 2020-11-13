package com.nhat.boiterplate.ui.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nhat.presentation.module.Image


class ImageShopList(private val imageList: List<Image>)
    : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val Image: Image = imageList[position]
        holder.bind(Image)
    }

    override fun getItemCount(): Int = imageList.size

}