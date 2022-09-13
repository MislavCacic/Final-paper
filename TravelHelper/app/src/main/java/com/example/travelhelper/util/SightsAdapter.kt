package com.example.travelhelper.util

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.travelhelper.R
import com.example.travelhelper.databinding.ItemSightBinding
import com.example.travelhelper.model.Sight
import com.example.travelhelper.viewModel.SightsViewModel

data class SelectableItem<T>(
    val item: T,
    var isSelected: Boolean = false
)

class SightsAdapter(
    private var sightsList: List<SelectableItem<Sight>> = listOf(),
    private val vm: SightsViewModel
) : RecyclerView.Adapter<SightsAdapter.SightsVH>() {

    fun getSelectedSights(): List<Sight> = sightsList.filter { it.isSelected }.map { it.item }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SightsVH {
        val binding =
            ItemSightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SightsVH(binding)
    }

    override fun onBindViewHolder(holder: SightsVH, position: Int) {
        return holder.bind(sightsList[position])
    }

    override fun getItemCount(): Int = sightsList.size

    fun setSights(newSights: List<Sight>) {
        sightsList = newSights.map { SelectableItem(item = it) }
        notifyDataSetChanged()
    }

    inner class SightsVH(private val binding: ItemSightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var item: SelectableItem<Sight>? = null

        private val onClick = View.OnClickListener {
            item?.isSelected = !(item?.isSelected)!!
            if (item?.isSelected!!) binding.cardSight.background = AppCompatResources.getDrawable(
                binding.root.context,
                R.drawable.selected_card
            ) else binding.cardSight.setBackgroundColor(Color.WHITE)
        }

        fun bind(selectableItem: SelectableItem<Sight>) {
            this.item = selectableItem
            binding.apply {
                ratingBar.rating = selectableItem.item.rating.toFloat()
                cardSight.setOnClickListener(onClick)
                sightName.text = selectableItem.item.name
                sightDescription.text = selectableItem.item.description
                sightAddress.text = selectableItem.item.location?.getLocation()
                selectableItem.item.id?.let {
                    vm.getImageBitmapFromStorage(it) { bitmap ->
                        sightPicture.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
}