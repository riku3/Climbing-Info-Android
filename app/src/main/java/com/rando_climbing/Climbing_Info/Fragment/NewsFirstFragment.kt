package com.rando_climbing.Climbing_Info.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.rando_climbing.Climbing_Info.Article
import com.rando_climbing.Climbing_Info.R
import com.rando_climbing.Climbing_Info.NewsRecyclerViewAdapter
import com.rando_climbing.Climbing_Info.ArticleActivity

class NewsFirstFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.first_recycler_list)
        val adapter = NewsRecyclerViewAdapter(createDataList(), object : NewsRecyclerViewAdapter.ListListener {
            override fun onClickListItem(tappedView: View, article: Article) {
                this@NewsFirstFragment.onClickListItem(tappedView, article)
            }
        })
        val itemDecoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun createDataList(): List<Article> {
        return arguments!!.getSerializable("first_list") as MutableList<Article>
    }

    fun onClickListItem(tappedView: View, article: Article) {
        var intent = Intent(getActivity(), ArticleActivity::class.java)
        intent.putExtra("link", article.link)
        startActivity(intent)
    }
}
