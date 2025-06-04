package com.cesar.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "table_order")
data class OrderEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:String,
    @ColumnInfo("client")
    val client:String,
    @ColumnInfo("product")
    val product:String,
    @ColumnInfo("amount")
    val amount:Int,
    @ColumnInfo("date")
    val date: String,
    @ColumnInfo("pendingSave")
    val pendingSave:Boolean,
)
