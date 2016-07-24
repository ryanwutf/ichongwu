package co.ichongwu.vidser.common.dao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class SqlParams {

    public static MapSqlParameterSource map(String paramName, Object value) {
        return new MapSqlParameterSource(paramName, value);
    }

    public static MapSqlParameterSource bean(Object object) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        BeanPropertySqlParameterSource bean = new BeanPropertySqlParameterSource(object);
        for (String paramName : bean.getReadablePropertyNames()) {
            Object value = bean.getValue(paramName);
            if (value != null) {
                map.addValue(paramName, value);
            }
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static SqlParameterSource sqlParams(Object... params) {
        if (params == null || params.length == 0) {
            return new EmptySqlParameterSource();
        }
        if (params.length == 1) {
            Object single = params[0];
            if (single == null) {
                return new EmptySqlParameterSource();
            }
            if (single instanceof SqlParameterSource) {
                return (SqlParameterSource) single;
            }
            if (single instanceof Map) {
                return new MapSqlParameterSource((Map<String, Object>) single);
            }
            else {
                return new BeanPropertySqlParameterSource(single);
            }
        }
        else {
            return new SqlParameterSourceWrapper(Arrays.asList(params));
        }
    }

    public static class SqlParameterSourceWrapper implements SqlParameterSource {

        private List<SqlParameterSource> paramsList = new ArrayList<SqlParameterSource>();

        public SqlParameterSourceWrapper(List<Object> params) {
            for (Object param : params) {
                paramsList.add(sqlParams(param));
            }
        }

        @Override
        public boolean hasValue(String paramName) {
            for (SqlParameterSource params : paramsList) {
                if (params.hasValue(paramName)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Object getValue(String paramName) throws IllegalArgumentException {
            for (SqlParameterSource params : paramsList) {
                if (params.hasValue(paramName)) {
                    return params.getValue(paramName);
                }
            }
            return null;
        }

        @Override
        public int getSqlType(String paramName) {
            for (SqlParameterSource params : paramsList) {
                if (params.hasValue(paramName)) {
                    return params.getSqlType(paramName);
                }
            }
            return TYPE_UNKNOWN;
        }

        @Override
        public String getTypeName(String paramName) {
            for (SqlParameterSource params : paramsList) {
                if (params.hasValue(paramName)) {
                    return params.getTypeName(paramName);
                }
            }
            return null;
        }
    }

}