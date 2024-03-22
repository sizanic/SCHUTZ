package com.schutz.stock.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schutz.stock.data.entity.Allee
import com.schutz.stock.data.entity.Emplacement
import com.schutz.stock.data.entity.Reference

@Dao
interface ReferenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReference(reference: Reference)

    @Query("SELECT * FROM reference where id = :id and emplacementId = :emplacementId and alleeId = :alleeId limit 1")
    fun getReference(alleeId: String, emplacementId: Int, id: String): Reference

    @Query("SELECT * FROM reference")
    fun getAllReferences(): List<Reference>
}