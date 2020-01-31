package com.info.uitc.freeandr.ui.serverlist

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.info.uitc.freeandr.R
import com.info.uitc.freeandr.model.Server
import com.info.uitc.freeandr.util.Servers
import kotlinx.android.synthetic.main.activity_server_list.*

class ServerListActivity : AppCompatActivity() {

    companion object {
        val RET_SELECTED_SERVER = "RET_SELECTED_SERVER"
    }

    private lateinit var mAdapter: ServerAdapter
    private lateinit var mServerList: List<Server>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_list)

        // button
        btnBack.setOnClickListener {
            onBackPressed()
        }

        // get server list
        mServerList = Servers.getAllServers()

        // recycler view
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mAdapter = ServerAdapter(mServerList) {
            val intent = Intent().apply {
                putExtra(RET_SELECTED_SERVER, it)
            }

            // send selected server back to ConnectActivity
            setResult(Activity.RESULT_OK, intent)

            // finish current activity
            finish()
        }
        recyclerView.adapter = mAdapter
    }
}
