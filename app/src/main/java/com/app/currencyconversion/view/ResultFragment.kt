package com.app.currencyconversion.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.app.currencyconversion.CurrencyUtil
import com.app.currencyconversion.R
import com.app.currencyconversion.model.data.Amount
import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.util.helper.Logger
import com.app.currencyconversion.viewmodel.AmountViewModel
import com.app.currencyconversion.viewmodel.CurrencyViewModel

class ResultFragment : Fragment(),AdapterView.OnItemSelectedListener {

    //View declaration
    lateinit var mAmountEditText:EditText;
    lateinit var mSpinner:Spinner
    lateinit var mInfoText:TextView

    //Currency specific declaration
    lateinit var mResultCurrency:Currency
    lateinit var mCurrencyList:List<Currency>
    var mInputAmount: Amount? = null


    //Handler to avoid sending multiple change notification to viewmodel
    val mTextChangeHandler : Handler = Handler();

    //Shared Viewmodel for amount change notification
    private val mAmountViewModel : AmountViewModel by activityViewModels()
    private val mCurrencyViewModel : CurrencyViewModel by activityViewModels()

    private fun initView(view:View) {
        mAmountEditText = view.findViewById<EditText>(R.id.currency_value)
        mInfoText = view.findViewById<TextView>(R.id.info_text)
        mSpinner = view.findViewById(R.id.currency_spinner)
        mSpinner!!.setOnItemSelectedListener(this)

        setInfoText("Converted Amount")
        setResultEditable(false)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.currency_input_fragment,container,false);

        initView(view)

        return view;
    }

    override fun onStart() {
        super.onStart()
        mAmountViewModel.amount.observe(viewLifecycleOwner, Observer<Amount>{ newAmount ->
            onAmountChanged(newAmount)
        })

        mCurrencyViewModel.currenciesAndRatesList.observe(this, Observer { currencyList ->

            if(currencyList == null) return@Observer;

            setCurrencyList(currencyList)

            val countryList = ArrayList<String>()
            for(currency in currencyList) {
                countryList.add(CurrencyUtil.getDisplayName(currency))
            }
            val listAdapter = ArrayAdapter<String>(activity!!
                ,android.R.layout.simple_spinner_item
                ,countryList)
            listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            mSpinner!!.setAdapter(listAdapter)
        })
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setInfoText(text:String) {
        mInfoText.setText(text)
    }
    private fun setResultEditable(editable:Boolean){
        mAmountEditText.setEnabled(editable)
    }

    private fun setCurrencyList(list:List<Currency>) {
        mCurrencyList = list;
    }

    private fun setConvertedAmount(inputAmt:Amount){
        if(inputAmt != null) {
            val convertedAmount: Float =
                CurrencyUtil.convert(inputAmt.currency, mResultCurrency, inputAmt.value)
            Logger.dLog(
                ResultFragment::class.java.simpleName,
                "setConvertedAmount :From" + inputAmt.currency.currency +" To currency="+mResultCurrency.currency)
            Logger.dLog(
                ResultFragment::class.java.simpleName,
                "setConvertedAmount :inputAmt.value" + inputAmt.value +"  Output value="+convertedAmount)
            mAmountEditText.setText(CurrencyUtil.convertFloatToString(convertedAmount));
        }
    }

    private fun onAmountChanged(amt: Amount) {
        Logger.dLog(
            ResultFragment::class.java.simpleName,
            "onAmountChanged :" + amt.currency.currency
        )

        mInputAmount = amt
        setConvertedAmount(mInputAmount!!)

    }

    override fun onItemSelected(arg0: AdapterView<*>?, view: View?, position: Int, id: Long) {
       mResultCurrency = mCurrencyList.get(position)

        mInputAmount?.let {
            Logger.dLog(
                ResultFragment::class.java.simpleName,
                "onItemSelected :From" + it.currency.currency +" To currency="+mResultCurrency.currency
            )
            setConvertedAmount(it)

        }

    }
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}