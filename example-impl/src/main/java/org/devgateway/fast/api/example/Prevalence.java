package org.devgateway.fast.api.example;

import org.devgateway.fast.api.commons.Config;
import org.devgateway.fast.api.example.io.CSV2Prevalence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories
@EntityScan
@SpringBootApplication
@Import(Config.class)
public class Prevalence implements CommandLineRunner {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CSV2Prevalence csv2Prevalence;

    @Value("${tcdi.startup.import}")
    private Boolean startupImport = false;

    @Value("${tcdi.ckan.host}")
    private String host = "";


    @Value("${tcdi.ckan.uri}")
    private String uri = "/api/3/action/datastore_search?limit=50&resource_id=";

    @Value("${tcdi.ckan.resource}")
    private String resource = "";

    @Value("${tcdi.prevalence.file}")
    String file;


    @Value("${tcdi.date.format}")
    String format;

    public Prevalence(CSV2Prevalence csv2Prevalence) {
        this.csv2Prevalence = csv2Prevalence;
    }


    public static void main(final String[] args) {
        SpringApplication.run(Prevalence.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        logger.info("................... Running Prevalence Micro Service ........");
        if (startupImport) {
            csv2Prevalence.start(file, format);

        }
    }
}
