package com.schutz.stock.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["id"], unique = true)])
data class Allee(
    @PrimaryKey val id: String
)
