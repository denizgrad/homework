package com.dozen.hangman;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;
@SpringBootApplication
@ComponentScan({"com.dozen.hangman.service", "com.dozen.hangman.dao","com.dozen.hangman.controller"})
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class HangmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(HangmanApplication.class, args);
	}
	
	 private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
		
	    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
	    private static final String PROPERTY_NAME_HIBERNATE_HBMDDL = "hibernate.hbm2ddl.auto";
		@Resource
		private Environment env;
		
		@Bean
		public DataSource dataSource() {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			
			dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
			dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
			dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
			dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
			
			return dataSource;
		}
		
		@Bean
		public org.springframework.orm.hibernate5.LocalSessionFactoryBean sessionFactory() {
			org.springframework.orm.hibernate5.LocalSessionFactoryBean sessionFactoryBean = new org.springframework.orm.hibernate5.LocalSessionFactoryBean();
			sessionFactoryBean.setDataSource(dataSource());
			sessionFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
			sessionFactoryBean.setHibernateProperties(hibProperties());
			return sessionFactoryBean;
		}
		
		private Properties hibProperties() {
			Properties properties = new Properties();
			properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
			properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
			properties.put(PROPERTY_NAME_HIBERNATE_HBMDDL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBMDDL));
			return properties;	
		}
		
		@Bean
		public org.springframework.orm.hibernate5.HibernateTransactionManager transactionManager() {
			org.springframework.orm.hibernate5.HibernateTransactionManager transactionManager = new org.springframework.orm.hibernate5.HibernateTransactionManager();
			transactionManager.setSessionFactory(sessionFactory().getObject());
			return transactionManager;
		}
		@Bean
		public SpringLiquibase liquibase () {
			SpringLiquibase result = new SpringLiquibase();
			result.setDataSource(dataSource());
			result.setChangeLog("classpath:db-changelog.xml");
			return result;
		}
		@Bean(name = "messageSource")
		public ResourceBundleMessageSource messageSource() {
			ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
			messageSource.setBasenames("labels");
			return messageSource;
		}
}
