package com.schutz.stock.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schutz.stock.data.entity.Allee
import com.schutz.stock.data.entity.Emplacement

@Dao
interface EmplacementDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEmplacement(emplacement: Emplacement)

    @Query("SELECT * FROM emplacement where id = :id and alleeId = :alleeId limit 1")
    fun getEmplacement(alleeId: String, id: Int): Emplacement

    @Query("SELECT * FROM emplacement where alleeId = :alleeId")
    fun getEmplacementsFromAllee(alleeId: String): List<Emplacement>

    @Query("SELECT e.* FROM emplacement e inner join reference r on r.emplacementId=e.id and e.alleeId=r.alleeId where e.alleeId = :alleeId")
    fun getEmplacementsOccupes(alleeId: String): List<Emplacement>


}