package com.schutz.stock.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.schutz.stock.data.dao.AlleeDao
import com.schutz.stock.data.dao.EmplacementDao
import com.schutz.stock.data.dao.ReferenceDao
import com.schutz.stock.data.entity.Allee
import com.schutz.stock.data.entity.Emplacement
import com.schutz.stock.data.entity.Reference

@Database(entities = [Allee::class, Emplacement::class, Reference::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alleeDao(): AlleeDao
    abstract fun emplacementDao(): EmplacementDao
    abstract fun referenceDao(): ReferenceDao
}