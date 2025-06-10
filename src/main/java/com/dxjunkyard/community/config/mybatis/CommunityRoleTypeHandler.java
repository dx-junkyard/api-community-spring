package com.dxjunkyard.community.config.mybatis;

import com.dxjunkyard.community.domain.CommunityRole;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommunityRoleTypeHandler extends BaseTypeHandler<CommunityRole> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CommunityRole parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    @Override
    public CommunityRole getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return CommunityRole.fromId(rs.wasNull() ? null : value);
    }

    @Override
    public CommunityRole getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return CommunityRole.fromId(rs.wasNull() ? null : value);
    }

    @Override
    public CommunityRole getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return CommunityRole.fromId(cs.wasNull() ? null : value);
    }
}
