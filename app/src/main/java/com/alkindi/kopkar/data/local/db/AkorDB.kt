package com.alkindi.kopkar.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alkindi.kopkar.data.local.db.room.UserPersonalDAO
import com.alkindi.kopkar.data.local.db.room.entity.PersonalDataEntity

@Database(entities = [PersonalDataEntity::class], version = 2, exportSchema = false)
abstract class AkorDB : RoomDatabase() {
    abstract fun userPersonalDAO(): UserPersonalDAO

    companion object {
        @Volatile
        var INSTANCE: AkorDB? = null

        @JvmStatic
        fun getDB(context: Context): AkorDB {
            if (INSTANCE == null) {
                synchronized(AkorDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AkorDB::class.java, "akor_db"
                    )
                        .build()
                }
            }
            return INSTANCE as AkorDB
        }
    }
}