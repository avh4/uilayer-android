package net.avh4.framework.uilayer.scene;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

class SwingImage {

	private final int x;
	private final int y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private final int width;
	private final int height;
	private final BufferedImage image;
	private static final HashMap<String, BufferedImage> cache = new HashMap<String, BufferedImage>();

	/**
	 * @param imageName
	 *            The image specified must exist on the classpath in the default
	 *            package.
	 */
	public SwingImage(final int x, final int y, final int width,
			final int height, final String imageName) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		image = loadImage(imageName);
	}

	private BufferedImage loadImage(final String imageName) {
		if (cache.containsKey(imageName)) {
			return cache.get(imageName);
		}

		final URL resource = ClassLoader.getSystemResource(imageName);
		if (resource == null) {
			throw new IllegalArgumentException(String.format(
					"No such resource '%s'", imageName));
		}
		try {
			final BufferedImage image = ImageIO.read(resource);
			cache.put(imageName, image);
			return image;
		} catch (final IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(String.format(
					"Could not read image '%s'", imageName), e);
		}
	}

	public java.awt.Image getImage() {
		return image;
	}

}
