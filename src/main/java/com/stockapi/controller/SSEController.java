package com.stockapi.controller;


import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@CrossOrigin
public class SSEController {
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> sse() {
        AtomicInteger i = new AtomicInteger();
        return Flux.interval(Duration.ofSeconds(2))
                .map(seq -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(seq))
                        .event("sse-event")
                        .data("Con meo " + i.getAndIncrement())
                        .build());
    }

}
