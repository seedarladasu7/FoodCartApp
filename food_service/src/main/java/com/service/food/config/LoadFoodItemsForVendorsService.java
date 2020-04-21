package com.service.food.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.service.food.entity.FoodItem;
import com.service.food.processor.ValidationFoodItemProcessor;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class LoadFoodItemsForVendorsService {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Value("classPath:/input/Vendor_Record_20042020.csv")
	private Resource inputResource;

	@Autowired
	DataSource dataSource;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("readCSVJob")
	private Job readCSVJob;

	// @Scheduled(cron = "0 */1 * * * ?")
	public void performReadCSVFileJob() throws Exception {
		System.out.println("Starting the job: readCSVFileJob");
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(readCSVJob, params);
	}

	@Bean(name = "readCSVJob")
	public Job readCSVFileJob() {
		return jobBuilderFactory.get("readCSVFileJob").incrementer(new RunIdIncrementer()).start(readCSVFileStep())
				.build();
	}

	@Bean
	public Step readCSVFileStep() {
		return stepBuilderFactory.get("readCSVFileStep").<FoodItem, FoodItem>chunk(5).reader(readCSVFileReader())
				.processor(readCSVFileProcessor()).writer(jpaEmployeeFromCSVFileWriter()).build();
	}

	@Bean
	public ItemProcessor<FoodItem, FoodItem> readCSVFileProcessor() {
		return new ValidationFoodItemProcessor();
	}

	@Bean
	public FlatFileItemReader<FoodItem> readCSVFileReader() {
		FlatFileItemReader<FoodItem> itemReader = new FlatFileItemReader<>();
		itemReader.setLineMapper(lineMapper());
		itemReader.setLinesToSkip(1);
		itemReader.setResource(inputResource);
		return itemReader;
	}

	@Bean
	public LineMapper<FoodItem> lineMapper() {
		DefaultLineMapper<FoodItem> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("itemname", "itemprice", "date", "vendorid");
		lineTokenizer.setIncludedFields(0, 1, 2, 3);
		BeanWrapperFieldSetMapper<FoodItem> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(FoodItem.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}

	@Bean
	public JpaItemWriter<FoodItem> jpaEmployeeFromCSVFileWriter() {
		JpaItemWriter<FoodItem> jpaEmpWriter = new JpaItemWriter<>();
		jpaEmpWriter.setEntityManagerFactory(entityManagerFactory);
		return jpaEmpWriter;
	}

	@Bean
	public JdbcBatchItemWriter<FoodItem> writer() {
		JdbcBatchItemWriter<FoodItem> itemWriter = new JdbcBatchItemWriter<>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql(
				"INSERT INTO FOODITEM (ITEM_NAME, ITEM_PRICE, PRICE, VENDOR_ID) VALUES (:itemname, :itemprice, :date, :vendorid)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<FoodItem>());
		return itemWriter;
	}

}
