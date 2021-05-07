package com.slakra.spacexnews.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.slakra.spacexnews.R

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String?) {
    val glideImageUrl = url ?: ""
    Glide.with(view.context)
        .load(glideImageUrl)
            .transform(CenterCrop(), RoundedCorners(16))
        .dontAnimate()
        .into(view)
}

@BindingAdapter(
        value = [
            "roundedCorner",
            "topLeftRadius",
            "bottomLeftRadius",
            "topRightRadius",
            "bottomRightRadius",
            "fillColor",
            "strokeColor"],
        requireAll = false)
fun setRoundedCorner(view: View, roundedCorner: Boolean, topLeft: Float?,
                     bottomLeft: Float?, topRight: Float?, bottomRight: Float?,
                     @ColorRes fillColor: Int?, @ColorRes strokeColor: Int?) {
    if (roundedCorner) {
        val radius = view.context.resources.getDimension(R.dimen.margin_24)
        val modal = ShapeAppearanceModel().toBuilder()
        val strColor = strokeColor ?: R.color.white
        if (topLeft == null) {
            modal.setTopLeftCorner(CornerFamily.ROUNDED, radius)
        } else {
            modal.setTopLeftCorner(CornerFamily.ROUNDED, topLeft)
        }

        if (bottomLeft == null) {
            modal.setBottomLeftCorner(CornerFamily.ROUNDED, radius)
        } else {
            modal.setBottomLeftCorner(CornerFamily.ROUNDED, bottomLeft)
        }

        if (topRight == null) {
            modal.setTopRightCorner(CornerFamily.ROUNDED, radius)
        } else {
            modal.setTopRightCorner(CornerFamily.ROUNDED, topRight)
        }

        if (bottomRight == null) {
            modal.setBottomRightCorner(CornerFamily.ROUNDED, radius)
        } else {
            modal.setBottomRightCorner(CornerFamily.ROUNDED, bottomRight)
        }

        val shapeDrawable = MaterialShapeDrawable(modal.build())
        if (fillColor == null) {
            shapeDrawable.fillColor = ContextCompat.getColorStateList(view.context, R.color.white)
        } else {
            shapeDrawable.fillColor = ContextCompat.getColorStateList(view.context, fillColor)
        }

        shapeDrawable.setStroke(2F, ContextCompat.getColor(view.context, strColor))

        ViewCompat.setBackground(view, shapeDrawable)
    }
}

@BindingAdapter("highlightString")
fun highlightText(textView: TextView, input: String) {
    val text: String = textView.text.toString()
    var ofe = text.indexOf(input, 0, true)
    val wordToSpan = SpannableString(text)
    var index = 0
    while (index < text.length && ofe != -1) {
        ofe = text.indexOf(input, index, true)
        if (ofe == -1) {
            break
        }else {
            wordToSpan.setSpan(BackgroundColorSpan(
                    ContextCompat.getColor(textView.context, R.color.highlight)),
                    ofe, ofe+input.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            textView.setText(wordToSpan, TextView.BufferType.SPANNABLE)
        }
        index++
    }
}