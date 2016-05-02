package com.emrekoca.worker;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * 
 * @author Emre Koca
 *
 */
public class FileLineConsumer implements Runnable {
	private final BlockingQueue<String> queue;
	private final AtomicInteger counter;
	private final Consumer<String> func;

	public FileLineConsumer(BlockingQueue<String> queue, Consumer<String> func) {
		this.queue = queue;
		this.counter = new AtomicInteger(0);
		this.func = func;
	}

	@Override
	public void run() {
		try {
			Optional<String> line = Optional.ofNullable(queue.poll());
			if (line.isPresent()) {
				counter.incrementAndGet();
				func.accept(line.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}