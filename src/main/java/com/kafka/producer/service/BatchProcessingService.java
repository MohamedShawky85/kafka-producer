package com.kafka.producer.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kafka.producer.entity.Employee;
import com.kafka.producer.repos.EmployeeRepository;

@Service
public class BatchProcessingService {

	private static final Logger log = Logger.getLogger(BatchProcessingService.class.getName());
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private KafkaTemplate<?, String> kafkaTemplate;

	@Value("${spring.kafka.template.default-topic}")
	private String kafkatopicName;

	public void processAllBatchs() {

		log.log(Level.INFO, "Inside processBatch method with a topic name: {0} ", kafkatopicName);
		// Fetch data from SingleStore database
		List<Employee> employeeList = employeeRepository.findAll();
		// Process each entity and send it to Kafka
		for (Employee entity : employeeList) {
			kafkaTemplate.send(kafkatopicName, entity.toString()); // Send each entity as a message to Kafka
		}
	}

	public void processBatch() {
		
		log.log(Level.INFO, "Inside processBatch method with a topic name: {0} ", kafkatopicName);
		// Set the batch size
		int pageSize = 1; // Batch size
	    int pageNumber = 0;
	    boolean hasMoreRecords = true;

	    // Fetch and process data from SingleStore database in batches
	    while (hasMoreRecords) {
	        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(pageNumber, pageSize));

	        log.log(Level.INFO, "employeeList Size: {0} ", employeePage.getSize());
	        // Process each entity and send it to Kafka
	        for (Employee entity : employeePage.getContent()) {
	        	 log.log(Level.INFO, "Processed entity: {0} ",entity);
	            kafkaTemplate.send(kafkatopicName, entity.toString()); // Send each entity as a message to Kafka
	        }

	        // Update the page number for the next batch
	        pageNumber++;

	        // Check if there are more records
	        hasMoreRecords = employeePage.hasNext();
	    }
		
	}

}