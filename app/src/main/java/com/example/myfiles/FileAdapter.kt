package com.example.myfiles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myfiles.databinding.LayoutFileItemBinding

class FileAdapter(private var fileList: List<FileData>) : RecyclerView.Adapter<FileViewHolder>() {
    var onItemClick : ((FileData) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding =
            LayoutFileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = fileList[position]
        holder.bindView(file)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(file)
        }
    }
}

class FileViewHolder(private val binding: LayoutFileItemBinding) : ViewHolder(binding.root) {
    fun bindView(fileData: FileData) {
        binding.imgFile.setImageResource(fileData.image)
        binding.tvFileName.text = fileData.file
        binding.tvTimestamp.text = fileData.file
    }
}