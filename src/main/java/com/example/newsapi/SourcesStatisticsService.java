package com.example.newsapi;

import com.example.newsapi.jpa.NewsRepository;
import com.example.newsapi.jpa.SourceRepository;
import com.example.newsapi.models.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
public class SourcesStatisticsService {
    private final NewsRepository newsRepository;
    private final SourceRepository sourceRepository;
    private final List<String> contentList = Collections.synchronizedList(new ArrayList<>());
    private static final Logger logger = LoggerFactory.getLogger(SourcesStatisticsService.class);

    public SourcesStatisticsService(NewsRepository newsRepository, SourceRepository sourceRepository) {
        this.newsRepository = newsRepository;
        this.sourceRepository = sourceRepository;
    }

    private void accumulateContent(String content) {
        contentList.add(content);
    }

    private void writeContentToFile(File file) {
        logger.info("Writing to " + file);
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            for (String content : contentList) {
                fileWriter.append(content).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void generateNewsStatistics() {
        contentList.clear();
        File file = new File("statistics_" + LocalDate.now().toString() + ".csv");

        try {
            if (file.createNewFile()) {
                logger.info("New file created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.append("Source ID,Source Name,Number of News").append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create threads and tasks
        List<Source> sources = sourceRepository.findAll();
        int numThreads = (int) Math.ceil((double) sources.size() / 10); // Round up to the nearest integer
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        List<Callable<Void>> tasks = new ArrayList<>();
        for (Source source : sources) {
            tasks.add(() -> {
                Long newsCount = newsRepository.countBySource(source);
                String content = source.getId().toString() + "," + source.getName() + "," + newsCount.toString();
                accumulateContent(content);
                return null;
            });
        }

        // Run tasks on threads
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

        // Write content to file
        writeContentToFile(file);
    }
}
