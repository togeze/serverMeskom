package com.mestKom.sources

import com.mestKom.data.tables.Users
import com.mestKom.data.tables.Videos
import com.mestKom.data.user.User
import com.mestKom.data.video.Video
import com.mestKom.database.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DatabaseDataSource : UserDataSource, VideoDataSource {

    private fun result(row: ResultRow) = User(
        username       = row[Users.username],
        password       = row[Users.password],
        email          = row[Users.email],
        salt           = row[Users.salt],
        sequelId       = row[Users.sequelId]
    )

    private fun resultVideo(row: ResultRow) = Video(
        descriptor  = row[Videos.descriptor],
        latitude    = row[Videos.latitude],
        longitude   = row[Videos.longitude],
        path        = row[Videos.path],
        sequelId    = row[Videos.sequelId]
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

    override suspend fun insertUser(user: User): Unit = dbQuery{
        //val insertStatement =
            Users.insertAndGetId{
            it[Users.username] = user.username
            it[Users.password] = user.password
            it[Users.email]    = user.email
            it[Users.salt]     = user.salt
            it[Users.sequelId]       = user.sequelId
        }
        //insertStatement.resultedValues?.singleOrNull()?.let (::result)
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

    override suspend fun allVideo(): List<Video> = dbQuery{
        Videos.selectAll().map(::resultVideo)
    }

    override suspend fun getVideoById(idVideo: String): Video? = dbQuery {
        Videos
            .select{Videos.sequelId eq idVideo}
            .map(::resultVideo)
            .singleOrNull()
    }

    override suspend fun insertVideo(video: Video) : Unit = dbQuery{
        Videos.insertAndGetId{
            it[Videos.descriptor] = video.descriptor
            it[Videos.latitude]   = video.latitude
            it[Videos.longitude]  = video.longitude
            it[Videos.path]       = video.path
            it[Videos.sequelId]   = video.sequelId
        }
    }

    override suspend fun editVideo(nameFile: String, descriptor: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteVideo(idVideo: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAllVideoOfUser(idUser: String): List<Video> {
        TODO("Not yet implemented")
    }

}