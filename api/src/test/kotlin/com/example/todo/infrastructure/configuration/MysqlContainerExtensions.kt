package com.example.todo.infrastructure.configuration

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName
import java.time.ZoneId

class MysqlContainerExtensions : BeforeAllCallback, AfterAllCallback {

    companion object {
        private val MYSQL = MySQLContainer(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("todo")
            .withUsername("todo")
            .withPassword("test")
            .withEnv("TZ", ZoneId.systemDefault().id)
            .withClasspathResourceMapping(
                "db/mysql/conf",
                "/etc/mysql/conf.d",
                BindMode.READ_ONLY
            )
            .withClasspathResourceMapping(
                "db/mysql/data",
                "/docker-entrypoint-initdb.d",
                BindMode.READ_ONLY
            )
    }

    override fun beforeAll(context: ExtensionContext) {
        MYSQL.start()
        System.setProperty("spring.datasource.url", MYSQL.jdbcUrl)
        System.setProperty("spring.datasource.username", MYSQL.username)
        System.setProperty("spring.datasource.password", MYSQL.password)
    }

    override fun afterAll(context: ExtensionContext) {
        MYSQL.stop()
    }
}
