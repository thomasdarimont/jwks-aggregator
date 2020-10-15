package com.github.thomasdarimont.jwt.jwksaggrator.web;

import com.github.thomasdarimont.jwt.jwksaggrator.JwksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwks")
@RequiredArgsConstructor
class JwksController {

    private final JwksService jwksService;

    @GetMapping
    Object getJwks() {
        return jwksService.getJwks().toJSONObject();
    }
}
