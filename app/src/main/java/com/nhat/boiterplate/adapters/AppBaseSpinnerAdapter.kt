package com.nhat.boiterplate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class AppBaseSpinnerAdapter<T, VH : AppBaseSpinnerAdapter.SpinnerHolder>(
    protected val context: Context,
    private val layout: Int
) : BaseAdapter() {
    private val data: MutableList<T> = mutableListOf()
    private val dataInitObservable = PublishSubject.create<T>()

    var selectedItemPosition: Int = 0

    abstract fun bindView(holder: VH, position: Int)

    abstract fun createHolder(rowView: View): VH

    fun clear() {
        data.clear()
    }

    fun add(item: T) {
        data.add(0, item)
    }

    fun addLast(item: T) {
        data.add(data.size, item)
    }

    fun addAll(list: MutableList<T>) {
        if (list.isNotEmpty()) {
            this.data.addAll(list)
            notifyDataSetChanged()
            dataInitObservable.onNext(data[selectedItemPosition])
        }
    }

    fun isSelected(position: Int) = position == selectedItemPosition

    fun isLastItem(position: Int) = position == data.size - 1

    fun setSelection(selection: Int) {
        this.selectedItemPosition = selection
        notifyDataSetChanged()
    }

    fun onDataInitiation(): Observable<T> = dataInitObservable

    fun getCurrentItem(): T = data[selectedItemPosition]

    override fun getItem(position: Int): T = data[position]

    override fun getItemId(position: Int): Long = data[position].hashCode().toLong()

    override fun getCount(): Int = data.size

    private fun row(convertView: View?, position: Int): View {
        var rowView = convertView
        bindView(
            if (rowView == null) {
                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                rowView = inflater.inflate(layout, null, false)
                val vh = createHolder(rowView)
                rowView.tag = vh
                vh
            } else {
                rowView.tag as VH
            },
            position
        )
        return rowView!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
        row(convertView, position)

    abstract class SpinnerHolder
}