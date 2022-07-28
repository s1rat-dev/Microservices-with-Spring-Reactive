package com.example.commentapi.configruation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import reactor.core.publisher.Mono

@EnableR2dbcAuditing
@Configuration
class AuditorConfiguration {


    @Bean
    fun auditorAware(): ReactiveAuditorAware<String>? {
        return ReactiveAuditorAware { Mono.empty() }
    }

}