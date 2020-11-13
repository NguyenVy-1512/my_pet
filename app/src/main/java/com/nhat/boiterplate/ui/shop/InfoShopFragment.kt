package com.nhat.boiterplate.ui.shop

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nhat.boiterplate.R
import com.nhat.boiterplate.common.BaseFragment
import com.nhat.boiterplate.model.MovieViewModel
import com.nhat.presentation.main.MainViewModel
import com.nhat.presentation.model.PetShopView
import com.nhat.presentation.module.Shop
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_shop_fragment.*
import javax.inject.Inject

class InfoShopFragment: BaseFragment<PetShopView>() {

    override val layoutId: Int
        get() = R.layout.info_shop_fragment

    @Inject
    lateinit var viewModel: MainViewModel
    lateinit var Shop: Shop
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)

        activity?.findViewById<Toolbar>(R.id.appBar)?.let {
            it.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            it.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }

        //FIXME. this var is only used to test. Delete it when getting data from the google API
        // prepare data shop
        Shop = Shop("Shop Thỏ con", "https://i.ibb.co/8DQt2N7/Hinh-anh-dong-vat-dang-yeu-cute-nhat-7.jpg", 4.0)

        //Set data for info shop
        shop_name.text = Shop.name
        number_rate.text = Shop.rate.toString()
        rating_bar.rating = Shop.rate.toFloat()
        Picasso.get()
            .load(Shop.image)
            .fit()
            .centerCrop()
            .into(image_shop)

        // Tabs Customization
        tabs_info_shop.setSelectedTabIndicatorColor(resources.getColor(R.color.rating))
        tabs_info_shop.tabTextColors = ContextCompat.getColorStateList( this.requireContext() ,R.color.rating)

        // Number Of Tabs
        val numberOfTabs = 3

        // Show all Tabs in screen
        tabs_info_shop.tabMode = TabLayout.MODE_FIXED

        // Set the ViewPager Adapter
        val TabsInfoShopAdapter =
            activity?.supportFragmentManager?.let { TabPagerAdapter(it, lifecycle, numberOfTabs) }
        tabs_viewpager.adapter = TabsInfoShopAdapter

        // Enable Swipe
        tabs_viewpager.isUserInputEnabled = true

        // Link the TabLayout and the ViewPager2 together and Set Text & Icons
        TabLayoutMediator(tabs_info_shop, tabs_viewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Thông tin"
                }
                1 -> {
                    tab.text = "Đánh giá"
                }
                2 -> {
                    tab.text = "Hình ảnh"
                }
            }

        }.attach()
    }

    override fun setupScreenForLoadingState() {
        TODO("Not yet implemented")
    }

    override fun setupScreenForSuccess(t: PetShopView?) {
        TODO("Not yet implemented")
    }

    override fun setupScreenForError(message: String?) {
        TODO("Not yet implemented")
    }
}