package io.arunbuilds.rickandmorty.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.arunbuilds.rickandmorty.R
import io.arunbuilds.rickandmorty.databinding.LayoutEmptyBinding
import io.arunbuilds.rickandmorty.databinding.LayoutErrorBinding
import io.arunbuilds.rickandmorty.databinding.LayoutLoadingBinding
import io.arunbuilds.rickandmorty.databinding.LceeRecyclerLayoutBinding

/**
 * Custom recycler view with integrated error, empty and loading view
 */
class LCEERecyclerView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)


    private val binding: LceeRecyclerLayoutBinding =
        LceeRecyclerLayoutBinding.inflate(LayoutInflater.from(context), this)

    private val errorBinding: LayoutErrorBinding = binding.customErrorView
    private val emptyBinding: LayoutEmptyBinding = binding.customEmptyView
    private val loadingBinding: LayoutLoadingBinding = binding.customOverlayView

    // expose the recycler view
    val recyclerView: RecyclerView
    get() = binding.customRecyclerView

    var errorText: String = ""
        set(value) {
            field = value
            errorBinding.errorMsgText.text = value
        }

    var emptyText: String = ""
        set(value) {
            field = value
            emptyBinding.emptyMessage.text = value
        }

    @DrawableRes
    var errorIcon = 0
        set(value) {
            field = value
            errorBinding.errorImage.setImageResource(value)
        }

    @DrawableRes
    var emptyIcon = 0
        set(value) {
            field = value
            emptyBinding.emptyImage.setImageResource(value)
        }

    init {

        context.theme.obtainStyledAttributes(
            attrs,
         R.styleable.CustomRecyclerView,
            0,
            0
        ).apply {
            try {
                errorText = getString(R.styleable.CustomRecyclerView_errorText) ?: "Something went wrong"
                emptyText =
                    getString(R.styleable.CustomRecyclerView_emptyText) ?: "Nothing to show"
                errorIcon = getResourceId(
                    R.styleable.CustomRecyclerView_emptyIcon,
                    R.drawable.ic_error
                )
                emptyIcon =
                    getResourceId(R.styleable.CustomRecyclerView_emptyIcon, R.drawable.ic_characters)
            } finally {
                recycle()
            }
        }
    }

    fun isErrorViewShown() = errorBinding.root.visibility == View.VISIBLE

    fun showEmptyView(msg: String? = null) {
        emptyText = msg ?: emptyText
        loadingBinding.root.visibility = View.GONE
        errorBinding.root.visibility = View.GONE

        emptyBinding.root.visibility = View.VISIBLE
    }

    fun showErrorView(msg: String? = null) {
        errorText = msg ?: errorText
        loadingBinding.root.visibility = View.GONE
        emptyBinding.root.visibility = View.GONE

        errorBinding.root.visibility = View.VISIBLE
    }

    fun showLoadingView() {
        emptyBinding.root.visibility = View.GONE
        errorBinding.root.visibility = View.GONE

        loadingBinding.root.visibility = View.VISIBLE
    }

    fun hideAllViews() {
        loadingBinding.root.visibility = View.GONE
        errorBinding.root.visibility = View.GONE
        emptyBinding.root.visibility = View.GONE
        binding.customRecyclerView.visibility = View.VISIBLE
    }

    fun setOnRetryClickListener(callback: () -> Unit) {
        errorBinding.retryButton.setOnClickListener {
            callback()
        }
    }
}