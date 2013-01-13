package com.tactfactory.mda.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.DimensionConstrain;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

public class ImageUtils {
	
	public static void resize(File imageSrc, File imageDst, float fraction) throws IOException {
		if (!imageDst.exists()) {
			ResampleOp  resampleOp = new ResampleOp(DimensionConstrain.createRelativeDimension(fraction));
			resampleOp.setFilter(ResampleFilters.getBiCubicHighFreqResponse());
			resampleOp.setNumberOfThreads(8);	// By default 8
			
			BufferedImage image = ImageIO.read(imageSrc);
			BufferedImage imageResize = resampleOp.filter(image, null);
			
			ImageIO.write(imageResize, FileUtils.getExtension(imageDst), imageDst);
		}
	}

}
