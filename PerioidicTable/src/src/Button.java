package src;

import java.util.*;

import processing.core.*;

public class Button {
    private PApplet sketch;
    private ArrayList<ElementComponent> components;
    private float x, y, width, height;
    private String text;
    private Series s;

    public Button(PApplet sketch, ArrayList<ElementComponent> components, float x, float y, Series s) {
	this.sketch = sketch;
	this.components = components;
	this.x = x;
	this.y = y;
	this.s = s;
	this.text = s.name();
    }

    public void render() {
	sketch.textFont(PeriodicTable.fontButton);
	width = sketch.textWidth(text) + 20;
	height = sketch.textAscent() + sketch.textDescent() + 20;

	if (inBounds()) {
	    sketch.fill(s.color[0], s.color[1], s.color[2]);
	    sketch.stroke(150);
	    sketch.strokeWeight(2);

	    for (ElementComponent c : components) {
		if (c.getElement().getSeries() != s) {
		    c.greyOut();
		}
		
		if (c.getElement().getSeries() == s) {
		    c.toOriginalColor();
		}
	    }
	} else {
	    sketch.fill(s.color[0], s.color[1], s.color[2], 150);
	    sketch.stroke(240);
	    sketch.strokeWeight(2);
	}
	
	
	sketch.rect(x, y, width, height, 10);
	sketch.fill(255);
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