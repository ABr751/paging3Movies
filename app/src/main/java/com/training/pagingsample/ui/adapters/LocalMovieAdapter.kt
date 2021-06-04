package com.training.pagingsample.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.training.pagingsample.R
import com.training.pagingsample.data.model.Content
import kotlinx.android.synthetic.main.item_content.view.*


class LocalMovieAdapter : PagingDataAdapter<Content, RecyclerView.ViewHolder>(
    ContentModelComparator
) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val content = getItem(position)!!
        content.let {
            val viewHolder = holder as LocalMovieViewHolder
            viewHolder.itemView.contentName.text = it.name
            setImage(it.posterImage ?: "", viewHolder.itemView.posterIV)
        }
    }

    private fun setImage(drawableSrc: String, iv: ImageView) {
        when {
            drawableSrc.contains("1") -> {
                Glide.with(iv.context).load(R.drawable.poster1)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder_for_missing_posters))
                    .into(iv)
            }
            drawableSrc.contains("2") -> {
                Glide.with(iv.context).load(R.drawable.poster2)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder_for_missing_posters))
                    .into(iv)
            }
            drawableSrc.contains("3") -> {
                Glide.with(iv.context).load(R.drawable.poster3)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder_for_missing_posters))
                    .into(iv)
            }
            drawableSrc.contains("4") -> {
                Glide.with(iv.context).load(R.drawable.poster4)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder_for_missing_posters))
                    .into(iv)
            }
            drawableSrc.contains("5") -> {
                Glide.with(iv.context).load(R.drawable.poster5)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder_for_missing_posters))
                    .into(iv)
            }
            drawableSrc.contains("6") -> {
                Glide.with(iv.context).load(R.drawable.poster6)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder_for_missing_posters))
                    .into(iv)
            }
            drawableSrc.contains("7") -> {
                Glide.with(iv.context).load(R.drawable.poster7)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder_for_missing_posters))
                    .into(iv)
            }
            drawableSrc.contains("8") -> {
                Glide.with(iv.context).load(R.drawable.poster8)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder_for_missing_posters))
                    .into(iv)
            }
            drawableSrc.contains("9") -> {
                Glide.with(iv.context).load(R.drawable.poster9)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder_for_missing_posters))
                    .into(iv)
            }
            else->{
                Glide.with(iv.context).load(R.drawable.poster_not_available)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder_for_missing_posters))
                    .into(iv)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LocalMovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_content, parent, false)
        )
    }

    class LocalMovieViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private val ContentModelComparator = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem.name == newItem.name


            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem.equals(newItem)
        }
    }

}