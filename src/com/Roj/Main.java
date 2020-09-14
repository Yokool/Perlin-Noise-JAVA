package com.Roj;

public class Main {

	public static void main(String[] args) {
		
		long timeStart = System.nanoTime();
		PerlinNoise perlinNoise = new PerlinNoise("1000x_jokesjokesjokes");
		perlinNoise.noise(12.5, 68.4);
		long timeTook = System.nanoTime() - timeStart;
		
		System.out.println("Time took to finish: " + timeTook / 10000000.0);
	}

}
