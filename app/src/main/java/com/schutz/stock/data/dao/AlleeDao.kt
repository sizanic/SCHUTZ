package com.schutz.stock.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schutz.stock.data.entity.Allee

@Dao
interface AlleeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllee(allee: Allee)

    @Query("SELECT * FROM allee where id = :id limit 1")
    fun getAllee(id: String): List<Allee>

    @Query("SELECT * FROM allee")
    fun getAllAllees(): List<Allee>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAllees(allees: List<Allee>) {

    }
}