package net.perfect.tea.luaimageservice.backend.routes

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.serialization.json.jsonObject
import mu.KotlinLogging

class PostBackendRoutes {

    private val logger = KotlinLogging.logger {}

     suspend fun onRequest(call: ApplicationCall) {
         val authorization = call.request.header("Authorization")
         if (authorization == null) {
             call.response.status(HttpStatusCode.Unauthorized)
             logger.info("The Authorization is null.")
             return
         }

         val request = Json.parseToJsonElement(call.receiveText()).jsonObject
    }

    val imagesFolderToPost = "assets/images"
}
