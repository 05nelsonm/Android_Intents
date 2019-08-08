package com.stackemhigh.imageview

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_REQUEST_CODE = 3324
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // When button is pressed, sends user to different activity to
        // select a picture to be retrieved.
        button_select_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            println(intent)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, IMAGE_REQUEST_CODE)
            }
        }
    }

    // Initialize mutable list to store Image Data
    private val stringUriList = mutableListOf<String>()

    private var index: Int = -1

    // Initialize Boolean value as false for use in cases of app initially starting
    // as well as if the user selects the back button instead of selecting an image
    var dataWasPassed: Boolean = false

    override fun onResume() {
        super.onResume()

        // During on resume lifecycle, refreshes scroll view's child layout
        // with fresh list of Image Data
        if (dataWasPassed) {
            populateScrollView()
            dataWasPassed = false
        }
    }

    // Programmatic construction of a TextView
    fun createTextView(stringUri: String, index: Int): TextView {
        val view = TextView(this)
        view.text = "$stringUri, $index"
        view.textSize = 18f
        return view
    }

    // Populates the scroll views' child LinearLayout with data
    fun populateScrollView() {
        val view = createTextView(stringUriList[index], index)
        scroll_view_layout.addView(view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Retrieve ImageData
            println("onActivityResult data: " + data?.data)
            stringUriList.add(data?.data.toString())
            index++

            // Allow for onResume override to issue populateScrollView()
            dataWasPassed = true
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}