package com.example.todo.infrastructure.mapper

import org.junit.ClassRule
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

class MysqlContainerContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    companion object {
        @ClassRule
        private val MYSQL = MySQLContainer<Nothing>(DockerImageName.parse("mysql:5.7"))
            .apply {
                withDatabaseName("testdb")
                withUsername("test")
                withPassword("test")
                withEnv("TZ", "Asia/Tokyo")
                withClasspathResourceMapping(
                    "docker-entrypoint-initdb.d",
                    "/docker-entrypoint-initdb.d",
                    BindMode.READ_WRITE
                )
            }

        init {
            MYSQL.start()
        }
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        TestPropertyValues
            .of(
                "spring.datasource.url=${MYSQL.jdbcUrl}",
                "spring.datasource.username=${MYSQL.username}",
                "spring.datasource.password=${MYSQL.password}"
            )
            .applyTo(applicationContext.environment)
    }
}
