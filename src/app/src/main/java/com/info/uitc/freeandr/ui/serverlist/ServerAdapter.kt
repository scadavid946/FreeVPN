package com.info.uitc.freeandr.ui.serverlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.info.uitc.freeandr.R
import com.info.uitc.freeandr.extensions.setImageResourceByName
import com.info.uitc.freeandr.model.Server
import kotlinx.android.synthetic.main.item_server.view.*

class ServerAdapter(private val mServerList: List<Server>,
                    private val mListener: (Server) -> Unit) : RecyclerView.Adapter<ServerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_server, parent, false))
    }

    override fun getItemCount(): Int = mServerList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mServerList[position], mListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(server: Server, listener: (Server) -> Unit) {
            itemView.txtCountryName.text = server.countryName
            itemView.imgCountryFlag.setImageResourceByName(server.countryCode)
            itemView.setOnClickListener {
                listener(server)
            }
        }
    }


}