package com.app.currencyconversion.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
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

class UserInputFragment : Fragment(),AdapterView.OnItemSelectedListener {

    //View declaration
    lateinit var mAmountEditText:EditText;
    lateinit var mSpinner:Spinner
    lateinit var mInfoText:TextView

    //Currency specific declaration
    lateinit var mCurrency:Currency
    lateinit var mCurrencyList:List<Currency>

    //Handler to avoid sending multiple change notification to viewmodel
    val mTextChangeHandler : Handler = Handler();

    //Shared Viewmodel for amount change notification
    private val mCurrencyViewModel : CurrencyViewModel by activityViewModels()
    private val mAmountViewModel : AmountViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    private fun initView(view:View) {
        mAmountEditText = view.findViewById<EditText>(R.id.currency_value)
        mInfoText = view.findViewById<TextView>(R.id.info_text)
        mSpinner = view.findViewById<Spinner>(R.id.currency_spinner)
        mSpinner!!.setOnItemSelectedListener(this)

        setInfoText("Your Amount")
    }


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.currency_input_fragment,container,false);

        initView(view)

        return view;
    }

    override fun onStart() {
        super.onStart()
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

            // android:drawableRight="@android:drawable/arrow_down_float"
            mSpinner!!.setAdapter(listAdapter)
        })

    }

    override fun onResume() {
        super.onResume()
        mAmountEditText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}


            override fun afterTextChanged(p0: Editable?) { }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                val r = Runnable {
                    notifyAmountChange(text.toString())
                }
                mTextChangeHandler.removeCallbacks(r)

               mTextChangeHandler.postDelayed(r, 3000)

            }

        })
    }

    private fun setInfoText(text:String) {
        mInfoText.setText(text)
    }

    private fun setCurrencyList(list:List<Currency>) {
        mCurrencyList = list;
    }



    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(arg0: AdapterView<*>?, view: View?, position: Int, id: Long) {
       mCurrency = mCurrencyList.get(position)
       notifyAmountChange(mAmountEditText.getText().toString())
    }
    private fun notifyAmountChange(amountEntered:String){
        try {
            if (amountEntered != null && amountEntered.isNotEmpty()) {
                val changedAmt = Amount(amountEntered.toFloat(), mCurrency)
                mAmountViewModel.setRate(changedAmt)
            }
        }catch (e:NumberFormatException){
            Logger.eLog(UserInputFragment::class.java.simpleName,"Exception :"+e.toString())
        }
    }

}