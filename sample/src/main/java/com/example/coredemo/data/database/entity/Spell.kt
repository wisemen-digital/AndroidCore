package com.example.coredemo.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.appwise.room.BaseEntity
import com.example.coredemo.data.database.utils.DBConstants

@Entity(tableName = DBConstants.TABLE_SPELLS)
data class Spell(
    @PrimaryKey
    override val id: String = "",
    val slug: String,
    val name: String,
    val desc: String,
    val level: String,
    val level_int: Int
): BaseEntity