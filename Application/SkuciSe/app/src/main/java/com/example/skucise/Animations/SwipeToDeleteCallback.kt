package com.example.skucise.Animations

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.R
import android.annotation.SuppressLint
import android.graphics.Color

import androidx.core.content.ContextCompat

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator




abstract class SwipeToDeleteCallback(context: Context): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

    val backroundColor = ContextCompat.getColor(context,R.color.holo_red_dark)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addBackgroundColor(backroundColor)
            .addActionIcon(R.drawable.ic_menu_delete)
            .create()
            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}