package com.tw.sportstest.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tw.sportstest.R
import com.tw.sportstest.data.Batting
import com.tw.sportstest.data.Bowling
import com.tw.sportstest.data.PlayerList

class AdapterThird : RecyclerView.Adapter<AdapterThird.ViewHolder>() {

    lateinit var context: Context
    var third: MutableList<PlayerList> = ArrayList()
    var battingNew: MutableList<Batting> = ArrayList()
    var bowlingListNew: MutableList<Bowling> = ArrayList()

    fun setData(
        india: List<PlayerList>,
        battingNew: List<Batting>,
        bowlingListNew: List<Bowling>
    ) {
        this.third = india as MutableList<PlayerList>
        this.battingNew = battingNew as MutableList<Batting>
        this.bowlingListNew = bowlingListNew as MutableList<Bowling>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.player_with_team, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = third[position]

        val name = data.name_Full
        val iskeeper = data.Iskeeper
        val isCap = data.isCaptain
        holder.tv_playersName.text = name
        if (iskeeper) {
            holder.tv_playersName.text = data.name_Full + " ( wk )"
        }
        if (isCap) {
            holder.tv_playersName.text = data.name_Full + " ( ck )"
        }
        holder.clickedTeamA.setOnClickListener {
            openSeniorityBottomSheet(position)
        }
    }

    override fun getItemCount(): Int {
        return third.size
    }

    class ViewHolder(viewHolder: View) : RecyclerView.ViewHolder(viewHolder) {

        var tv_playersName: TextView
        var clickedTeamA: CardView

        init {
            tv_playersName = itemView.findViewById(R.id.tv_playersName)
            clickedTeamA = itemView.findViewById(R.id.clickedTeamA)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun openSeniorityBottomSheet(posi: Int) {
        val alertDialog = BottomSheetDialog(context)
        val dialogView: View = LayoutInflater.from(context).inflate(R.layout.details_clicked, null)
        alertDialog.setContentView(dialogView)
        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()

        val battingStyle = dialogView.findViewById<TextView>(R.id.tv_battingStyle)
        val battingAverage = dialogView.findViewById<TextView>(R.id.tv_battingAverage)
        val battingStrikerate = dialogView.findViewById<TextView>(R.id.tv_battingStrikerate)
        val battingRuns = dialogView.findViewById<TextView>(R.id.tv_battingRuns)
        val bowlingStyle = dialogView.findViewById<TextView>(R.id.tv_bowlingStyle)
        val bowlingAverage = dialogView.findViewById<TextView>(R.id.tv_bowlingAverage)
        val bowlingEco = dialogView.findViewById<TextView>(R.id.tv_bowlingEco)
        val bowlingWickets = dialogView.findViewById<TextView>(R.id.tv_bowlingWickets)


        battingStyle.text = "Style:- " + battingNew[posi].style
        battingAverage.text = "Average:- " + battingNew[posi].average
        battingStrikerate.text = "StrikeRate:- " + battingNew[posi].strikerate
        battingRuns.text = "Runs:- " + battingNew[posi].runs

        bowlingStyle.text = "Style:- " + bowlingListNew[posi].style
        bowlingAverage.text = "Average:- " + bowlingListNew[posi].average
        bowlingEco.text = "Economy:- " + bowlingListNew[posi].economyrate
        bowlingWickets.text = "Wickets:- " + bowlingListNew[posi].wickets

    }
}
