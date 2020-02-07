package ru.otus.lib.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MigrationBatchService {

    private final Logger logger = LoggerFactory.getLogger("MigrationBatchService");

    private final JobLauncher jobLauncher;
    
    private final Job migrateDataJob;
    
    public void launchJob() {
        try {
            jobLauncher.run(migrateDataJob, new JobParameters());
        } catch (Exception e) {
            logger.error("Job failed", e);
            throw new RuntimeException(e);
        } 
    }
}
