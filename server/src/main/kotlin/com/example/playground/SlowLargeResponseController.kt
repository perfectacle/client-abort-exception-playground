package com.example.playground

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SlowLargeResponseController {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/slow-large")
    fun slow(): String {
        log.info("request is arrived!")

        Thread.sleep(10_000L)

        log.info("slow large process is done!")
        return "done!".repeat(1_000_000)
    }
}