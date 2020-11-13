package com.nhat.boiterplate.ui.shop

import android.annotation.SuppressLint
import android.graphics.Point
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import com.nhat.boiterplate.R
import com.nhat.presentation.module.Comment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_shop_fragment.*
import kotlinx.android.synthetic.main.item_review.view.*


class ReviewShopList (private val fragment: Fragment, commentlist: List<Comment>) : BaseAdapter(){

        private var commentlist = ArrayList<Comment>()

        init {
            this.commentlist = commentlist as ArrayList<Comment>
        }

        override fun getCount(): Int {
            return commentlist.size
        }

        override fun getItem(i: Int): Any {
            return i
        }

        override fun getItemId(i: Int): Long {
            return i.toLong()
        }

        @SuppressLint("InflateParams", "ViewHolder")
        override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
            val inflater = fragment.layoutInflater
            val itemcommentView = inflater.inflate(R.layout.item_review, null, true)
            Picasso.get().load(commentlist[i].image)
                .resize(64,64)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.logo)
                .into(itemcommentView.avatar)
            itemcommentView.textnameAccount.text = commentlist[i].name
            itemcommentView.textnumberrate.text = commentlist[i].rate.toString()
            itemcommentView.commentAccount.text = commentlist[i].comment
            itemcommentView.ratingbar.rating = commentlist[i].rate.toFloat()
            return itemcommentView
        }
}