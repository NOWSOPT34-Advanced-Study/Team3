package com.sopt.now.core.util.context

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.snackBar(
    anchorView: View,
    message: String
) {
    Snackbar.make(anchorView, message, Snackbar.LENGTH_SHORT).show()
}

fun Context.colorOf(
    @ColorRes resId: Int
) = ContextCompat.getColor(this, resId)

fun Context.drawableOf(
    @DrawableRes resId: Int
) = ContextCompat.getDrawable(this, resId)

fun Context.pxToDp(px: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        px.toFloat(),
        resources.displayMetrics
    ).toInt()
}
