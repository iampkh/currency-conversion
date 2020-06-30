package com.app.currencyconversion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class UserInputFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.currency_input_fragment,container,false);


        return view;
    }



}