package com.emrekoca.worker;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author Emre Koca
 *
 */
public class WorkerThreadFactory implements ThreadFactory {
	protected final AtomicInteger threadNumber = new AtomicInteger(0);

	public Thread newThread(Runnable r) {
		return new Thread(r, "Worker #" + threadNumber.incrementAndGet());
	}
}