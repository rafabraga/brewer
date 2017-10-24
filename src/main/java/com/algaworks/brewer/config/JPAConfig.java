package com.algaworks.brewer.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.Cervejas;

@Configuration
@EnableJpaRepositories(basePackageClasses = Cervejas.class)
public class JPAConfig {

    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        // Buscar a configuração no Tomcat (que é configurado pelo context.xml).
        dataSourceLookup.setResourceRef(Boolean.TRUE);
        return dataSourceLookup.getDataSource("jdbc/brewerDB");
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL);
        adapter.setShowSql(Boolean.FALSE);
        // Gerar as tabelas a partir dos mapeamentos.
        adapter.setGenerateDdl(Boolean.FALSE);
        adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        return adapter;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(final DataSource dataSource, final JpaVendorAdapter jpaVendorAdapter) {
        final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(jpaVendorAdapter);
        factory.setPackagesToScan(Cerveja.class.getPackage().getName());
        factory.setMappingResources("sql/consultas-nativas.xml");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

}
