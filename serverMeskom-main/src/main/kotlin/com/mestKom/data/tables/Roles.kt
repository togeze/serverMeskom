package com.mestKom.data.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Roles : IntIdTable() {
    val name = varchar("Name", 45)
}