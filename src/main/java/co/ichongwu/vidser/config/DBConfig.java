package co.ichongwu.vidser.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DBConfig {

	
    @Bean
    @ConfigurationProperties(prefix="spring.gina")
    public DataSource ginaDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix="spring.local")
//    public DataSource localDataSource() {
//        return DataSourceBuilder.create().build();
//    }
    
    @Bean
    public JdbcTemplate ginaJdbcTemplate(
            @Qualifier("ginaDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

//    @Bean
//    public JdbcTemplate localJdbcTemplate(
//            @Qualifier("localDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }

}
