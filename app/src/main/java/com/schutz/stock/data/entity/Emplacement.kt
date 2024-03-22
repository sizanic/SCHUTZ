package com.schutz.stock.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Allee::class,
            parentColumns = ["id"],
            childColumns = ["alleeId"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["id", "alleeId"], unique = true)]
)
data class Emplacement(
    @PrimaryKey val id: Int,
    val alleeId: String
)
