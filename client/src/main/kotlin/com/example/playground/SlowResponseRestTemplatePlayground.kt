package com.example.playground

import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.time.Duration
import java.time.LocalDateTime

fun main() {
    val readTimeout = Duration.ofSeconds(3L)
    val restTemplate = RestTemplate(
        HttpComponentsClientHttpRequestFactory(
            HttpClientBuilder
                .create()
                .setMaxConnPerRoute(100)
                .setMaxConnTotal(100)
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(
                    RequestConfig.custom().setSocketTimeout(readTimeout.toMillis().toInt()).build()
                )
                .build()
        )
    )

    try {
        restTemplate.getForObject("http://localhost:8080/slow", String::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        println("[${LocalDateTime.now()}] request is done!")
    }

    // 커넥션 풀에 있는 커넥션을 바로 종료하지 않기 위해 10초간 슬립
    Thread.sleep(10_000L)
    println("[${LocalDateTime.now()}] main function is done!")
}