package com.mestKom

import com.google.gson.Gson
import com.mestKom.data.video.Video
import com.mestKom.data.video.VideoJSON
import com.mestKom.sources.UserDataSource
import com.mestKom.sources.VideoDataSource
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.h2.util.json.JSONObject
import java.io.File
import java.util.*

fun Route.uploadVideo(videoDataSource: VideoDataSource){
    var fileName = ""
    var fileDescriptor = ""
    var fileBytes : ByteArray = ByteArray(0)

    post("/uploadVideo") {
        val multipart = call.receiveMultipart()

        multipart.forEachPart {part ->
            when(part){
                is PartData.FormItem -> {
                    fileDescriptor = """${part.value.trimIndent()}"""
                }
                is PartData.FileItem -> {
                    fileName = part.originalFileName as String
                    fileBytes = part.streamProvider().readBytes()
                }
                else -> {}
            }
            part.dispose()
        }
        println(fileDescriptor)

        var videoJson = Gson().fromJson(/* json = */ fileDescriptor, /* classOfT = */ VideoJSON::class.java)
        println("${videoJson.description}, ${videoJson.latitude}, ${videoJson.longitude}, ${"D://video/${videoJson.id}/${fileName}"}, ${videoJson.id}")

        var video = Video(
            descriptor = videoJson.description,
            latitude = videoJson.latitude,
            longitude = videoJson.longitude,
            path = "D://video/${videoJson.id}/${fileName}",
            sequelId = videoJson.id
        )

        videoDataSource.insertVideo(video)

        File("D://video/${videoJson.id}/${fileName}").writeBytes(fileBytes)
        call.respond(HttpStatusCode.OK)
    }

}