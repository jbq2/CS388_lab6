package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val ARTICLE_EXTRA = "ARTICLE_EXTRA"
private const val TAG = "ArticleAdapter"

class ArticleAdapter(private val context: Context, private val articles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount() = articles.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val mediaImageView = itemView.findViewById<ImageView>(R.id.mediaImage)
        private val titleTextView = itemView.findViewById<TextView>(R.id.storyTitle)
        private val abstractTextView = itemView.findViewById<TextView>(R.id.storyAbstract)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(article: Article) {
            titleTextView.text = article.headline?.main
            abstractTextView.text = article.abstract

            val prefs = itemView.context.getSharedPreferences("com.codepath.articlesearch", Context.MODE_PRIVATE)
            val setSpecialFont = prefs.getBoolean(itemView.context.getString(R.string.font_pref), false)
            if(setSpecialFont) {
                titleTextView.typeface = Typeface.DEFAULT_BOLD
                abstractTextView.typeface = Typeface.DEFAULT_BOLD
            }
            else {
                titleTextView.typeface = Typeface.DEFAULT
                abstractTextView.typeface = Typeface.DEFAULT
            }

            Glide.with(context)
                .load(article.mediaImageUrl)
                .into(mediaImageView)
        }

        override fun onClick(v: View?) {
            // Get selected article
            val article = articles[absoluteAdapterPosition]

            // Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ARTICLE_EXTRA, article)
            context.startActivity(intent)
        }
    }
}