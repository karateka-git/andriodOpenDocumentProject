package com.example.openDocumentProject.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.openDocumentProject.databinding.FileItemViewHolderBinding
import com.example.openDocumentProject.models.FileItem

class FileViewHolder(private val binding: FileItemViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FileItem) {
        binding.fileName.text = item.url.toString()
    }
}
