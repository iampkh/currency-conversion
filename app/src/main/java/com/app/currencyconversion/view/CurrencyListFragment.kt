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
import com.app.currencyconversion.model.data.Amount
import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.util.helper.Logger
import com.app.currencyconversion.view.adapter.ListRecyclerViewAdapter
import com.app.currencyconversion.viewmodel.AmountViewModel
import com.app.currencyconversion.viewmodel.CurrencyViewModel

class CurrencyListFragment : Fragment() {
    //View declaration
    private lateinit var mRecylerView:RecyclerView
    private lateinit var mAdapter:ListRecyclerViewAdapter

    //Currency
    lateinit var mCurrencyList:List<Currency>

    //Shared Viewmodel for amount change notification
    private val mAmountViewModel : AmountViewModel by activityViewModels()
    private val mCurrencyViewModel : CurrencyViewModel by activityViewModels()


    private fun initView(view:View) {
        mRecylerView = view.findViewById(R.id.listView)
        mRecylerView.setLayoutManager(GridLayoutManager(activity!!,2))
        mAdapter = ListRecyclerViewAdapter(activity!!, emptyList())
        mRecylerView!!.setAdapter(mAdapter)
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

            setCurrencyList(currencyList)
            updateRecyclerList(currencyList)

        })
        mAmountViewModel.amount.observe(viewLifecycleOwner, Observer<Amount>{ newAmount ->
            onAmountChanged(newAmount)
        })
    }

    /**
     * refreshing RecyclerList with new data.
     */
    private fun updateRecyclerList(list:List<Currency>){
        var updatedList = ArrayList<Currency>()
        for(item in list){
            updatedList.add(getConvertedCurrency(item))
        }
        //mAdapter = ListRecyclerViewAdapter(activity!!,updatedList)
        mAdapter.currencyList = updatedList
        mAdapter.notifyDataSetChanged()
    }

    /**
     * Updating the value available in Currency based on user entered amount.
     */
    private fun getConvertedCurrency(currency:Currency):Currency {
        if(mAmountViewModel !=null) {
            val currentSetAmt: Amount? = mAmountViewModel.amount.value
            currentSetAmt?.let {
                val convertedAmount: Float = CurrencyUtil.convert(it.currency, currency, it.value)
                val newCurrency:Currency = Currency(currency.currency,convertedAmount)
                return  newCurrency
            }
        }
        return currency
    }

    private fun setCurrencyList(list:List<Currency>) {
        mCurrencyList = list;
    }

    /**
     * Function gets updated when user changes the amount
     */
    private fun onAmountChanged(amt: Amount) {
        Logger.dLog(
            CurrencyListFragment::class.java.simpleName,
            "onAmountChanged :" + amt.currency.currency
        )
        updateRecyclerList(mCurrencyList)

    }
}