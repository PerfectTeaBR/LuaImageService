package net.perfect.tea.luaimageservice

import io.ktor.http.content.*
import io.ktor.server.application.call
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondText

import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import mu.KotlinLogging
import java.io.File
import java.util.*

import net.perfect.tea.luaimageservice.padronization.Padronization
import net.perfect.tea.luaimageservice.backend.routes.PostBackendRoutes


class LuaImageService {
    private val logger = KotlinLogging.logger {}
    // O arquivo que você pediu para adicionar
    private val imageInfosFile = File("./image-infos.json")
    private val storageDir = File("uploads")

    fun start() {
        // Inicialização do arquivo de infos
        if (!imageInfosFile.exists()) {
            imageInfosFile.createNewFile()
            logger.info("Created ./image-infos.json :3")
        }

        embeddedServer(Netty, port = 8080) {
            routing {

                // Rota GET: Retorna o objeto de rotas
                get("/api/file") {
                    val padronization = Padronization()
                    val postRoute = PostBackendRoutes()
                    call.respond(postRoute)
                }

                // Rota POST: Guarda o arquivo físico no disco
                post("/api/upload") {
                    val multipart = call.receiveMultipart()

                    multipart.forEachPart { part ->
                        if (part is PartData.FileItem) {
                            val fileName = part.originalFileName ?: "img_${UUID.randomUUID()}"
                            val file = File(storageDir, fileName)

                            // Cria a pasta de uploads se não existir
                            storageDir.mkdirs()

                            // Escreve os bytes no arquivo
                            part.streamProvider().use { input ->
                                file.outputStream().buffered().use { output ->
                                    input.copyTo(output)
                                }
                            }

                            logger.info("Arquivo guardado em: ${file.absolutePath}")

                        }
                        part.dispose()
                    }
                    call.respondText("Arquivo guardado com sucesso!")
                }
            }
        }.start(wait = true) // Inicia a aplicação
    }
}
