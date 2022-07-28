package com.example.productapi.configuration

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfiguration(private val databaseProperties: R2dbcProperties){

    @Bean(initMethod = "migrate")
    fun flyway() : Flyway {
        return Flyway(with(databaseProperties) {
            Flyway.configure()
                    .baselineOnMigrate(true)
                    .outOfOrder(true)
                    .dataSource(
                            url.replace("r2dbc","jdbc"),
                            username,
                            password
                    )
        })
    }

}