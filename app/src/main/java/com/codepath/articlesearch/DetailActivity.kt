package com.codepath.articlesearch

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var bylineTextView: TextView
    private lateinit var abstractTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.storyTitle)
        bylineTextView = findViewById(R.id.mediaByline)
        abstractTextView = findViewById(R.id.storyAbstract)
        val prefs = getSharedPreferences("com.codepath.articlesearch", Context.MODE_PRIVATE)
        val setSpecialFont = prefs.getBoolean(getString(R.string.font_pref), false)
        if(setSpecialFont) {
            titleTextView.typeface = Typeface.DEFAULT_BOLD
            bylineTextView.typeface = Typeface.DEFAULT_BOLD
            abstractTextView.typeface = Typeface.DEFAULT_BOLD
        }
        else {
            titleTextView.typeface = Typeface.DEFAULT
            bylineTextView.typeface = Typeface.DEFAULT
            abstractTextView.typeface = Typeface.DEFAULT
        }

        val article = intent.getSerializableExtra(ARTICLE_EXTRA) as Article

        // Set title and abstract information for the article
        titleTextView.text = article.headline?.main
        bylineTextView.text = article.byline?.original
        abstractTextView.text = article.abstract

        // Load the media image
        Glide.with(this)
            .load(article.mediaImageUrl)
            .into(mediaImageView)
    }
}