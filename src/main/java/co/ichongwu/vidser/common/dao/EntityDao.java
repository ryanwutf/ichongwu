package co.ichongwu.vidser.common.dao;



import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import co.ichongwu.vidser.utils.ConversionUtil;


public class EntityDao<E, I> extends JdbcDao {

    protected Class<E> entityClass;

    protected Class<I> idClass;

    protected Field idField;

    protected String idFieldName;
    
    protected List<String> fields = new ArrayList<String>();

    protected String table;

    protected List<String> columns = new ArrayList<String>();

    protected String idColumn;

    protected String selectColumn;

    protected String sortColumn;

    protected String insertField;

    protected String insertColumn;

    protected String updateFieldColumn;

    @SuppressWarnings("unchecked")
    protected EntityDao() {
        Class<?> clazz = getClass();
        Type superType = clazz.getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            ParameterizedType parameterizedSuperType = (ParameterizedType) superType;
            Type[] typeArgs = parameterizedSuperType.getActualTypeArguments();
            if (typeArgs.length == 2//
                            && typeArgs[0] instanceof Class//
                            && typeArgs[1] instanceof Class) {
                setClass((Class<E>) typeArgs[0], (Class<I>) typeArgs[1]);
            }
        }
    }

    public EntityDao(Class<E> entityClass, Class<I> idClass) {
        setClass(entityClass, idClass);
    }

    protected void setClass(Class<E> entityClass, Class<I> idClass) {
        this.entityClass = entityClass;
        this.idClass = idClass;
        table = tableName(entityClass);
        List<String> insertFieldList = new ArrayList<String>();
        List<String> insertColumnList = new ArrayList<String>();
        List<String> updateFieldColumnList = new ArrayList<String>();
        for (Class<?> clazz = entityClass; clazz != null; clazz = clazz.getSuperclass()) {
            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if(field.getAnnotation(Transient.class) != null) {
                	continue;
                }
                String fieldName = field.getName();
                fields.add(fieldName);
                Column column = field.getAnnotation(Column.class);
                String columnName;
                if (column != null && column.name().length() > 0) {
                    columnName = column.name();
                }
                else {
                    columnName = underscoreName(fieldName);
                }
                if (field.getAnnotation(Id.class) != null) {
                    idField = field;
                    if (!idField.isAccessible()) {
                        idField.setAccessible(true);
                    }
                    idFieldName = fieldName;
                    idColumn = columnName;
                    sortColumn = idColumn + " desc";
                }
                if (column == null || column.insertable()) {
                    insertFieldList.add(":" + fieldName);
                    insertColumnList.add(columnName);
                }
                if (column == null || column.updatable()) {
                    updateFieldColumnList.add(columnName + " = " + ":" + fieldName);
                }
                columns.add(columnName);
            }
        }
        selectColumn = join(columns, ", ");
        insertField = join(insertFieldList, ", ");
        insertColumn = join(insertColumnList, ", ");
        updateFieldColumn = join(updateFieldColumnList, ", ");
    }

    protected E entity() {
        try {
            return entityClass.newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }
    
    protected I id(E e) {
        try {
            return idClass.cast(idField.get(e));
        } catch (IllegalArgumentException e1) {
        } catch (IllegalAccessException e1) {
        }
        return null;
    }
    
    public E get(I id) {
        String sql = "select " + selectColumn + " from " + table + " where " + idColumn + " = :" + idFieldName;
        return get(sql, SqlParams.map(idFieldName, id), entityClass);
    }

    public List<E> list(Object params, QueryLimit queryLimit) {
        String sql = "select " + selectColumn + " from " + table;
        List<String> conditions = conditions(null, params);
        if (conditions.size() > 0) {
            sql += " where " + join(conditions, " and ");
        }
        sql += " order by " + sortColumn;
        sql = limit(sql, queryLimit);
        return list(sql, params, queryLimit, entityClass);
    }

    public Long count(Object params) {
        String sql = "select count(*) from " + table;
        List<String> conditions = conditions(null, params);
        if (conditions.size() > 0) {
            sql += " where " + join(conditions, " and ");
        }
        return get(sql, params, Long.class);
    }

    public I insert(E params) {
        String sql = "insert into " + table + " (" + insertColumn + ") values (" + insertField + ")";
        Number id = insert(sql, params);
        return ConversionUtil.convert(id, idClass);
    }

    public int update(E params) {
        String sql = "update " + table + " set " + updateFieldColumn + " where " + idColumn + " = :" + idFieldName;
        return update(sql, params);
    }

    public PutResult<I> put(E params) {
        String sql = "insert into " + table + " (" + insertColumn + ") values (" + insertField + ") on duplicate key update " + updateFieldColumn;
        PutResult<Number> putResult = put(sql, params, idColumn);
        return new PutResult<I>(putResult.getAffected(), ConversionUtil.convert(putResult.getId(), idClass));
    }

    public int delete(Object params) {
        String sql = "delete from " + table + " where ";
        if (idClass.isInstance(params)) {
            sql += idColumn + "  = :" + idFieldName;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(idFieldName, params);
            params = map;
        }
        else {
            sql += join(conditions(null, params), " and ");
        }
        return update(sql, params);
    }

    public List<String> conditions(String alias, Object params) {
        List<String> conditions = new ArrayList<String>();
        SqlParameterSource sqlParams = SqlParams.sqlParams(params);
        for (int i = 0, size = fields.size(); i < size; i++) {
            String field = fields.get(i);
            String columnName = columns.get(i);
            if (alias != null) {
        		columnName  = alias + "." + columnName;
            }
            for (Condition condition : Condition.values()) {
                String paramName = field + condition.suffix();
                if (sqlParams.hasValue(paramName)) {
                    conditions.add(condition.expression(paramName, columnName));
                }
            }
        }
        return conditions;
    }
    
    public Map<I, E> map(List<E> list) {
        Map<I, E> map = new HashMap<I, E>();
        for (E e : list) {
            map.put(id(e), e);
        }
        return map;
    }
    
    public static String tableName(Class<?> clazz) {
        String table;
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation != null && tableAnnotation.name().length() > 0) {
            table = tableAnnotation.name();
        }
        else {
            table = underscoreName(clazz.getSimpleName());
        }
        return table;
    }
    
    public  List<String> aliasColumns(String alias) {
        List<String> list = new ArrayList<String>(columns.size());
        for (String column : columns) {
        	list.add(alias + "." + column);
        }
        return list;
    }

}
