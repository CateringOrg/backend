package pl.edu.pw.ee.catering_backend.infrastructure.cron;

import org.springframework.scheduling.annotation.Scheduled;

public abstract class BaseCronJob implements CronJob {

    /**
     * Calls the execute method of the implementing class to perform the scheduled task.
     * The method is annotated with @Scheduled and the cron expression is read from the application.properties file.
     */
    @Scheduled(cron = "#{@environment.getProperty('cron.expression')}")
    public void schedule() {
        execute();
    }
}
