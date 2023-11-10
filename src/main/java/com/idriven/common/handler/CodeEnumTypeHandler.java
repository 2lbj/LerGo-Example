package com.idriven.common.handler;

import com.idriven.entity.enums.TgDemoSexEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CodeEnumTypeHandler extends BaseTypeHandler<TgDemoSexEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TgDemoSexEnum parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public TgDemoSexEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public TgDemoSexEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public TgDemoSexEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
