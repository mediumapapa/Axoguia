package com.example.axoguia.home.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.axoguia.core.model.Place
import com.example.axoguia.databinding.ItemPlaceBinding

class PlacesAdapter(
    private val onItemClick: (Place) -> Unit = {}
) : ListAdapter<Place, PlacesAdapter.PlaceViewHolder>(DIFF) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceViewHolder {
        val binding = ItemPlaceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PlaceViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class PlaceViewHolder(
        private val binding: ItemPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(place: Place) {
            binding.tvPlaceBorough.text = place.borough
            binding.tvPlaceName.text = place.shortName
            binding.tvPlaceType.text = place.type

            binding.root.setOnClickListener {
                onItemClick(place)
            }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(
                oldItem: Place,
                newItem: Place
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Place,
                newItem: Place
            ) = oldItem == newItem
        }
    }
}
