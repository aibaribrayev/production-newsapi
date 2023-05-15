package com.example.newsapi.scheduledStatsTask;

import com.example.newsapi.jpa.NewsRepository;
import com.example.newsapi.jpa.SourceRepository;
import com.example.newsapi.models.Source;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class SourcesStatisticsService {
    private final NewsRepository newsRepository;
    private final SourceRepository sourceRepository;

    public SourcesStatisticsService(NewsRepository newsRepository, SourceRepository sourceRepository) {
        this.newsRepository = newsRepository;
        this.sourceRepository = sourceRepository;
    }

   // @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "0 */2 * * * *")
    public void generateNewsStatistics() {

        //create file called statistics_current_date.csv and add column titles
        File file = new File("statistics_" + LocalDate.now().toString() + ".csv");
        try(FileWriter fileWriter = new FileWriter(file, true)){
            fileWriter.append("Source ID,Source Name,Number of News").append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //divide tasks to threads
        List<Source> sources = sourceRepository.findAll();
        int numThreads = sources.size()/5;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        List<Callable<Void>> tasks = new ArrayList<>();
        for (Source source : sources) {
            tasks.add(() -> {
                // Fetch news articles for each source and update the article count
                Long newsCount = newsRepository.countBySource(source);
                writeToFile(source.getId().toString() +
                        "," + source.getName() +
                        "," + newsCount.toString(),
                        file);
                return null;
            });
        }

        try {
            List<Future<Void>> futures = executorService.invokeAll(tasks);
            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }

    private synchronized void writeToFile(String content, File file) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.append(content.toString()).append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



