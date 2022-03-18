package com.example.openDocumentProject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openDocumentProject.databinding.FileItemViewHolderBinding
import com.example.openDocumentProject.models.FileItem

class FileAdapter : RecyclerView.Adapter<FileViewHolder>() {
    private var items = mutableListOf<FileItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun swapItems(items: List<FileItem>) {
        this.items = items as MutableList<FileItem>
        notifyDataSetChanged()
    }

    fun addItem(item: FileItem) {
        items.add(item)
        notifyItemRangeInserted(items.size - 1, 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder =
        FileViewHolder(
            FileItemViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() =
        items.size
}
