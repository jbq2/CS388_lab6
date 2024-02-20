package com.codepath.articlesearch

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StoriesAdapter(private val context: Context, private val stories: List<TopStory>) :
    RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView = itemView.findViewById<TextView>(R.id.storyTitle)
        val abstractTextView = itemView.findViewById<TextView>(R.id.storyAbstract)
        val bylineView = itemView.findViewById<TextView>(R.id.storyByline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_top_story, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onBindViewHolder(holder: StoriesAdapter.ViewHolder, position: Int) {
        val topStory = stories[position]
        holder.titleTextView.text = topStory.title
        holder.bylineView.text = topStory.byline
        holder.abstractTextView.text = topStory.abstract

        val prefs = holder.itemView.context.getSharedPreferences("com.codepath.articlesearch", Context.MODE_PRIVATE)
        val setSpecialFont = prefs.getBoolean(holder.itemView.context.getString(R.string.font_pref), false)
        if(setSpecialFont) {
            holder.titleTextView.typeface = Typeface.DEFAULT_BOLD
            holder.bylineView.typeface = Typeface.DEFAULT_BOLD
            holder.abstractTextView.typeface = Typeface.DEFAULT_BOLD
        }
        else {
            holder.titleTextView.typeface = Typeface.DEFAULT
            holder.bylineView.typeface = Typeface.DEFAULT
            holder.abstractTextView.typeface = Typeface.DEFAULT
        }
    }
}