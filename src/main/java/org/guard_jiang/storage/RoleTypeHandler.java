package org.guard_jiang.storage;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.guard_jiang.Role;
import org.guard_jiang.chat.ChatStatus;

import javax.annotation.Nonnull;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by someone on 4/3/2017.
 */
public class RoleTypeHandler extends BaseTypeHandler<Role> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int paramIdx, Role role, JdbcType jdbcType) throws SQLException {
        ps.setInt(paramIdx, role.getCode());
    }

    @Override
    public Role getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        }
        return Role.fromCode(code);
    }

    @Override
    public Role getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        }
        return Role.fromCode(code);
    }

    @Override
    public Role getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        }
        return Role.fromCode(code);
    }
}