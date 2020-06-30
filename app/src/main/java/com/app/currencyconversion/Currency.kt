package com.app.currencyconversion

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Utility.RATE_TABLE_NAME)
data class Currency(@PrimaryKey @ColumnInfo(name =Utility.COLUMN_CURRENCY) val currency: String,
                    @ColumnInfo(name =Utility.COLUMN_RATE)val rate: String)