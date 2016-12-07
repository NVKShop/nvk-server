package hu.unideb.inf.rft.nvkshop.config;

import java.util.Properties;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan("hu.unideb.inf.rft.nvkshop")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("hu.unideb.inf.rft.nvkshop.repositories")
public class DatabaseConfig {

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	private static final String JDBC_USERNAME = "nvkshop.username";
	private static final String JDBC_URL = "nvkshop.dburl";
	private static final String JDBC_PASSWORD = "nvkshop.password";

	@Autowired
	private Environment env;

	@Bean(destroyMethod = "close")
	public HikariDataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setMaximumPoolSize(100);
		config.setJdbcUrl(env.getRequiredProperty(JDBC_URL));
		config.setUsername(env.getRequiredProperty(JDBC_USERNAME));
		config.setPassword(env.getRequiredProperty(JDBC_PASSWORD));
//		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		HikariDataSource ds = new HikariDataSource(config);
		ds.addDataSourceProperty("cachePrepStmts", true);
		ds.addDataSourceProperty("prepStmtCacheSize", 250);
		ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
		ds.addDataSourceProperty("useServerPrepStmts", true);
//		ds.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		return ds;
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager() throws ClassNotFoundException {
		return new JpaTransactionManager(entityManagerFactory().getObject());
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws ClassNotFoundException {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("hu.unideb.inf.rft.nvkshop");
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		Properties jpaProperties = new Properties();
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, "true");
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "false");
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean;
	}

	@Bean
	public SharedEntityManagerBean sharedEntityManager() throws ClassNotFoundException {
		SharedEntityManagerBean sharedEntityManagerBean = new SharedEntityManagerBean();
		sharedEntityManagerBean.setEntityManagerFactory(entityManagerFactory().getObject());
		return new SharedEntityManagerBean();
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		AbstractJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

		jpaVendorAdapter.setDatabasePlatform(env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		jpaVendorAdapter.setShowSql(false);

		return jpaVendorAdapter;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
		schedulerFactoryBean.setDataSource(dataSource());
		return schedulerFactoryBean;
	}

}