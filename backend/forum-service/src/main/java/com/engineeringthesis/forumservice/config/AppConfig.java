package com.engineeringthesis.forumservice.config;

import com.engineeringthesis.commons.config.WebConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(WebConfig.class)
public class AppConfig {
}

