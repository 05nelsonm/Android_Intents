package com.stackemhigh.imageview

import android.net.Uri
import java.io.Serializable

class ImageData : Serializable {
    private var uriPath: String? = null
    fun getUriPath(): Uri {
        return Uri.parse(uriPath)
    }
    fun setUriPath(uri: Uri?) {
        uriPath = uri.toString()
    }
}