package com.info.uitc.freeandr.ui.connect

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.info.uitc.freeandr.R
import kotlinx.android.synthetic.main.activity_connect.*
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import com.info.uitc.freeandr.extensions.setImageResourceByName
import com.info.uitc.freeandr.model.Server
import com.info.uitc.freeandr.ui.serverlist.ServerListActivity


class ConnectActivity : AppCompatActivity() {

    companion object {
        val REQUEST_SELECT_SERVER = 1
    }

    private var connectStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        // change connection status
        circleProgress.setOnClickListener {
            if (connectStatus) {
                // disconnect
                circleProgress.unfinishedStrokeColor = resources.getColor(R.color.colorProgressFinished)
                circleProgress.attributeResourceId = R.drawable.ic_power
                circleProgress.setAdProgress(0)

                txtConnectStatus.text = "Not connected"
            } else {
                // connect
                circleProgress.unfinishedStrokeColor = Color.LTGRAY
                circleProgress.attributeResourceId = R.drawable.ic_close

                val anim = ObjectAnimator.ofInt(circleProgress, "AdProgress", 0, 30)
                anim.interpolator = DecelerateInterpolator()
                anim.duration = 500
                anim.start()

                txtConnectStatus.text = "Connecting"
            }

            connectStatus = !connectStatus
        }

        cardServer.setOnClickListener {
            // change vpn server
            val intent = Intent(this, ServerListActivity::class.java)
            startActivityForResult(intent, REQUEST_SELECT_SERVER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SELECT_SERVER) {
            if (resultCode == Activity.RESULT_OK) {
                val server: Server? = data?.getParcelableExtra(ServerListActivity.RET_SELECTED_SERVER)
                server?.let {
                    imgCountryFlag.setImageResourceByName(it.countryCode)
                    txtCountryName.text = it.countryName
                }
            }
        }
    }
}
