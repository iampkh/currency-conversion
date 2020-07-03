package com.app.currencyconversion.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.currencyconversion.CurrencyUtil
import com.app.currencyconversion.R
import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.util.helper.Logger
import com.app.currencyconversion.util.helper.WorkManagerHelper
import com.app.currencyconversion.viewmodel.CurrencyViewModel

class MainActivity : AppCompatActivity() {

    //Button to switch between fragment.
    private lateinit var mShowMoreBtn:Button

    /**
     * Display's action bar with custom title and subtitle.
     */
    private fun updateActionBar(){
        val actionBar = supportActionBar
        actionBar!!.title= "Money converter"
        actionBar.subtitle = "convert's money using currencylayer.com"
        actionBar.elevation = 4.0f
    }


    override fun onStart() {
        super.onStart()

        if(!WorkManagerHelper.isSyncStoppedByUser(this)) {
            WorkManagerHelper.initiateLiveSyncPeriodic(this, true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateActionBar()

        mShowMoreBtn = findViewById<Button>(R.id.show_more_less)
        mShowMoreBtn.setOnClickListener(View.OnClickListener {view ->
            val resultFrag = findViewById<FragmentContainerView>(R.id.result_input_fragment)
            val resultListFrag = findViewById<FragmentContainerView>(R.id.result_list_currency_fragment)

            if(resultFrag.visibility == View.GONE){
                resultFrag.visibility = View.VISIBLE
                resultListFrag.visibility = View.GONE
                mShowMoreBtn.setText("Show More")
            }else {
                resultFrag.visibility = View.GONE
                resultListFrag.visibility = View.VISIBLE
                mShowMoreBtn.setText("Show Less")
            }

        })

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sync_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sync_now -> {
                WorkManagerHelper.initiateLiveSyncNow(this,true)
                Logger.dLog(this::class.java.simpleName,"MenuSelected : SyncNow")
            }
            R.id.sync_min30 -> {
                WorkManagerHelper.initiateLiveSyncPeriodic(this,true)
                Logger.dLog(this::class.java.simpleName,"MenuSelected : Sync 30 min")
            }
            R.id.sync_stop -> {
                WorkManagerHelper.initiateLiveSyncPeriodic(this,false)
                Logger.dLog(this::class.java.simpleName,"MenuSelected : SyncStop")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
