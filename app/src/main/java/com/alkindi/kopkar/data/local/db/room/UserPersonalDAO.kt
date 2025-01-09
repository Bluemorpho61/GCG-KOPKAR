package com.alkindi.kopkar.data.local.db.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alkindi.kopkar.data.local.db.room.entity.PersonalDataEntity

@Dao
interface UserPersonalDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(user: List<PersonalDataEntity>)

    @Update
    fun updateData(user: PersonalDataEntity)

    @Query("DELETE FROM personaldataentity WHERE mbrid =:mbrid")
    fun deleteData(mbrid: String)

    @Query("SELECT * FROM personaldataentity")
   fun getUserPersonalData(): LiveData<List<PersonalDataEntity>>


    @Query("SELECT * FROM personaldataentity WHERE mbrid = :mbrid")
    fun getDataByMBRID(mbrid: String): PersonalDataEntity?
}