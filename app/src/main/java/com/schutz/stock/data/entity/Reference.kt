package com.schutz.stock.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["alleeId", "emplacementId","id"],
    foreignKeys = [
        ForeignKey(
            entity = Allee::class,
            parentColumns = ["id"],
            childColumns = ["alleeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Emplacement::class,
            parentColumns = ["alleeId", "id"],
            childColumns = ["alleeId", "emplacementId"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["id", "alleeId", "emplacementId"], unique = true)]
)
data class Reference(
    val alleeId: String,
    val emplacementId: Int,
    val id: String
)
