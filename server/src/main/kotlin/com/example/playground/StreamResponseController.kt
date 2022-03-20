package com.example.playground

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter
import java.util.concurrent.Executors

@RestController
class StreamResponseController {
    private val executor = Executors.newCachedThreadPool()
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/stream")
    fun stream(): ResponseEntity<ResponseBodyEmitter> {
        val emitter = ResponseBodyEmitter()
        executor.execute {
            try {
                Thread.sleep(100L)
                emitter.send("hello, once", MediaType.TEXT_PLAIN)
                log.info("hello, once")

                Thread.sleep(100L)
                emitter.send("hello, twice", MediaType.TEXT_PLAIN)
                log.info("hello, twice")

                Thread.sleep(500L)
                emitter.send("hello, thrice", MediaType.TEXT_PLAIN)
                log.info("hello, thrice")

                Thread.sleep(100L)
                emitter.send("bye", MediaType.TEXT_PLAIN)
                log.info("bye")

                emitter.complete()
            } catch (e: Exception) {
                emitter.completeWithError(e)
                log.error(e.message, e)
            }
        }
        return ResponseEntity(emitter, HttpStatus.OK)
    }
}
