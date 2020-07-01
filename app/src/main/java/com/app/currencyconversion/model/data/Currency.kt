package com.app.currencyconversion.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.currencyconversion.CurrencyUtil

@Entity(tableName = CurrencyUtil.RATE_TABLE_NAME)
data class Currency(@PrimaryKey @ColumnInfo(name =CurrencyUtil.COLUMN_CURRENCY) val currency: String,
                    @ColumnInfo(name =CurrencyUtil.COLUMN_RATE)val rate: Float)