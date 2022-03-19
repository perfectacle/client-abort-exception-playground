package com.example.playground

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VerySlowResponseController {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/very-slow")
    fun verySlow(): String {
        log.info("request is arrived!")

        Thread.sleep(70_000L)

        log.info("very slow process is done!")
        return "done!"
    }
}