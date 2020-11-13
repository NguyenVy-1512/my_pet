package com.nhat.boiterplate.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import com.nhat.boiterplate.R
import com.nhat.boiterplate.model.MovieViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import javax.inject.Inject


class MoviesListAdapter @Inject constructor() :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    var movies: AsyncListDiffer<MovieViewModel> =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<MovieViewModel>() {
            override fun areItemsTheSame(
                oldItem: MovieViewModel,
                newItem: MovieViewModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieViewModel,
                newItem: MovieViewModel
            ): Boolean {
                return oldItem.equals(newItem)
            }
        })

    val itemEventSource: PublishProcessor<Int> = PublishProcessor.create<Int>()

    fun submitList(list: List<MovieViewModel>) {
        movies.submitList(list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieViewModel = movies.currentList[position]
        holder.attach()
        holder.disposable.addAll(holder.itemEvent.subscribe { itemEventSource.onNext(it) })
        Glide.with(holder.itemView.context)
            .load(movieViewModel.posterPathUrl)
            .placeholder(R.drawable.placeholder16x9)
            .error(R.drawable.placeholder16x9)
            .into(holder.avatarImage)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.disposable.clear()
        super.onViewRecycled(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_movie, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movies.currentList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val disposable = CompositeDisposable()
        val itemEvent: PublishProcessor<Int> = PublishProcessor.create<Int>()
        var avatarImage: ImageView = view.findViewById(R.id.image_avatar)
        fun attach() {
            disposable.addAll(
                avatarImage.clicks().map { adapterPosition }.subscribe { itemEvent.onNext(it) }
            )
        }
    }
}