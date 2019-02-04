package com.mercadolibre.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mercadolibre.job.ForecastJob;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.mercadolibre")
public class AppConfig {
	
	@Autowired
	Environment environment;
	
	@Bean
	public DataSource dataSource() {
		final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setType(EmbeddedDatabaseType.HSQL);
		builder.addScript("sql/weatherTableCreate.sql");
		return builder.build();
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.HSQL);
		vendorAdapter.setGenerateDdl(true);
		final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setDataSource(dataSource());
		factory.setPackagesToScan(new String[] { "com.mercadolibre.model" });
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		final JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());

		return txManager;
	}
	
	@Bean
	public ForecastJob weatherForecastBean() {
		final ForecastJob weatherForecastJob = new ForecastJob();
		return weatherForecastJob;
	}
	
	@Bean
	public MethodInvokingJobDetailFactoryBean weatherForecastJob() {
		final MethodInvokingJobDetailFactoryBean jobDetailBean = new MethodInvokingJobDetailFactoryBean();
		jobDetailBean.setTargetObject(weatherForecastBean());
		jobDetailBean.setTargetMethod("execute");
		return jobDetailBean;
	}
	
	@Bean
	public SimpleTriggerFactoryBean simpleTriggerFactoryBean() {
		final SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
		trigger.setJobDetail(weatherForecastJob().getObject());
		trigger.setStartDelay(1000);
		trigger.setRepeatInterval(1200000);
		return trigger;
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		final SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setJobDetails(weatherForecastJob().getObject());
		schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean().getObject());
		return schedulerFactoryBean;
	}
}
