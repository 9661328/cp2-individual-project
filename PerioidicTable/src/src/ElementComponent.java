package src;

import processing.core.*;

public class ElementComponent {
    public static final int RECT_SIZE = 80, CORNER_RADIUS = 20;

    private PApplet sketch;
    private Element element;
    private float x, y;
    private int r, g, b;

    public ElementComponent(PApplet sketch, Element element, float x, float y) {
	this.sketch = sketch;
	this.element = element;
	this.x = x;
	this.y = y;
	r = element.getSeries().color[0];
	g = element.getSeries().color[1];
	b = element.getSeries().color[2];
    }

    public void render() {
	sketch.fill(r, g, b);
	sketch.stroke(240);
	sketch.strokeWeight(2);
	sketch.rect(x, y, RECT_SIZE, RECT_SIZE, CORNER_RADIUS);

	if (inBounds()) {
	    sketch.fill(255, 0, 0);
	} else {
	    if (element.getSeries().isMetal) {
		sketch.fill(0);
	    } else {
		sketch.fill(255);
	    }
	}
	sketch.textAlign(PApplet.LEFT, PApplet.BASELINE);
	sketch.textFont(PeriodicTable.fontNumber);
	sketch.text(element.getAtomicNumber(), x + 10, y + 30);
	sketch.textFont(PeriodicTable.fontSymbol);
	sketch.text(element.getSymbol(), x + 10, y + 60);
    }
    
    public Element getElement() {
	return element;
    }
    
    private void changeColor(int r, int g, int b) {
	this.r = r;
	this.g = g;
	this.b = b;
    }
    
    public void greyOut() {
	changeColor(200, 200, 200);
    }
    
    public void toOriginalColor() {
	Series s = element.getSeries();
	
	changeColor(s.color[0], s.color[1], s.color[2]);
    }



    public boolean inBounds() {
	return (sketch.mouseX > x) && (sketch.mouseY > y) && (sketch.mouseX < x + RECT_SIZE)
		&& (sketch.mouseY < y + RECT_SIZE);
    }
}