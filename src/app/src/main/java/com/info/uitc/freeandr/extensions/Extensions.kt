package com.info.uitc.freeandr.extensions

import android.widget.ImageView
import kotlinx.android.synthetic.main.item_server.view.*

fun ImageView.setImageResourceByName(resName: String) {
    val imageId = context.resources.getIdentifier("flag_" + resName,
        "drawable", context.packageName)
    setImageResource(imageId)
}