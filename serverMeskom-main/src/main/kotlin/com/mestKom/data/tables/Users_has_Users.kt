package com.mestKom.data.tables

import org.jetbrains.exposed.sql.Table


object Users_has_Users: Table() {
    val username_id = reference("username_id", Users)
    val friend_id = reference("friend_id", Users)

    override val primaryKey = PrimaryKey(username_id, friend_id)
}