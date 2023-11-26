package com.example.tugas_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class profil_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        val headingNews : TextView = findViewById(R.id.heading)
        val mainNews : TextView = findViewById(R.id.news)
        val imageNews : ImageView = findViewById(R.id.image_heading)

        val bundle : Bundle?= intent.extras
        val heading = bundle!!.getString("heading")
        val imageId = bundle.getInt("imageId")
        val news = bundle.getString("news")

        headingNews.text = heading
        mainNews.text = news
        imageNews.setImageResource(imageId)

    }
}