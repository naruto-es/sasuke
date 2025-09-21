package org.cherhy.sasuke.config

import org.cherhy.sasuke.config.property.DatabaseProperty
import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfig(
    private val databaseProperty: DatabaseProperty,
) {
    @Bean(initMethod = "migrate")
    fun flyway() =
        Flyway(
            Flyway.configure()
                .dataSource(
                    databaseProperty.url,
                    databaseProperty.username,
                    databaseProperty.password,
                )
                .baselineOnMigrate(true)
                .locations("classpath:db/migration")
        )
}