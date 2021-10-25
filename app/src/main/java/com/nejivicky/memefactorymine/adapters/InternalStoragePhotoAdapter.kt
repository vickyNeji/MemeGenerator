package com.nejivicky.memefactorymine.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nejivicky.memefactorymine.databinding.ItemPhotoBinding
import com.nejivicky.memefactorymine.models.InternalStoragePhoto

class InternalStoragePhotoAdapter(private val onStoragePhotoClicked: OnStoragePhotoClicked):RecyclerView.Adapter<InternalStoragePhotoAdapter.StorageViewHolder>() {

    private val differCallBack= object :DiffUtil.ItemCallback<InternalStoragePhoto>(){
        override fun areItemsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto): Boolean {
           return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto): Boolean {
            return oldItem.name==newItem.name
        }

    }

    val differ= AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageViewHolder {
        return StorageViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: StorageViewHolder, position: Int) {
        val photo= differ.currentList[position]
        holder.bind(photo)
        holder.binding.ivDrawShare.setOnClickListener {
            onStoragePhotoClicked.onShareClick(photo.name,photo.bmp)
        }

        holder.binding.ivDrawDelete.setOnClickListener {
            onStoragePhotoClicked.onDeleteClick(photo.name)
        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    inner class StorageViewHolder( val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(photo:InternalStoragePhoto){
            binding.ivPhoto.setImageBitmap(photo.bmp)
        }
    }

    interface OnStoragePhotoClicked{
        fun onShareClick(fileName:String,bitmap: Bitmap)
        fun onDeleteClick(fileName: String)
    }

}