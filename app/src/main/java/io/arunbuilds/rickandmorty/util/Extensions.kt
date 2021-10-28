package io.arunbuilds.rickandmorty.util

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.textview.MaterialTextView
import io.arunbuilds.rickandmorty.R
import io.arunbuilds.rickandmorty.model.response.characters.Status


const val MILLISECONDS_IN_ONE_SECOND = 1000L

/**
 * Show the view  (visibility = View.VISIBLE)
 */
fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.showForSecond(seconds: Long): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    postDelayed({ hide() }, seconds * MILLISECONDS_IN_ONE_SECOND)
    return this
}

/**
 * Show the view if [condition] returns true
 * (visibility = View.VISIBLE)
 */
inline fun View.showIf(condition: () -> Boolean): View {
    if (visibility != View.VISIBLE && condition()) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Remove the view (visibility = View.GONE)
 */
fun View.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

/**
 * Remove the view if [predicate] returns true
 * (visibility = View.GONE)
 */
inline fun View.hideIf(predicate: () -> Boolean): View {
    if (visibility != View.GONE && predicate()) {
        visibility = View.GONE
    }
    return this
}


fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, observer: (T) -> Unit) =
    liveData?.observe(this, Observer(observer))

fun MaterialTextView.status(status: Status) {
    text = status.toString()
    when (status) {
        Status.ALIVE -> setDrawableLeft(R.color.green_a700)
        Status.DEAD -> setDrawableLeft(R.color.red_a700)
        Status.UNKNOWN -> setDrawableLeft(R.color.gray_700)
    }
}

fun MaterialTextView.setDrawableLeft(@ColorRes res: Int) {
    if (compoundDrawables[0] == null) return
    compoundDrawables[0]?.setTint(ContextCompat.getColor(context, res))
}
