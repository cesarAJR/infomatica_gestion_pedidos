package com.cesar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cesar.data.database.dao.OrderDao
import com.cesar.data.database.model.OrderEntity

@Database(entities = [OrderEntity::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun OrderDao():OrderDao
}