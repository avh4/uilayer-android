package net.avh4.framework.uilayer.scene;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

public class Scene implements Iterable<SwingSceneObject> {

	private final ArrayList<SwingSceneObject> children = new ArrayList<SwingSceneObject>();
	private final String title;

	public Scene(final String title) {
		this.title = title;
	}

	public Scene() {
		this("Untitled Scene");
	}

	public Dimension getBounds() {
		return new Dimension(800, 600);
	}

	public void addImage(final int x, final int y, final int width,
			final int height, final String imageName) {
		children.add(new SwingImage(x, y, width, height, imageName));
	}

	public void addImage(final int x, final int y, final int width,
			final int height, final Image image) {
		children.add(new SwingImage(x, y, width, height, image));
	}

	@Override
	public Iterator<SwingSceneObject> iterator() {
		return children.iterator();
	}

	public String getTitle() {
		return title;
	}

	public void addPlaceholder(final String name, final int x, final int y,
			final int width, final int height) {
		children.add(new SwingPlaceholder(name, x, y, width, height));
	}

	public void addText(final String text, final int x, final int y,
			final int width, final String customFontResource, final int fontSize) {
		children.add(new SwingText(text, x, y, width, customFontResource,
				fontSize));
	}

}
