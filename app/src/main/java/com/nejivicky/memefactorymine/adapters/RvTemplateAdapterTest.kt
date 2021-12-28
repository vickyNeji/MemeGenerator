package com.nejivicky.memefactorymine.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nejivicky.memefactorymine.R
import com.nejivicky.memefactorymine.databinding.RvTemplateSingeItemBinding
import com.nejivicky.memefactorymine.models.Template

class RvTemplateAdapterTest(private val list:List<Template>, val onTemplateClicked: OnTemplateClicked):
    RecyclerView.Adapter<RvTemplateAdapterTest.DataHolder>(),Filterable {

    var templateFilteredList = ArrayList<Template>()

    init {
        templateFilteredList = list as ArrayList<Template>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val binding= RvTemplateSingeItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return DataHolder(binding)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onTemplateClicked.onTemplateCLick(templateFilteredList[position])
        }
        holder.bind(templateFilteredList[position])

    }

    override fun getItemCount(): Int {
        return templateFilteredList.size
    }


    inner class DataHolder( private val binding:RvTemplateSingeItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(template:Template){
        Glide.with(binding.ivTemplate)
            .load(template.url)
            .into(binding.ivTemplate)
        }
    }

    interface OnTemplateClicked{
        fun onTemplateCLick(template:Template)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    templateFilteredList = list as ArrayList<Template>
                } else {
                    val resultList = ArrayList<Template>()
                    for (row in list) {
                        if (row.name.lowercase().contains(constraint.toString().lowercase())) {
                            resultList.add(row)
                        }
                    }
                    templateFilteredList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = templateFilteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                templateFilteredList = results?.values as ArrayList<Template>
                notifyDataSetChanged()
            }
        }
    }

}