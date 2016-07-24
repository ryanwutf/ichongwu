package co.ichongwu.vidser.common.dao;


import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Repository
@Primary
public class JdbcDao {
	
	@Autowired(required = false)
    protected DataSource dataSource;

    protected NamedParameterJdbcTemplate jdbc;

    protected PlatformTransactionManager transactionManager;

    protected TransactionTemplate transactionTemplate;

    private ThreadLocal<TransactionStatus> transactionStatusLocal = new ThreadLocal<TransactionStatus>();
    
    private ThreadLocal<Integer> transactionDepthLocal = new ThreadLocal<Integer>();
    
    
    @Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbc = new NamedParameterJdbcTemplate(
				jdbcTemplate);
	}
    
    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

    @Autowired
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}


    public <T> T get(String sql, Object params, Class<T> clazz) {
        List<T> results = jdbc.query(sql, SqlParams.sqlParams(params), rowMapper(clazz));
        return DataAccessUtils.uniqueResult(results);
    }

    public <T> List<T> list(String sql, Object params, QueryLimit queryLimit, Class<T> clazz) {
        return jdbc.query(sql, SqlParams.sqlParams(params, queryLimit), rowMapper(clazz));
    }
    public <T> List<T> list(String sql, Object params, Class<T> clazz) {
        return jdbc.query(sql, SqlParams.sqlParams(params), rowMapper(clazz));
    }

    public Number insert(String sql, Object params) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, SqlParams.sqlParams(params), keyHolder);
        return keyHolder.getKey();
    }

    public int update(String sql, Object params) {
        return jdbc.update(sql, SqlParams.sqlParams(params));
    }

    public PutResult<Number> put(String sql, Object params, String idColumn) {
        int affected;
        Number id = null;
        if (idColumn != null) {
        	KeyHolder keyHolder = new GeneratedKeyHolder();
        	affected = jdbc.update(sql, SqlParams.sqlParams(params), keyHolder, new String[] { idColumn });
        	if (keyHolder.getKeyList().size() == 1 && keyHolder.getKeyList().get(0).size() == 1) {
                id = keyHolder.getKey();
            }
        }
        else {
        	affected = jdbc.update(sql, SqlParams.sqlParams(params));
        }
        return new PutResult<Number>(affected > 0 ? 1 : 0, id);
    }

    public void begin() {
        Integer depth = transactionDepthLocal.get();
        if (depth == null) {
            transactionStatusLocal.set(transactionManager.getTransaction(null));
            transactionDepthLocal.set(1);
        }
        else {
            transactionDepthLocal.set(depth + 1);
        }
    }

    public void commit() {
        Integer depth = transactionDepthLocal.get();
        if (depth == 1) {
            transactionDepthLocal.remove();
            TransactionStatus transactionStatus = transactionStatusLocal.get();
            transactionStatusLocal.remove();
            transactionManager.commit(transactionStatus);
        }
        else {
            transactionDepthLocal.set(depth - 1);
        }
    }

    public void rollback() {
        TransactionStatus transactionStatus = transactionStatusLocal.get();
        Integer depth = transactionDepthLocal.get();
        if (depth == 1) {
            transactionDepthLocal.remove();
            transactionStatusLocal.remove();
            transactionManager.rollback(transactionStatus);
        }
        else {
            transactionStatus.setRollbackOnly();
            transactionDepthLocal.set(depth - 1);
        }
    }

    public <T> T transaction(TransactionCallback<T> callback) {
        return transactionTemplate.execute(callback);
    }

    public static String underscoreName(String name) {
        if (name == null || name.length() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(Character.toLowerCase(name.charAt(0)));
        for (int i = 1, length = name.length(); i < length; i++) {
            Character c = name.charAt(i);
            Character clc = Character.toLowerCase(c);
            if (!c.equals(clc)) {
                builder.append('_').append(clc);
            }
            else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public static <T> String join(Collection<T> collection, Object separator) {
        StringBuilder builder = new StringBuilder();
        if (collection != null) {
            boolean addition = false;
            for (T t : collection) {
                if (addition && separator != null) {
                    builder.append(separator);
                }
                builder.append(t);
                addition = true;
            }
        }
        return builder.toString();
    }

    public static String limit(String sql, QueryLimit queryLimit) {
        if (queryLimit != null && queryLimit.getLimit() != null) {
            if (queryLimit.getStart() == null) {
                return sql + " limit :limit ";
            }
            else {
                return sql + " limit :start, :limit ";
            }
        }
        return sql;
    }

    public static <T> RowMapper<T> rowMapper(Class<T> clazz) {
        if (BeanUtils.isSimpleProperty(clazz)) {
            return new SingleColumnRowMapper<T>(clazz);
        }
        else {
            return new BeanPropertyRowMapper<T>(clazz);
        }
    }

}
