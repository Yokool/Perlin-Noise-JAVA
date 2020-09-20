package com.Roj;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PerlinNoise
{
	/**
	 * Seed format
	 * lengthXrandom_sequence
	 */
	private String seed;
	
	private int[] permutations;
	
	public PerlinNoise(String seed)
	{
		this.seed = seed;
		generatePermutationsFromSeed();
	}
	
	public double noise(double x, double y)
	{
		System.out.println("**********************");
		System.out.println("Noise Function Started");
		System.out.println("X: " + x + " Y: " + y);
		System.out.println("**********************");
		
		
		int xSquare = (int)x;
		int ySquare = (int)y;
		System.out.println("xSquare: " + xSquare + " ySquare: " + ySquare);
		
		// 
		// AB  BB
		//
		// AA  BA
		//
		//
		System.out.println("**********************");
		System.out.println("DIFFERENCE VECTORS:");
		
		Vector2 deltaAA = new Vector2(x - xSquare, y - ySquare);
		Vector2 deltaBA = new Vector2(x - (xSquare + 1), y - ySquare);
		
		Vector2 deltaAB = new Vector2(x - xSquare, y - (ySquare + 1));
		Vector2 deltaBB = new Vector2(x - (xSquare + 1), y - (ySquare + 1));
		
		System.out.println("AB: " + deltaAB + "\nBB: " + deltaBB);
		System.out.println("AA: " + deltaAA + "\nBA: " + deltaBA);
		
		System.out.println("**********************");
		
		// Hashes
		int hAA = permutations[permutations[(permutations[xSquare]+ySquare)%permutations.length]];
		int hBA = permutations[permutations[(permutations[xSquare + 1]+ySquare)%permutations.length]];;
		int hAB = permutations[permutations[(permutations[xSquare]+ySquare + 1)%permutations.length]];;
		int hBB = permutations[permutations[(permutations[xSquare + 1]+ySquare + 1)%permutations.length]];;
		
		
		// TODO: SOMEWHAT TESTED
		
		Vector2 gradientVectorAA = getCornerVectorFromHash(hAA);
		Vector2 gradientVectorBA = getCornerVectorFromHash(hBA);
		Vector2 gradientVectorAB = getCornerVectorFromHash(hAB);
		Vector2 gradientVectorBB = getCornerVectorFromHash(hBB);
		
		System.out.println("GRADIENT VECTORS:");
		System.out.println("**********************");
		System.out.println("AB: " + gradientVectorAB + "\nBB: " + gradientVectorBB);
		System.out.println("AA: " + gradientVectorAA + "\nBA: " + gradientVectorBA);
		System.out.println("**********************");
		
		gradientVectorAA.normalize();
		gradientVectorBA.normalize();
		gradientVectorAB.normalize();
		gradientVectorBB.normalize();
		
		
		double dotProductAA = dotProduct(gradientVectorAA, deltaAA);
		double dotProductBA = dotProduct(gradientVectorBA, deltaBA);
		double dotProductAB = dotProduct(gradientVectorAB, deltaAB);
		double dotProductBB = dotProduct(gradientVectorBB, deltaBB);
		
		
		System.out.println("DOT PRODUCTS:");
		System.out.println("**********************");
		System.out.println("AB: " + dotProductAB + "\nBB: " + dotProductBB);
		System.out.println("AA: " + dotProductAA + "\nBA: " + dotProductBA);
		System.out.println("**********************");
		
		
		
		// TODO: UNTESTED
		/*
		double lerpTopAxis = ((xSquare + 1 - x)/(xSquare + 1 - xSquare)) * dotProductAA;
		lerpTopAxis += ((x - xSquare)/(xSquare + 1 - xSquare)) * dotProductBA;
		
		double lerpBottomAxis = ((xSquare + 1 - x)/(xSquare + 1 - xSquare)) * dotProductAB;
		lerpBottomAxis += ((x - xSquare)/(xSquare + 1 - xSquare)) * dotProductBB;
		
		double finalValue = ((ySquare + 1 - y)/(ySquare + 1 - ySquare))*lerpBottomAxis;
		finalValue += ((y - ySquare)/(ySquare + 1 - ySquare))*lerpTopAxis;
		*/
		
		double xValBottom = lerp(dotProductAA, dotProductBA, fade(x - xSquare));
		double xValTop = lerp(dotProductAB, dotProductBB, fade(x - xSquare));
		double finalValue = lerp(xValBottom, xValTop, fade(y - ySquare));
		
		return finalValue;
		
	}
	
	private double fade(double t)
	{
		return t * t * t * (t * (t * 6 - 15) + 10);
	}
	
	private double lerp(double x, double y, double v)
	{
		return x + ((y - x) * v);
	}
	
	/*
	private double lerp(Vector2 pointOne, Vector2 pointTwo, double x)
	{
		
		double x0 = pointOne.getX();
		double x1 = pointTwo.getX();
		
		double y0 = pointOne.getY();
		double y1 = pointTwo.getY();
		
		return (y0 * (x1 - x) + y1 * (x - x0)) / (x1 - x0);
		
	}
	*/
	private double dotProduct(Vector2 v, Vector2 v1)
	{
		return (v.getX() * v1.getX()) + (v.getY() * v1.getY());
	}
	
	
	private Vector2 getCornerVectorFromHash(int seed)
	{
		// Sqrt(2) = 1.41421356237
		switch(seed % 7)
		{
		case 0:
			return new Vector2(1, 0);
		case 1:
			return new Vector2(0, 1);
		case 2:
			return new Vector2(-1, 0);
		case 3:
			return new Vector2(0, -1);
		case 4:
			return new Vector2(1, 1.41421356237);
		case 5:
			return new Vector2(1, -1.41421356237);
		case 6:
			return new Vector2(-1, 1.41421356237);
		case 7:
			return new Vector2(-1, -1.41421356237);
		default:
			System.out.println("Something went wrong.");
			return null;
		}
	}
	
	private void generatePermutationsFromSeed()
	{
		
		System.out.println("=== --- ===");
		System.out.println("Generating pemutation from seed: " + seed);
		System.out.println("Searching for the length parameter.");
		
		Pattern lengthPattern = Pattern.compile("^\\d+x_");
		Matcher lengthMatcher = lengthPattern.matcher(seed);
		
		String permutationArrayLength;
		if(lengthMatcher.find())
		{
			permutationArrayLength = lengthMatcher.group(0);
			permutationArrayLength = permutationArrayLength.replace("x_", "");
			permutations = new int[Integer.parseInt(permutationArrayLength)];
			System.out.println("SUCCESS: Length Parameter Found with value: " + permutations.length);
		}
		else
		{
			throw new RuntimeException("FAILURE: Length Parameter not found.");
		}
		
		Pattern permutationPattern = Pattern.compile("x_\\D+");
		Matcher permutationMatcher = permutationPattern.matcher(seed);
		
		String seedPayload;
		
		if(permutationMatcher.find())
		{
			seedPayload = permutationMatcher.group(0).replace("x_", "");
			System.out.println("SUCCESS: Seed Payload was found with the value " + seedPayload);
		}
		else
		{
			throw new RuntimeException("FAILURE: Seed payload not found.");
		}
		
		
		System.out.println("Generating permutations");
		System.out.println("-----------------------");
		
		// Create an array of random values from 0 to permutation.length()
		ArrayList<Integer> randomValues = new ArrayList<Integer>();
		for(int i = 0; i < permutations.length; ++i)
		{
			randomValues.add(i);
		}
		
		
		// Start assigning values pseudo randomly
		for(int i = 0; i < permutations.length; ++i)
		{
			
			if(randomValues.size() == 0)
			{
				break;
			}
			
			// Pseudorandom index generation that gets larger as we go further
			int stringIndex = i + seedPayload.charAt(0);
			stringIndex *= i;
			
			// Get the amount of times that the overflow occurred
			int randomValueIndex = (stringIndex - (stringIndex % seedPayload.length())) / seedPayload.length();
			randomValueIndex = Math.abs(randomValueIndex);
			
			// Get a pseudorandom character value
			randomValueIndex *= ((int)seedPayload.charAt(randomValueIndex % seedPayload.length()));
			
			// Overflow it to the random value size
			randomValueIndex %= randomValues.size();
			
			int valueAt = -1;
			int tracker = 1;
			
			while(valueAt == -1)
			{
				int index = Math.abs((randomValueIndex * tracker));
				index %= randomValues.size();
				
				valueAt = randomValues.get(index);
				randomValues.remove(index);
				
				tracker++;
			}
			
			permutations[i] = valueAt;
			
			System.out.println("I: " + permutations[i]);
		}
		System.out.println("-----------------------");
		System.out.println("Permutation method finished.");
		System.out.println("-----------------------");
		
	}
	
	
}
