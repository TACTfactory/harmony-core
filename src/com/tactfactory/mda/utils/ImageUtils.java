/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.DimensionConstrain;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * Images utility class.
 * @author gregg
 *
 */
public abstract class ImageUtils {
	// Thread numbers
	private static final int NB_THREAD = 8;
	
	/**
	 * Resize a image given a ratio.
	 * @param imageSrc Source image
	 * @param imageDst Destination file
	 * @param fraction Ratio for image resizing
	 * @throws IOException
	 */
	public static void resize(
			final File imageSrc,
			final File imageDst,
			final float fraction) 
					throws IOException {
		
		if (!imageDst.exists()) {
			final ResampleOp resampleOp = new ResampleOp(
						DimensionConstrain.createRelativeDimension(fraction));
			
			resampleOp.setFilter(
					ResampleFilters.getBiCubicHighFreqResponse());
			resampleOp.setNumberOfThreads(NB_THREAD);
			
			final BufferedImage image = ImageIO.read(imageSrc);
			final BufferedImage imageResize = 
					resampleOp.filter(image, null);
			
			ImageIO.write(
					imageResize, 
					FileUtils.getExtension(imageDst),
					imageDst);
		}
	}

}
