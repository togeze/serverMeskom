package com.mestKom.sources

import com.mestKom.data.tables.Users
import com.mestKom.data.user.User
import com.mestKom.database.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DatabaseDataSource : UserDataSource {

    private fun result(row: ResultRow) = User(
        username = row[Users.username],
        password = row[Users.password],
        email    = row[Users.email],
        salt     = row[Users.salt],
        id       = row[Users.id]
    )


    override suspend fun allUsers(): List<User> = dbQuery{
        Users.selectAll().map(::result)
    }

    override suspend fun getUserByUsername(username: String): User? = dbQuery{
        Users
            .select{Users.username eq username}
            .map(::result)
            .singleOrNull()
    }

    override suspend fun insertUser(user: User): User? = dbQuery{
        val insertStatement = Users.insert{
            it[Users.username] = user.username
            it[Users.password] = user.password
            it[Users.email]    = user.email
            it[Users.salt]     = user.salt
            it[Users.id]       = user.id
        }
        insertStatement.resultedValues?.singleOrNull()?.let (::result)
    }

    override suspend fun editUser(email: String, password: String, username: String): Boolean = dbQuery {
        Users.update({Users.username eq username}) {
            it[Users.email]    = email
            it[Users.password] = password
            it[Users.username] = username
        } > 0
    }

    override suspend fun deleteUser(username: String): Boolean = dbQuery {
        Users.deleteWhere { Users.username eq username } > 0
    }

    override suspend fun getUserByEmail(email: String): User? = dbQuery{
        Users
            .select{Users.email eq email}
            .map(::result)
            .singleOrNull()
    }

}