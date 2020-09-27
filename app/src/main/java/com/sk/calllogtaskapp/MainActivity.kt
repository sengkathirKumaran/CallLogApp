package com.sk.calllogtaskapp

import android.R.attr
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sk.calllogtaskapp.db.CallDao


class MainActivity : AppCompatActivity() {

    private val YOUR_IMAGE_CODE = 1
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val editText = findViewById<EditText>(R.id.addAge_et)
        editText.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "select a picture"), YOUR_IMAGE_CODE
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == YOUR_IMAGE_CODE) {
            if (resultCode == RESULT_OK) {
                val selectedImageUri: Uri? = data?.data
                println("MA-URI===>" + selectedImageUri.toString())
                println("DATA in DB=>" + mUserViewModel.get())


            }

        }
    }

}