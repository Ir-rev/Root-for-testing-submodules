package ir.rev.root.utils

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("onClick")
fun View.onClick(onClick: () -> Unit) {
    this.setOnClickListener {
        onClick()
    }
}