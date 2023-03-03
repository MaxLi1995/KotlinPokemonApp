package com.maxli.pokedex.ui.home

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Point
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maxli.pokedex.R

// created based on https://noties.io/blog/2019/04/23/gradient-messenger/index.html
class GradientDecoration(
    recyclerView: RecyclerView
) : RecyclerView.ItemDecoration() {

    private val drawable: Drawable

    init {
        drawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            recyclerView.resources.getIntArray(R.array.list_gradient_color)
        )
            .also { initDrawableBounds(recyclerView, it) }
    }

    private fun initDrawableBounds(view: View, drawable: Drawable) {
        view.viewTreeObserver.addOnGlobalLayoutListener {
            drawable.setBounds(0, 0, view.width, view.height)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        0.until(parent.childCount)
            .map { parent.getChildAt(it) }
            .mapNotNull { parent.findContainingViewHolder(it) }
            .forEach {
                val view = it.itemView.findViewById<TextView>(R.id.pokemon_name)
                val point = Point()

                point.set(view.left, view.top)
                var p = view.parent

                while (p != null && p != parent) {
                    val v = p as View
                    point.x += v.left
                    point.y += v.top
                    p = v.parent
                }
                with(c.save()) {
                    val corners = floatArrayOf(40f, 40f, 40f, 40f, 40f, 40f, 40f, 40f)

                    val path = Path()
                    path.addRoundRect(
                        RectF(
                            point.x.toFloat(),
                            point.y.toFloat(),
                            point.x + view.width.toFloat(),
                            point.y + view.height.toFloat()
                        ), corners, Path.Direction.CW
                    )
                    c.clipPath(path)
                    drawable.draw(c)
                    c.restoreToCount(this)
                }
            }
    }
}