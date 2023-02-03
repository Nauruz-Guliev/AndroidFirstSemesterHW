package ru.kpfu.itis.hw_android_2022.recyclerviewComponents

import android.graphics.Canvas
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.hw_android_2022.models.convertDpToPx


class SwipeListener(
    view: View,

) :
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        private val scrollLimitX = (-80).convertDpToPx(view.context)
        private var currentScrollX = 0
        private var currentScrollXWhenInActive = 0
        private var initXWhenInActive = 0f
        private var firstInActive = false


        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = 0
            val swipeFlags = ItemTouchHelper.RIGHT
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
            return Integer.MAX_VALUE.toFloat()
        }

        override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
            return Integer.MAX_VALUE.toFloat()
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
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && viewHolder !is MusicAdapter.HeaderViewHolder) {
                if (dX == 0f) {
                    currentScrollX = viewHolder.itemView.scrollX
                    firstInActive = true
                }
                if (isCurrentlyActive) {
                    var scrollOffset = currentScrollX + (-dX).toInt()
                    if (scrollOffset < scrollLimitX) {
                        scrollOffset = scrollLimitX
                    }
                    viewHolder.itemView.scrollTo(scrollOffset, 0)
                } else {
                    if (firstInActive) {
                        firstInActive = false
                        currentScrollXWhenInActive = viewHolder.itemView.scrollX
                        initXWhenInActive = dX
                    }
                    if (viewHolder.itemView.scrollX > scrollLimitX) {
                        viewHolder.itemView.scrollTo(
                            (currentScrollXWhenInActive * dX / initXWhenInActive).toInt(),
                            0
                        )
                    }
                }
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            if (viewHolder.itemView.scrollX < scrollLimitX) {
                viewHolder.itemView.scrollTo(scrollLimitX, 0)
            }
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }




        /*  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
              val deletedItem: UIModel =
                  Repository.getDataList(view.context).get(viewHolder.adapterPosition)

              val position = viewHolder.adapterPosition

              Repository.deleteItem(position)
              adapter.notifyItemRemoved(viewHolder.adapterPosition)

              Snackbar.make(view, "Item has been deleted", Snackbar.LENGTH_LONG)
                  .setAction(
                      "Undo"
                  ) {
                      Repository.addItem(deletedItem, position)

                      adapter.notifyItemInserted(position)
                  }.show()
          }
         */
    })

