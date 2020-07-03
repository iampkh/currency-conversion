package com.app.currencyconversion.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.app.currencyconversion.CurrencyUtil
import com.app.currencyconversion.R
import com.app.currencyconversion.model.data.Amount
import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.viewmodel.AmountViewModel


class ListRecyclerViewAdapter (val context:Context, var currencyList:List<Currency>)
    : RecyclerView.Adapter<ListRecyclerViewAdapter.CurrencyViewHolder>() {

    private lateinit  var mAmountViewModel : AmountViewModel

    init {
       // mAmountViewModel = ViewModelProvider(context.get).get(AmountViewModel::class.java)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(LayoutInflater.from(context).inflate(R.layout.currency_item,parent,false))
    }

    override fun getItemCount(): Int {
        return currencyList!!.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencyList.get(position))
    }


    class CurrencyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        private var mCurrencyText: TextView? =null
        private var mConvertedValueText: TextView? =null

        init {
            mCurrencyText = itemView.findViewById(R.id.currency_name)
            mConvertedValueText = itemView.findViewById(R.id.currency_value)
        }

        fun bind(amount:Amount) {
            mCurrencyText?.setText(amount.currency.currency)
            mConvertedValueText?.setText(amount.value.toString())
        }
        fun bind(currency:Currency) {
            mCurrencyText?.setText(CurrencyUtil.getDisplayName(currency))
            mConvertedValueText?.setText(CurrencyUtil.convertFloatToString(currency.rate))
        }

    }
}