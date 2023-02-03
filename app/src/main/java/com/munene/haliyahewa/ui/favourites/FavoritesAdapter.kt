package com.munene.haliyahewa.ui.favourites

import android.annotation.SuppressLint
import com.munene.haliyahewa.R
import com.munene.haliyahewa.data.db.entities.Favorite
import com.munene.haliyahewa.databinding.ItemFavoriteBinding
import com.munene.haliyahewa.util.recyclerview.DataBoundAdapter
import com.munene.haliyahewa.util.recyclerview.DataBoundViewHolder
import com.munene.haliyahewa.util.recyclerview.RecyclerViewClickListener

class FavoritesAdapter(
    private val clickListener: RecyclerViewClickListener
) : DataBoundAdapter<ItemFavoriteBinding>(R.layout.item_favorite) {

    private var mFavorites = arrayListOf<Favorite>()

    override fun getItemCount(): Int = mFavorites.size

    override fun bindItem(
        holder: DataBoundViewHolder<ItemFavoriteBinding>?,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        val binding = holder?.binding!!
        val favorite = mFavorites[position]
        binding.favorite = favorite
        binding.root.setOnClickListener {
            clickListener.onClick(favorite)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavorites(favorites: ArrayList<Favorite>) {
        mFavorites = favorites
        notifyDataSetChanged()
    }

    fun updateFavoriteWeather(index: Int, updatedFavorite: Favorite) {
        mFavorites[index] = updatedFavorite
        notifyItemChanged(index)
    }
}
