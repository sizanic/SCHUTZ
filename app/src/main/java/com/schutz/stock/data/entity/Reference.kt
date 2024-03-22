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
        ),
        ForeignKey(
            entity = Emplacement::class,
            parentColumns = ["id"],
            childColumns = ["emplacementId"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["id", "alleeId", "emplacementId"], unique = true)]
)
data class Reference(
    @PrimaryKey val id: String,
    val alleeId: String,
    val emplacementId: Int
)
