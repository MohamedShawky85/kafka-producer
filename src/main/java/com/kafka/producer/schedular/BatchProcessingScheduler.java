package com.kafka.producer.schedular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kafka.producer.service.BatchProcessingService;

@Component
public class BatchProcessingScheduler {
	@Autowired
	private BatchProcessingService batchProcessingService;

	@Scheduled(fixedDelay = 10000) // Run the batch processing every 10 seconds
	public void runBatchProcessing() {
		batchProcessingService.processBatch();
	}
}