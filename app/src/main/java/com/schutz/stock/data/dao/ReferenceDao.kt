package com.schutz.stock.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schutz.stock.data.entity.Reference

@Dao
interface ReferenceDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertReference(reference: Reference)

    @Delete
    fun removeReference(reference: Reference)

    @Query("SELECT * FROM reference where id = :refId limit 1")
    fun searchReference(refId: String): Reference

    @Query("SELECT * FROM reference where alleeId = :alleeId  and emplacementId = :emplacementId limit 1")
    fun getReference(alleeId: String, emplacementId: Int): Reference

    @Query("SELECT * FROM reference")
    fun getAllReferences(): List<Reference>

    @Query("SELECT count(*) FROM emplacement where alleeId = :alleeId")
    fun getNbEmplacement(alleeId: String): Int

    @Query("SELECT count(*) FROM reference where alleeId = :alleeId")
    fun getNbReferenceFromAllee(alleeId: String): Int

    @Query("SELECT * FROM reference where emplacementId = :emplacementId and alleeId = :alleeId")
    fun getNbReference(alleeId: String, emplacementId: Int): Int
}