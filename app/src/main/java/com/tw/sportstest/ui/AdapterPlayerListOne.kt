package com.tw.sportstest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tw.sportstest.R
import com.tw.sportstest.data.PlayerList

class AdapterPlayerListOne : RecyclerView.Adapter<AdapterPlayerListOne.ViewHolder>() {
    var playerList: MutableList<PlayerList> = ArrayList()

    fun setData(data: List<PlayerList>) {
        this.playerList = data as MutableList<PlayerList>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder =
            LayoutInflater.from(parent.context).inflate(R.layout.player_names, parent, false)
        return ViewHolder(holder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = playerList[position]
        holder.tv_playerName.text = context.name_Full.toString()
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_playerName: TextView

        init {
            tv_playerName = itemView.findViewById(R.id.tv_playerName)
        }
    }
}