package src;

import java.util.*;

import processing.core.*;

public class Button {
	private PApplet sketch;
	private float x, y, width, height;
	private String text;
	private int[] color;

	private ArrayList<ElementComponent> components;
	private Series series;
	
	public Button(PApplet sketch, String text, float x, float y) {
		this.sketch = sketch;
		this.text = text;
		this.x = x;
		this.y = y;

		this.color = new int[] { 0, 0, 0 };
		this.series = null;
		this.components = null;
	}

	public Button(PApplet sketch, ArrayList<ElementComponent> components, float x, float y, Series series) {
		this.sketch = sketch;
		this.components = components;
		this.x = x;
		this.y = y;
		this.series = series;

		this.text = series.name().replaceAll("_", " ");
		this.color = series.color;
	}

	public void render() {
		sketch.textFont(PeriodicTable.fontButton);
		width = sketch.textWidth(text) + 20;
		height = sketch.textAscent() + sketch.textDescent() + 20;

		if (inBounds()) {
			sketch.fill(color[0], color[1], color[2]);
			sketch.stroke(150);
			sketch.strokeWeight(2);
			if (components != null) {
				for (ElementComponent c : components) {
					if (c.getElement().getSeries() != series) {
						c.greyOut();
					}

					if (c.getElement().getSeries() == series) {
						c.toOriginalColor();
					}
				}
			}
		} else {
			sketch.fill(color[0], color[1], color[2], 200);
			sketch.stroke(240);
			sketch.strokeWeight(2);
		}
		
		sketch.rect(x, y, width, height, 10);
		sketch.fill(150);
		sketch.textAlign(PApplet.LEFT, PApplet.TOP);
		sketch.text(text, x + 10, y + 10);
	}

	public PApplet getSketch() {
		return sketch;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public String getText() {
		return text;
	}

	public boolean inBounds() {
		return (sketch.mouseX > x) && (sketch.mouseY > y) && (sketch.mouseX < x + width)
				&& (sketch.mouseY < y + height);
	}
}