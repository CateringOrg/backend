package pl.edu.pw.ee.catering_backend.infrastructure.cron;

public interface CronJob {
    void execute();

    String getFrequency();
}
