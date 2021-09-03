package com.example.todo.infrastructure.configuration.typehandler

import com.example.todo.domain.enumration.TaskStatus
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

class TaskStatusTypeHandler : BaseTypeHandler<TaskStatus>() {

    override fun setNonNullParameter(ps: PreparedStatement, i: Int, parameter: TaskStatus, jdbcType: JdbcType?) =
        ps.setString(i, parameter.statusId)

    override fun getNullableResult(rs: ResultSet, columnName: String): TaskStatus =
        TaskStatus.of(rs.getString(columnName))

    override fun getNullableResult(rs: ResultSet, columnIndex: Int): TaskStatus =
        TaskStatus.of(rs.getString(columnIndex))

    override fun getNullableResult(cs: CallableStatement, columnIndex: Int): TaskStatus =
        TaskStatus.of(cs.getString(columnIndex))
}
