package mathpar.web.learning.school._configs;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import mathpar.web.learning.school.utils.MathparProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
@Slf4j
@Configuration
@EntityScan(basePackages = "mathpar.web.learning.school.entities")
@EnableJpaRepositories(basePackages = "mathpar.web.learning.school.repositories")
public class DatabaseConfiguration {
    @Bean
    @Profile("!test")
    public DataSource dataSource(MathparProperties mathparProperties){
        var datasource = new MysqlDataSource();
        datasource.setUrl(mathparProperties.getDatabaseUrl());
        datasource.setUser(mathparProperties.getDatabaseUsername());
        datasource.setPassword(mathparProperties.getDatabasePassword());
        log.info("COnnection String: " + datasource.getUrl());


        return datasource;
    }
}
