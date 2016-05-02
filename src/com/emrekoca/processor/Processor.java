package com.emrekoca.processor;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Emre Koca
 *
 */
@Component
public class Processor<T> implements Consumer<T> {

	/**
	 * Put logic here for processing the lines. More than one thread may access
	 * here at the same time so watch out
	 */
	@Override
	public void accept(T t) {
		System.out.println(t);
	}

}
