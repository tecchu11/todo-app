package com.example.todo.infrastructure.configuration

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName
import java.time.ZoneId

class MysqlContainerExtensions : BeforeAllCallback, AfterAllCallback {

    private lateinit var mysql: MySQLContainer<Nothing>

    override fun beforeAll(context: ExtensionContext) {
        mysql = MySQLContainer<Nothing>(DockerImageName.parse("mysql:5.7"))
            .apply {
                withDatabaseName("testdb")
                withUsername("test")
                withPassword("test")
                withEnv("TZ", ZoneId.systemDefault().id)
                withClasspathResourceMapping(
                    "docker-entrypoint-initdb.d",
                    "/docker-entrypoint-initdb.d",
                    BindMode.READ_WRITE
                )
                start()
            }

        System.setProperty("spring.datasource.url", mysql.jdbcUrl)
        System.setProperty("spring.datasource.username", mysql.username)
        System.setProperty("spring.datasource.password", mysql.password)
    }

    override fun afterAll(context: ExtensionContext) {
        mysql.stop()
    }
}
