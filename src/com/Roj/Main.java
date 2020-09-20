package com.Roj;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {
		
		PerlinNoise perlinNoise = new PerlinNoise("12x_aaa");
		
		
		int imgX = 1;
		int imgY = 1;
		
		BufferedImage bufferedImage = new BufferedImage(imgX, imgY, BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0; x < imgX; ++x)
		{
			for(int y = 0; y < imgY; ++y)
			{
				
				double random = perlinNoise.noise(x * 0.01, y * 0.01);
				
				System.out.println(random);
				
				random = (random+1) * 0.5;
				
				
				
				System.out.println(random);
				
				int c = (int) (255 * random);
				
				System.out.println(c);
				
				
				Color color = new Color(c, c, c);
				
				int p = color.getRGB();
				
				bufferedImage.setRGB(x, y, p);
				
			}
		}
		
		try
		{
			File f = new File("D:\\noise.jpg");
			ImageIO.write(bufferedImage, "jpg", f);
		}
		catch(Exception e)
		{
			System.out.println("FAILED TO WRITE THE FILE");
		}
		
		
		
	}
	

}
