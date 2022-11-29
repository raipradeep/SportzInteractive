package com.tw.sportstest.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import com.tw.sportstest.R
import com.tw.sportstest.data.Batting
import com.tw.sportstest.data.Bowling
import com.tw.sportstest.data.PlayerList
import com.tw.sportstest.data.TeamsData
import com.tw.sportstest.retrofitClient.ApiClient
import com.tw.sportstest.retrofitClient.ApiService
import com.tw.sportstest.retrofitClient.JsonUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var apiService: ApiService
    private var teamsList: ArrayList<TeamsData> = ArrayList()
    private var indiaLIst: ArrayList<PlayerList> = ArrayList()
    private var battingListInd: ArrayList<Batting> = ArrayList()
    private var battingListNz: ArrayList<Batting> = ArrayList()

    private var bowlingListInd: ArrayList<Bowling> = ArrayList()
    private var bowlingListNz: ArrayList<Bowling> = ArrayList()

    private var nzList: ArrayList<PlayerList> = ArrayList()
    lateinit var adapterPlayer: AdapterPlayerListOne
    lateinit var adapterSecond: AdapterSecond
    lateinit var adapterThird: AdapterThird

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapterPlayer = AdapterPlayerListOne()
        adapterSecond = AdapterSecond()
        adapterThird = AdapterThird()

        apiService = ApiClient.getInterface()

        llFullDetails.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("indiaList", indiaLIst)
            bundle.putSerializable("newLandList", nzList)

            bundle.putSerializable("battingListInd", battingListInd)
            bundle.putSerializable("bowlingListInd", bowlingListInd)

            bundle.putSerializable("battingListNz", battingListNz)
            bundle.putSerializable("bowlingListNz", bowlingListNz)

            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }

        getGlobalValues()
    }

    @SuppressLint("CheckResult")
    fun getGlobalValues() {
        apiService.getData.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn { throwable: Throwable ->
                Log.e(Constraints.TAG, "apply: " + throwable.message)
                null
            }.subscribe({ responseBody: ResponseBody ->
                teamsList.clear()
                battingListNz.clear()
                battingListInd.clear()
                bowlingListNz.clear()
                bowlingListInd.clear()
                val data = responseBody.string()
                val jsonObject = JsonUtil.mainjson(data)
                val teams = jsonObject.getJSONObject("Teams")
                val itTeam: Iterator<String> = teams.keys()

                val matchDetailObj = jsonObject.getJSONObject("Matchdetail")
                val venueObj = matchDetailObj.getJSONObject("Venue")
                val matchObj = matchDetailObj.getJSONObject("Match")
                tv_venueName.text = "Venue:- " + venueObj.getString("Name")
                tv_timeDate.text =
                    "Date:- " + matchObj.getString("Date") + " Time:-" + matchObj.getString("Time")
                while (itTeam.hasNext()) {
                    val keyTeam = itTeam.next()
                    if (teams.get(keyTeam) is JSONObject) {
                        val obj = teams.getJSONObject(keyTeam)
                        teamsList.add(
                            TeamsData(
                                obj.getString("Name_Full"), obj.getString("Name_Short")
                            )
                        )
                        val playerObject = obj.getJSONObject("Players")
                        val playersName: Iterator<String> = playerObject.keys()
                        while (playersName.hasNext()) {
                            val keyPl = playersName.next()
                            if (playerObject.get(keyPl) is JSONObject) {
                                val objPla = playerObject.getJSONObject(keyPl)

                                val name = objPla.getString("Name_Full")
                                var captain: Boolean = false
                                var keeper: Boolean = false
                                if (objPla.has("Iscaptain")) {
                                    captain = objPla.getBoolean("Iscaptain")
                                }
                                if (objPla.has("Iskeeper")) {
                                    keeper = objPla.getBoolean("Iskeeper")
                                }
                                val batting = objPla.getJSONObject("Batting")
                                val bowling = objPla.getJSONObject("Bowling")
                                if (obj.getString("Name_Short") == "IND") {

                                    val style = batting.getString("Style")
                                    val average = batting.getString("Average")
                                    val strikerate = batting.getString("Strikerate")
                                    val runs = batting.getString("Runs")

                                    battingListInd.add(
                                        Batting(
                                            style.toString(),
                                            average.toString(),
                                            strikerate.toString(),
                                            runs.toString()
                                        )
                                    )
                                    val bowlStyle = bowling.getString("Style")
                                    val bowlAverage = bowling.getString("Average")
                                    val bowlStrikerate = bowling.getString("Economyrate")
                                    val bowlRuns = bowling.getString("Wickets")

                                    bowlingListInd.add(
                                        Bowling(
                                            bowlStyle.toString(),
                                            bowlAverage.toString(),
                                            bowlStrikerate.toString(),
                                            bowlRuns.toString()
                                        )
                                    )
                                    indiaLIst.add(PlayerList(name, captain, keeper))
                                } else {
                                    nzList.add(PlayerList(name, captain, keeper))
                                    val style = batting.getString("Style")
                                    val average = batting.getString("Average")
                                    val strikerate = batting.getString("Strikerate")
                                    val runs = batting.getString("Runs")
                                    battingListNz.add(
                                        Batting(
                                            style.toString(),
                                            average.toString(),
                                            strikerate.toString(),
                                            runs.toString()
                                        )
                                    )
                                    val bowlStyle = bowling.getString("Style")
                                    val bowlAverage = bowling.getString("Average")
                                    val bowlStrikerate = bowling.getString("Economyrate")
                                    val bowlRuns = bowling.getString("Wickets")

                                    bowlingListNz.add(
                                        Bowling(
                                            bowlStyle.toString(),
                                            bowlAverage.toString(),
                                            bowlStrikerate.toString(),
                                            bowlRuns.toString()
                                        )
                                    )
                                }

                            }
                        }

                    }
                }
                tv_teamNameOne.text = teamsList[0].fullName
                tv_teamNameTwo.text = teamsList[1].fullName
                adapterSecond.setData(indiaLIst, battingListInd, bowlingListInd)
                adapterThird.setData(nzList, battingListNz, bowlingListNz)

            }) { throwable: Throwable ->
                Log.e(Constraints.TAG, "apply: " + throwable.message)
            }
    }

}