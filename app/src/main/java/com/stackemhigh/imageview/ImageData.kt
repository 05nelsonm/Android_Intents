package com.stackemhigh.imageview

import android.net.Uri
import java.io.Serializable

class ImageData(uriData: Uri) : Serializable {
    var uriData = uriData
        private set
    fun setUri(): String {
        return uriData.toString()
    }
    fun getUri(): Uri {
        return Uri.parse(setUri())
    }
}