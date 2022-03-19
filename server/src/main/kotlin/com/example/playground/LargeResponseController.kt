package com.example.playground

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LargeResponseController {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/large")
    fun slow(): String {
        log.info("request is arrived!")
        return "done!".repeat(1_000_000)
    }
}