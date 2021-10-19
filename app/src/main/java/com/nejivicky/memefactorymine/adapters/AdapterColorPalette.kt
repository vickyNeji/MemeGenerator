package com.nejivicky.memefactorymine.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.nejivicky.memefactorymine.R
import com.nejivicky.memefactorymine.databinding.RvSilgleItemColorPalleteBinding
import com.nejivicky.memefactorymine.databinding.RvTemplateSingeItemBinding

class AdapterColorPalette(
    private val colorList:List<Int>,
    private val onPaletteClick: OnPaletteClick
):RecyclerView.Adapter<AdapterColorPalette.DataHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val binding= RvSilgleItemColorPalleteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataHolder(binding)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.bind(colorList[position])
        holder.binding.root.setOnClickListener {
            onPaletteClick.onPaletteClicked(colorList[position])
        }
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    inner class DataHolder(val binding: RvSilgleItemColorPalleteBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(selectedColor:Int){
            val drawable= ResourcesCompat.getDrawable(binding.root.resources, R.drawable.circle,null)
            drawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                DrawableCompat.setTint(wrappedDrawable, selectedColor)
                binding.viewAdapterNoteColor.background=wrappedDrawable
            }
        }
    }

    interface OnPaletteClick{
        fun onPaletteClicked(selectedColor:Int)
    }

}