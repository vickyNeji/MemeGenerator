package com.nejivicky.memefactorymine.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nejivicky.memefactorymine.R
import com.nejivicky.memefactorymine.databinding.RvTemplateSingeItemBinding

class RvTemplateAdapter(private val list:List<Int>,val onTemplateClicked: OnTemplateClicked):RecyclerView.Adapter<RvTemplateAdapter.DataHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val binding= RvTemplateSingeItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return DataHolder(binding)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onTemplateClicked.onTemplateCLick(list[position])
        }
        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class DataHolder( private val binding:RvTemplateSingeItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(template:Int){
            binding.ivTemplate.setImageResource(template)
        }
    }

    interface OnTemplateClicked{
        fun onTemplateCLick(template:Int)
    }

}