package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray

private const val TAG = "HomeFragment"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val TOP_STORIES_URL =
    "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=${SEARCH_API_KEY}"

class HomeFragment : Fragment() {

    private val stories = mutableListOf<TopStory>()
    private lateinit var storiesRecyclerView: RecyclerView
    private lateinit var storiesAdapter: StoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val layoutManager = LinearLayoutManager(context)

        storiesRecyclerView = view.findViewById(R.id.top_stories_recycler_view)
        storiesRecyclerView.layoutManager = layoutManager
        storiesAdapter = StoriesAdapter(view.context, stories)
        storiesRecyclerView.adapter = storiesAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchTopStories()
    }

    private fun fetchTopStories() {
        val client = AsyncHttpClient()
        client.get(TOP_STORIES_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch top stories: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.i(TAG, "Successfully fetched articles: $json")
                if(json != null) {
                    val resultsJsonStr = (json.jsonObject.get("results") as JSONArray).toString()
                    val gson = Gson()
                    stories.addAll(gson.fromJson<List<TopStory>>(
                        resultsJsonStr,
                        object : TypeToken<List<TopStory>>() {}.type
                    ))

                    storiesAdapter.notifyDataSetChanged()
                }
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}