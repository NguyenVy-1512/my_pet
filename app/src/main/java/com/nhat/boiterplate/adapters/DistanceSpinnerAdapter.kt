package com.nhat.boiterplate.adapters

import android.content.Context
import android.view.View
import android.widget.TextView
import com.nhat.boiterplate.R
import com.nhat.presentation.model.DistanceView

class DistanceSpinnerAdapter(context: Context, itemLayout: Int) :
    AppBaseSpinnerAdapter<DistanceView, DistanceSpinnerAdapter.Holder>(context, itemLayout) {
    override fun bindView(holder: Holder, position: Int) {
        holder.distanceTextView?.text = getItem(position).distanceStringValue
    }

    override fun createHolder(rowView: View): Holder {
        val holder = Holder()
        holder.distanceTextView = rowView.findViewById(R.id.distanceTextView)
        return holder
    }

    inner class Holder : SpinnerHolder() {
        var distanceTextView: TextView? = null
    }
}