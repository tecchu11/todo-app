package com.example.todo.mysql.configuration

import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

@Configuration
class MybatisConfig {
    @Bean
    fun configurationCustomizer(): ConfigurationCustomizer = ConfigurationCustomizer {
        it.typeHandlerRegistry.register(
            GenericEnum::class.java,
            GenericTypeHandler::class.java
        )
    }
}

class GenericTypeHandler<E>(private val clazz: Class<E>) : BaseTypeHandler<E>()
        where E : Enum<*>,
              E : GenericEnum {

    private fun enumOf(code: String): E = clazz.enumConstants?.firstOrNull { it.code() == code }
        ?: throw IllegalArgumentException("enum constant isn't defined in $clazz by code = $code")

    override fun setNonNullParameter(ps: PreparedStatement, i: Int, parameter: E, jdbcType: JdbcType?) =
        ps.setString(i, parameter.code())

    override fun getNullableResult(rs: ResultSet, columnName: String): E =
        enumOf(rs.getString(columnName))

    override fun getNullableResult(rs: ResultSet, columnIndex: Int): E =
        enumOf(rs.getString(columnIndex))

    override fun getNullableResult(cs: CallableStatement, columnIndex: Int): E =
        enumOf(cs.getString(columnIndex))
}
