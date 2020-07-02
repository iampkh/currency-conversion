package com.app.currencyconversion.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.currencyconversion.CurrencyUtil
import com.app.currencyconversion.R
import com.app.currencyconversion.util.helper.Logger
import com.app.currencyconversion.view.adapter.ListRecyclerViewAdapter
import com.app.currencyconversion.viewmodel.AmountViewModel
import com.app.currencyconversion.viewmodel.CurrencyViewModel

class CurrencyListFragment : Fragment() {
    //View declaration
    private lateinit var mRecylerView:RecyclerView

    //Shared Viewmodel for amount change notification
    private val mAmountViewModel : AmountViewModel by activityViewModels()
    private val mCurrencyViewModel : CurrencyViewModel by activityViewModels()


    private fun initView(view:View) {
        mRecylerView = view.findViewById(R.id.listView)
        //val layoutManager = LinearLayoutManager(context)
        //layoutManager.orientation= LinearLayoutManager.VERTICAL
        //mRecylerView.setLayoutManager(layoutManager)
        mRecylerView.setLayoutManager(GridLayoutManager(activity!!,2))
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.currency_list_fragment,container,false);

        initView(view)

        return view;
    }

    override fun onStart() {
        super.onStart()
        mCurrencyViewModel.currenciesAndRatesList.observe(this, Observer { currencyList ->

            Logger.dLog(CurrencyListFragment::class.java.simpleName, "CurrencyModel observer")
            if(currencyList == null) return@Observer;
            val adapter = ListRecyclerViewAdapter(activity!!,currencyList)

            mRecylerView!!.setAdapter(adapter)
        })
    }
}