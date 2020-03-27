package com.stackemhigh.imageview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stackemhigh.imageview.MainActivity.Companion.STRING_DATA_KEY
import kotlinx.android.synthetic.main.activity_image_details.*
import android.content.Intent

class ImageDetailsActivity : AppCompatActivity() {

    private var myObject: ImageData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)

        myObject = intent.getSerializableExtra(STRING_DATA_KEY) as ImageData
        image_details_view_image.setImageURI(myObject?.getUriPath())
        text_uri.text = myObject?.toString()
    }
}