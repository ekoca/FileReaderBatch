package com.emrekoca;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.emrekoca.master.FileLineProcuder;
import com.emrekoca.processor.Processor;
import com.emrekoca.worker.FileLineConsumer;
import com.emrekoca.worker.WorkerThreadFactory;

/**
 * 
 * @author Emre Koca
 *
 */
@Component
public class ApplicationStarter implements Runnable {
	private final int QUEUE_SIZE = 5;
	private BlockingQueue<String> queue;
	private CountDownLatch latch;
	@Autowired
	Processor<String> processor;
	@Autowired
	private ResourceLoader resourceLoader;

	public ApplicationStarter() {
		this.queue = new ArrayBlockingQueue<String>(QUEUE_SIZE);
		this.latch = new CountDownLatch(1);
	}

	@Override
	public void run() {
		Instant startTime = Instant.now();
		excute();
		Duration duration = Duration.between(startTime, Instant.now());
		System.out.println("Total running time :" + duration.toMillis());

	}

	private void excute() {
		// Let producer do its job
		ExecutorService master = Executors.newSingleThreadExecutor();
		master.submit(new FileLineProcuder(queue, latch, getFilePath()));
		// Let consumers work too
		ScheduledExecutorService workers = Executors.newScheduledThreadPool(QUEUE_SIZE, new WorkerThreadFactory());
		workers.scheduleAtFixedRate(new FileLineConsumer(queue, processor), 5, 1, TimeUnit.MILLISECONDS);
		try {
			// all we need to wait till job is finished
			latch.await();
			destroy(master, workers);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Path getFilePath() {
		try {
			Resource resource = resourceLoader.getResource("classpath:FL_insurance_sample.csv");
			return resource.getFile().toPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void destroy(ExecutorService... pools) {
		for (ExecutorService pool : pools) {
			shutdownAndAwaitTermination(pool);
		}
	}

	void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}
}
