package com.nhat.boiterplate.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nhat.boiterplate.R
import com.nhat.presentation.module.Image
import kotlinx.android.synthetic.main.image_shop_tab.view.*


class ImageShopTab : Fragment() {
    var imagelist = ArrayList<Image>()
    var imageadapter: ImageShopList? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.image_shop_tab, container, false)
        val staggeredGridLayoutManager= StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        rootView.imagerecyclerview.layoutManager = staggeredGridLayoutManager
        imageadapter = ImageShopList(imagelist)
        rootView.imagerecyclerview.adapter = imageadapter
        prepareImageData()
        return rootView
    }

    //FIXME. this funtion is only used to test. Delete it when getting data from the google API
    private fun prepareImageData() {
        var image = Image("https://i.ibb.co/ZfXwnzH/chronometer.png")
        imagelist.add(image)

        image = Image("https://i.ibb.co/VjFWNys/your-logo-1.png")
        imagelist.add(image)

        image = Image("https://i.ibb.co/X2Z1whm/way.png")
        imagelist.add(image)

        image = Image("https://i.ibb.co/8DQt2N7/Hinh-anh-dong-vat-dang-yeu-cute-nhat-7.jpg")
        imagelist.add(image)

        image = Image("https://i.ibb.co/3kZ1sSN/phone-number.png")
        imagelist.add(image)

        image = Image("https://i.ibb.co/w7Tkjjd/marker.png")
        imagelist.add(image)
        imageadapter?.notifyDataSetChanged()
    }
}