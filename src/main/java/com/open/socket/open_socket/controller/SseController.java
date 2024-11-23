package com.open.socket.open_socket.controller;

import java.io.IOException;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SseController {

    // @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    // public SseEmitter subscribe() {
    // SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
    // // Add the emitter to a list of subscribers or handle it in another way
    // return emitter;
    // }
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<SseEmitter> subscribeSseEmitter() {
        return ResponseEntity
                .ok()
                .header("Connection", "keep-alive")
                .cacheControl(CacheControl.noCache())
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(new SseEmitter(Long.MAX_VALUE));
    }

    @PostMapping(path = "/sse")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> postMethodName(@RequestBody String entity) {
        executeSseLogic(new SseEmitter(Long.MAX_VALUE));

        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(entity);
    }

    private void executeSseLogic(SseEmitter emitter) {
        try {
            for (int counter = 0; counter < 10; counter++) {
                // Create an event with a custom event ID and data
                SseEmitter.SseEventBuilder event = SseEmitter.event().id(String.valueOf(counter))
                        .data("Event data at ");
                // Send the event to the client
                emitter.send(event);
                // Wait for one second before sending the next event
            }
            // Mark the end of the event stream
            emitter.complete();
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
