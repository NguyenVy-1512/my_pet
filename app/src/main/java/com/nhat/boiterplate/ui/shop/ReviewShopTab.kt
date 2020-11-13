package com.nhat.boiterplate.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nhat.boiterplate.R
import com.nhat.presentation.module.Comment
import kotlinx.android.synthetic.main.review_shop_tab.view.*

class ReviewShopTab : Fragment() {
    var commentList = ArrayList<Comment>()
    var commentadapter: ReviewShopList? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.review_shop_tab, container, false)
        commentadapter = ReviewShopList(this, commentList)
        rootView.review_list_view.adapter = commentadapter
        prepareReviewData()
        return rootView
    }

    //FIXME. this funtion is only used to test. Delete it when getting data from the google API
    private fun prepareReviewData() {
        var comment = Comment("Vy Nguyễn", 4.0, "Shop rất tốt và rẻ", "https://i.ibb.co/w7Tkjjd/marker.png")
        commentList.add(comment)

        comment = Comment("Minh Đức", 3.8, "Đây là một trong những shop mà mình ưng ý nhất vì sự đa dạng và tận tình của nhân viên hướng dẫn. Thú cứng rất xinh và vừa giá tiền của mình. Mình sẽ tiếp tục ủng hộ","https://i.ibb.co/8DQt2N7/Hinh-anh-dong-vat-dang-yeu-cute-nhat-7.jpg")
        commentList.add(comment)

        comment = Comment("Hoàng Quân", 4.1, "Đồ ăn và thú cưng đa dạng, sẽ tiếp tục ủng hộ","https://i.ibb.co/VjFWNys/your-logo-1.png")
        commentList.add(comment)
        commentadapter?.notifyDataSetChanged()
    }
}