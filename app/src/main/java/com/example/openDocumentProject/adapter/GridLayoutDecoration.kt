package com.example.openDocumentProject.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridLayoutDecoration(val gridSpacingPx: Int, val gridSize: Int) : RecyclerView.ItemDecoration() {
    private var needLeftSpacing = false

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val frameWidth =
            ((parent.width - gridSpacingPx * (gridSize - 1)) / gridSize) as Int
        val padding: Int = parent.width / gridSize - frameWidth
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        if (itemPosition < gridSize) {
            outRect.top = 0
        } else {
            outRect.top = gridSpacingPx
        }
        if (itemPosition % gridSize == 0) {
            outRect.left = 0
            outRect.right = padding
            needLeftSpacing = true
        } else if ((itemPosition + 1) % gridSize == 0) {
            needLeftSpacing = false
            outRect.right = 0
            outRect.left = padding
        } else if (needLeftSpacing) {
            needLeftSpacing = false
            outRect.left = gridSpacingPx - padding
            if ((itemPosition + 2) % gridSize == 0) {
                outRect.right = gridSpacingPx - padding
            } else {
                outRect.right = gridSpacingPx / 2
            }
        } else if ((itemPosition + 2) % gridSize == 0) {
            needLeftSpacing = false
            outRect.left = gridSpacingPx / 2
            outRect.right = gridSpacingPx - padding
        } else {
            needLeftSpacing = false
            outRect.left = gridSpacingPx / 2
            outRect.right = gridSpacingPx / 2
        }
        outRect.bottom = 0
    }
}
