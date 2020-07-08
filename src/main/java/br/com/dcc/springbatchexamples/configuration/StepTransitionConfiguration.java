package br.com.dcc.springbatchexamples.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class StepTransitionConfiguration {

	@Bean
	public Step stepTransitionStep1(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("StepTransitionStep1")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						log.info(">> This is the StepTransition Step 1");
						return RepeatStatus.FINISHED;
					}
				})
				.build();
	}

	@Bean
	public Step stepTransitionStep2(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("StepTransitionStep2")
				.tasklet((contribution, chunkContext) -> {
						log.info(">> This is the StepTransition Step 2");
						return RepeatStatus.FINISHED;
					})
				.build();
	}

	@Bean
	public Step stepTransitionStep3(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("StepTransition Step3")
				.tasklet((contribution, chunkContext) -> {
						log.info(">> This is the StepTransition Step 3");
						return RepeatStatus.FINISHED;
					})
				.build();
	}

	@Bean
	public Job stepTransitionJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		return jobBuilderFactory.get("StepTransitionJob")
				/*
				 * Traditional way...
				 */
				.start(stepTransitionStep1(stepBuilderFactory))
				.next(stepTransitionStep2(stepBuilderFactory))
				.next(stepTransitionStep3(stepBuilderFactory))
				.build();

				/*
				 *  Another way...
				 */
//				.start(stepTransitionStep1())
//				.on("COMPLETED").to(stepTransitionStep2())
//				.from(stepTransitionStep2()).on("COMPLETED").to(stepTransitionStep3())
//				.from(stepTransitionStep3()).end()
//				.build();
	}

}
