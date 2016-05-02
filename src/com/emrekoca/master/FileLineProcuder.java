package com.emrekoca.master;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

/**
 * 
 * @author Emre Koca
 *
 */
public class FileLineProcuder implements Runnable, LineReader {
	private BlockingQueue<String> queue;
	private CountDownLatch countDownLatch;
	private Path filePath;

	public FileLineProcuder(BlockingQueue<String> queue, CountDownLatch lacth, Path path) {
		this.queue = queue;
		this.countDownLatch = lacth;
		this.filePath = path;
	}

	@Override
	public void run() {
		try (Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8)) {
			lines.onClose(this::shutdownHook).forEach(this::lineReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void lineReader(final String line) {
		try {
			queue.put(line);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void shutdownHook() {
		while (!queue.isEmpty()) {
			try {
				System.out.println("Waiting for queue to be empty before closing up");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		countDownLatch.countDown();
	}
}
