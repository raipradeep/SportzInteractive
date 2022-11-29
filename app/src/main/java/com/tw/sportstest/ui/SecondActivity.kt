package com.tw.sportstest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.tw.sportstest.R
import com.tw.sportstest.data.Batting
import com.tw.sportstest.data.Bowling
import com.tw.sportstest.data.PlayerList
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    lateinit var adapterSecond: AdapterSecond
    lateinit var adapterThird: AdapterThird

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        adapterSecond = AdapterSecond()
        adapterThird = AdapterThird()
        var bundle = intent.getBundleExtra("bundle")

        val indList: ArrayList<PlayerList>? =
            bundle?.getSerializable("indiaList") as ArrayList<PlayerList>?
        val nzLandList: ArrayList<PlayerList>? =
            bundle?.getSerializable("newLandList") as ArrayList<PlayerList>?

        val battingListInd: ArrayList<Batting>? =
            bundle?.getSerializable("battingListInd") as ArrayList<Batting>?
        val bowlingListInd: ArrayList<Bowling>? =
            bundle?.getSerializable("bowlingListInd") as ArrayList<Bowling>?

        val battingListNz: ArrayList<Batting>? =
            bundle?.getSerializable("battingListNz") as ArrayList<Batting>?
        val bowlingListNz: ArrayList<Bowling>? =
            bundle?.getSerializable("bowlingListNz") as ArrayList<Bowling>?

        adapterSecond.setData(indList!!, battingListInd!!, bowlingListInd!!)

        adapterThird.setData(nzLandList!!, battingListNz!!, bowlingListNz!!)


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recylewViewInda.layoutManager = layoutManager
        recylewViewInda.itemAnimator = DefaultItemAnimator()
        recylewViewInda.adapter = adapterSecond

        val layoutManager2 = LinearLayoutManager(this)
        layoutManager2.orientation = LinearLayoutManager.VERTICAL
        recylewViewNewLand.layoutManager = layoutManager2
        recylewViewNewLand.itemAnimator = DefaultItemAnimator()
        recylewViewNewLand.adapter = adapterThird

    }
}