package com.cesar.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cesar.data.database.model.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(order : OrderEntity)

    @Query("SELECT * FROM table_order")
    fun getListOrder() :List<OrderEntity>

    @Delete
    fun deleteOrder(order : OrderEntity)

    @Update
    fun editOrder(order : OrderEntity)
}