package com.example.newsapi;

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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
public class SourcesStatisticsService {
    private final NewsRepository newsRepository;
    private final SourceRepository sourceRepository;
    private final List<String> contentList = Collections.synchronizedList(new ArrayList<>());


    public SourcesStatisticsService(NewsRepository newsRepository, SourceRepository sourceRepository) {
        this.newsRepository = newsRepository;
        this.sourceRepository = sourceRepository;
    }

    private void accumulateContent(String content) {
        contentList.add(content);
    }
    private void writeContentToFile(File file) {
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
        File file = new File("statistics_" + LocalDate.now().toString() + ".csv");
        try(FileWriter fileWriter = new FileWriter(file, true)){
            fileWriter.append("Source ID,Source Name,Number of News").append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //create threads and tasks
        List<Source> sources = sourceRepository.findAll();
        int numThreads = sources.size()/5;//5 sources per 1 thread for testing only. You can increase the number of threads if necessary
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

        //run tasks on threads
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

        // write content to file
        writeContentToFile(file);
    }
}