package com.Roj;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Main {
	
	private static final String FILE_PATH =  "D:\\perlinnoise1.jpg";
	private static final int IMG_X = 1920;
	private static final int IMG_Y = 1080;
	
	public static void main(String[] args) {
		
		PerlinNoise perlinNoise = new PerlinNoise("256x_asdaswhtklfdjkaxnsjssjgsdfuofvbxceowlsalcpfogjrnwucjkdfzhkjj");
		
		BufferedImage bufferedImage = new BufferedImage(IMG_X, IMG_Y, BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0; x < IMG_X; ++x)
		{
			for(int y = 0; y < IMG_Y; ++y)
			{
				
				double random = perlinNoise.noise(x * 0.01, y * 0.01);
				
				random = (random+1) * 0.5;
				
				int c = (int) (255 * random);
				
				Color color = new Color(c, c, c);
				
				int p = color.getRGB();
				
				bufferedImage.setRGB(x, y, p);
				
			}
			
			if(((x+1) % (IMG_X/20)) != 0)
			{
				continue;
			}
			
			float percentage = ((x + 1f) / IMG_X * 100);
			System.out.println(percentage + "% complete.");
		}
		
		try
		{
			File f = new File(FILE_PATH);
			ImageIO.write(bufferedImage, "jpg", f);
			System.out.println("Image saved at: " + FILE_PATH);
		}
		catch(Exception e)
		{
			System.out.println("FAILED TO WRITE THE FILE");
		}
		
		
		
	}
	

}
