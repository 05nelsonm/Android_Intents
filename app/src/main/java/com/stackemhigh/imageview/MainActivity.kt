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
        val STRING_DATA_KEY = "poiub24h972qb4toquh0kbfuio"
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
    private val imageDataList = mutableListOf<ImageData>()

    private var index: Int = -1

    private val context = this

    // Initialize Boolean value as false for use in cases of app initially starting
    // as well as if the user selects the back button instead of selecting an image
    var dataWasPassed: Boolean = false

    override fun onResume() {
        super.onResume()

        // During on resume lifecycle, will add a TextView to the UI in the event that
        // the user selected an image.
        if (dataWasPassed) {
            populateScrollView()
            dataWasPassed = false
        }
    }

    // Programmatic construction of a TextView
    private fun createTextView(stringUri: String, index: Int): TextView {
        val view = TextView(this)

        view.setOnClickListener {
            val imageTag = imageDataList[index]
            val intent = Intent(this, ImageDetailsActivity::class.java)
            imageTag.getUriPath()
            intent.putExtra(STRING_DATA_KEY, imageTag)
            startActivity(intent)
            println(intent)
        }

        view.text = "$stringUri, $index"
        view.textSize = 18f
        return view
    }

    // Populates the scroll views' child LinearLayout with data
    private fun populateScrollView() {
        val view = createTextView(imageDataList[index].toString(), index)
        scroll_view_layout.addView(view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            println("onActivityResult data: " + data?.data)

            // Creates a null ImageData object
            val imageData = ImageData()

            // Uses the ImageData setter to set data
            imageData.setUriPath(data?.data)

            // Adds the ImageData to the list
            imageDataList.add(imageData)

            // Increases index by increment of 1 to track elements of the list where this
            // data is stored.
            index++

            // Allow for onResume override to issue populateScrollView()
            dataWasPassed = true
        }
    }
}