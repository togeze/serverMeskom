package com.mestKom.data.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object Videos : IntIdTable() {
    val descriptor = varchar("descriptor", 120)
    val latitude = varchar("latitude", 50)
    val longitude = varchar("longitude", 50)
    val path = varchar("path", 120)
    val sequelId = varchar("sequelId", 100)
}