import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class Util {

	// Upscale Variables
	private int scaleAmount;
	private int denoiseAmount;
	private String imagePath;

	public static BufferedImage loadImage(String fileName) {
		BufferedImage bufImg = null;
		try {
			bufImg = ImageIO.read(new File(fileName));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return bufImg;
	}


	// SETTERS

	// Set Scale Amount
	public void setScale(int scale){
		this.scaleAmount = scale;
	}

	// Set De-Noise Amount
	public void setDeNoise(int denoise){
		this.denoiseAmount = denoise;
	}

	// Set the path to the image
	public void setImagePath(String path) {
		this.imagePath = path;
		System.out.println(imagePath);
	}

	// GETTERS

	public String getFlags() {
		// -m noise -m scale -m noise_scale
		// --noise_level #
		// --scale_ratio #

		/*
		" -i " + imageInputPath +
		" -o " + imageOutputPath +
		*/

		List<String> results = new ArrayList<>();

		boolean doNoise = false;
		boolean doScale = false;

		switch(denoiseAmount) {
			case 1:
			case 2:
			case 3:
				results.add("--noise_level " + denoiseAmount);
				doNoise = true;
				break;
		}

		if (scaleAmount > 1){
			results.add("--scale_ratio " + scaleAmount);
			doScale = true;
		}

		String scaleFlag = "";
		if (doNoise && doScale){
			scaleFlag = "-m ";
			scaleFlag += "noise_scale";
		} else if (doNoise){
			scaleFlag = "-m ";
			scaleFlag += "noise";
		} else if (doScale){
			scaleFlag = "-m ";
			scaleFlag += "scale";
		}
		results.add(scaleFlag);

		Pattern pattern = Pattern.compile("\\.(?=\\w+$)");

		String imageInputPath = this.imagePath;
		String[] splits = pattern.split(imageInputPath);

		String imageOutputPath = splits[0] + "_out." + splits[1];

		results.add("-i " + imageInputPath);
		results.add("-o " + imageOutputPath);

		return String.join(" ", results);
	}
}
