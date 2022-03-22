import processing.core.*;

public class ElementComponent {
    public static final int RECT_SIZE = 80, CORNER_RADIUS = 20;

    private PApplet sketch;
    private Element element;
    private float x, y;

    public ElementComponent(PApplet sketch, Element element, float x, float y) {
	this.sketch = sketch;
	this.element = element;
	this.x = x;
	this.y = y;
    }

    public void render() {
	sketch.fill(element.series.color[0], element.series.color[1], element.series.color[2]);
	sketch.stroke(240);

	sketch.strokeWeight(2);
	sketch.rect(x, y, RECT_SIZE, RECT_SIZE, CORNER_RADIUS);
	
	if (inBounds()) {
	sketch.fill(255, 0, 0);
	} else {
	    sketch.fill(0);
	}
	sketch.textFont(PeriodicTable.fontNumber);
	sketch.text(element.getAtomicNumber(), x + 10, y + 30);

	sketch.textFont(PeriodicTable.fontSymbol);
	sketch.text(element.getSymbol(), x + 10, y + 60);
    }
    
    public Element getElement() {
	return element;
    }

    public boolean inBounds() {
	return (sketch.mouseX > x) && (sketch.mouseY > y) && (sketch.mouseX < x + RECT_SIZE)
		&& (sketch.mouseY < y + RECT_SIZE);
    }
}