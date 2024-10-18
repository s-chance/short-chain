package org.entropy.shortchain.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyInetTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        PGobject inetObject = new PGobject();
        inetObject.setType("inet");
        inetObject.setValue(parameter);
        ps.setObject(i, inetObject);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        PGobject inetObject = (PGobject) rs.getObject(columnName);
        return inetObject != null ? inetObject.getValue() : null;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        PGobject inetObject = (PGobject) rs.getObject(columnIndex);
        return inetObject != null ? inetObject.getValue() : null;
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        PGobject inetObject = (PGobject) cs.getObject(columnIndex);
        return inetObject != null ? inetObject.getValue() : null;
    }
}
