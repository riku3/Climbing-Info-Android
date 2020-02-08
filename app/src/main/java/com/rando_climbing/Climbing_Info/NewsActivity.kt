package com.rando_climbing.Climbing_Info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.rando_climbing.Climbing_Info.Fragment.NewsSecondFragment
import com.rando_climbing.Climbing_Info.Fragment.NewsThirdFragment
import com.rando_climbing.Climbing_Info.Fragment.NewsFirstFragment
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_news.*
import android.os.AsyncTask
import androidx.appcompat.app.AlertDialog
import java.io.Serializable

class NewsActivity : AppCompatActivity() {

    var dataList: MutableList<Article> = mutableListOf()
    var firstDataList: MutableList<Article> = mutableListOf()
    var secondDataList: MutableList<Article> = mutableListOf()
    var thirdDataList: MutableList<Article> = mutableListOf()
    var jsonSting = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val API_URL = "https://q0jxf9yhbi.execute-api.ap-northeast-1.amazonaws.com/prod/articles"
        HitAPITask().execute(API_URL)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        var bundle = Bundle()

        bundle.putSerializable("first_list", firstDataList as Serializable)
        bundle.putSerializable("second_list", secondDataList as Serializable)
        bundle.putSerializable("third_list", thirdDataList as Serializable)

        var firstFragment = NewsFirstFragment()
        var secondFragment = NewsSecondFragment()
        var thirdFragment = NewsThirdFragment()

        firstFragment.setArguments(bundle)
        secondFragment.setArguments(bundle)
        thirdFragment.setArguments(bundle)

        adapter.addFragment(firstFragment,"HOME")
        adapter.addFragment(secondFragment, "BLOG")
        adapter.addFragment(thirdFragment, "WORLD")

        viewPager.adapter = adapter
    }

    inner class HitAPITask: AsyncTask<String, String, String>() {
        lateinit var alertDialog: AlertDialog.Builder

        override fun onPreExecute() {
            super.onPreExecute()
            alertDialog = AlertDialog.Builder(this@NewsActivity)
        }

        override fun doInBackground(vararg params: String?): String {
            val API_URL = params[0]!!

            val jsonString = API_URL.httpGet().response().second.data.toString(Charsets.UTF_8)
            return jsonString
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            jsonSting = result!!

            if (jsonSting == "") {
                alertDialog
                        .setTitle("接続失敗")
                        .setMessage("インターネットに接続されていない可能性があります。")
                        .setPositiveButton("アプリを閉じる", { dialog, which ->
                            finish()
                        })
                        .show()
                return
            }

            dataList = jacksonObjectMapper().readValue(jsonSting)

            for (data in dataList) {
                when (data.category) {
                    "1" -> {
                        firstDataList.add(data)
                    }
                    "2" -> {
                        secondDataList.add(data)
                    }
                    "3" -> {
                        thirdDataList.add(data)
                    }
                }
            }

            setupViewPager(viewpager)
            tabs!!.setupWithViewPager(viewpager)
        }
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }
}
