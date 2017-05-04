package com.beerjournal.infrastructure.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ApplicationProperties {

    @Value("#{'${bj.app.images.extensions}'.split(',')}")
    @Getter
    private Set<String> acceptedImageExtensions;
}
