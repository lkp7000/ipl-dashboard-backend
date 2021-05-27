package ipl.dashboard.data;

import ipl.dashboard.data.model.Match;
import ipl.dashboard.data.model.Team;
import ipl.dashboard.data.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EntityManager em;

    private final String[] FIELD_NAMES = new String[]{
            "id", "city", "date", "player_of_match", "venue",
            "neutral_venue", "team1", "team2", "toss_winner",
            "toss_decision", "winner", "result", "result_margin",
            "eliminator", "method", "umpire1", "umpire2"
    };

    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("MatchItemReader")
                .resource(new ClassPathResource("match-data.csv"))
                .delimited()
                .names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {{
                    setTargetType(MatchInput.class);
                }})
                .build();
    }

    @Bean
    public MatchDataProcessor processor() {
        return new MatchDataProcessor();
    }

    @Bean
    public ItemWriter<Match> writer() {
        JpaItemWriter writer = new JpaItemWriter<Match>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<MatchInput, Match> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListener() {


            @Override
            public void beforeJob(JobExecution jobExecution) {
            }

            @Override
            @Transactional
            public void afterJob(JobExecution jobExecution) {
                if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                    log.info("!!! JOB FINISHED! Time to verify the results");
                    Map<String, Team> teamData = new HashMap<String, Team>();

                    em.createQuery("select distinct m.team1, count(*) from Match m group by m.team1", Object[].class)
                            .getResultList()
                            .stream()
                            .map(e -> new Team((String) e[0], (long) e[1]))
                            .forEach(team -> teamData.put(team.getTeamName(), team));
                    em.createQuery("select distinct m.team2, count(*) from Match m group by m.team2", Object[].class)
                            .getResultList()
                            .stream()
                            .forEach(e -> {
                                Team team = teamData.get((String)e[0]);
                                team.setTotalMatches(team.getTotalMatches() + (long)e[1]);
                            });
                    em.createQuery("select distinct m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
                            .getResultList()
                            .stream()
                            .forEach(e -> {
                                Team team = teamData.get((String)e[0]);
                                if(team != null) team.setTotalWins((long)e[1]);
                            });
                    teamData.values().forEach(team -> em.persist(team));
                    teamData.values().forEach(team -> System.out.println(team.getTeamName()+" "
                            +team.getTotalMatches()+" "+team.getTotalWins()));
                }
            }
        };
    }

}
