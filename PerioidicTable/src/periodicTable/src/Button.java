package periodicTable.src;
import processing.core.*;

public class Button {
    private PApplet sketch;
    private float x, y, width, height;
    public String text;

    public Button(PApplet sketch, float x, float y, String text) {
	this.sketch = sketch;
	this.x = x;
	this.y = y;
	this.text = text;
    }

    public void render() {
	sketch.textFont(PeriodicTable.fontButton);
	width = sketch.textWidth(text) + 20;
	height = 40;

	if (inBounds()) {
	    sketch.fill(255, 0, 0);
	} else {
	    sketch.fill(0);
	}

	sketch.stroke(240);
	sketch.strokeWeight(2);
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