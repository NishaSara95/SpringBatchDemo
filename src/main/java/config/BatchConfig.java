package config;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tasks.TaskOne;
import tasks.TaskTwo;

@Configuration
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


// creating steps using step builder factory
    @Bean
    private Step stepOne(){
        return stepBuilderFactory.get("stepOne").tasklet(new TaskOne()).build();
    }

    @Bean
    private Step stepTwo(){
        return stepBuilderFactory.get("stepTwo").tasklet(new TaskTwo()).build();
    }

    //Incrementer is used to give unique id for every job instance
    @Bean(name="batchJobs")
    private Job batchJobs(){
        return jobBuilderFactory.get("batchJobs")
                .incrementer(new RunIdIncrementer())
                .start(stepOne())
                .next(stepTwo())
                .build();

    }
}
