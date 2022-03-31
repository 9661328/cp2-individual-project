package periodicTable.src;

import frames.*;
import processing.core.*;
import processing.data.*;
import java.awt.event.*;
import java.util.*;

public class PeriodicTable extends PApplet {
    public static PFont fontNumber, fontSymbol, fontButton;
    private PImage iconImage;
    private JSONArray elementArray;
    private ArrayList<ElementComponent> components;
    private ArrayList<Button> buttons;
    private ArrayList<LinkedHashMap<String, Object>> elements;

    public static void main(String[] args) {
	String[] appletArgs = new String[] { "periodicTable.src.PeriodicTable" };
	PApplet.main(appletArgs);
    }

    public void settings() {
	size(ElementComponent.RECT_SIZE * 18, ElementComponent.RECT_SIZE * 10);
    }

    public void setup() {
	fontNumber = createFont("space-age.ttf", 20, true);
	fontSymbol = createFont("space-age.ttf", 32, true);
	fontButton = createFont("uni-sans-heavy.otf", 20, true);
	iconImage = loadImage("icon_image.png");

	elementArray = loadJSONObject("periodic_table.json").getJSONArray("elements");
	components = new ArrayList<>();
	buttons = new ArrayList<>();
	elements = new ArrayList<>();

	surface.setTitle("Periodic Table");
	surface.setResizable(false);
	surface.setIcon(iconImage);

	populateElementInfo();
	createElementComponents();
	createButtons();
    }

    public void draw() {
	background(255);

	for (ElementComponent component : components) {
	    component.render();
	}

	for (Button button : buttons) {
	    button.render();
	}
    }

    public void mouseClicked() {
	for (Button b : buttons) {
	    if (b.getText().equals("Sort") && b.inBounds()) {
		createSortFrame();
	    }
	}

	for (ElementComponent e : components) {
	    if (e.inBounds()) {
		createInfoPanel(components.get(e.getElement().getAtomicNumber() - 1));

		for (ElementComponent c : components) {
		    if (c.getElement().getSeries() != e.getElement().getSeries()) {
			c.greyOut();
		    }
		}
	    }
	}
    }

    public void mouseMoved() {
//	for (Button b : buttons) {
//	    if (b.inBounds() && b.getText().equals("Metal")) {
//		for (ElementComponent e : components) {
//		    Series s = e.getElement().getSeries();
//
//		    if (!s.isMetal) {
//			e.greyOut();
//		    }
//		}
//	    } else if (b.inBounds() && b.getText().equals("Nonmetal")) {
//		for (ElementComponent e : components) {
//		    Series s = e.getElement().getSeries();
//
//		    if (s.isMetal) {
//			e.greyOut();
//		    }
//		}
//	    } else if (!b.inBounds() && (b.getText().equals("Metal") || b.getText().equals("Nonmetal"))) {
//		for (ElementComponent e : components) {
//		    e.toOriginalColor();
//		}
//	    }
//	}
    }

    private void populateElementInfo() {
	String[] elementKeys = { "summary", "symbol", "number", "appearance", "category", "atomic_mass", "boil", "melt",
		"density", "discovered_by", "named_by", "period", "phase", "xpos", "ypos", "molar_heat",
		"electron_configuration", "electron_configuration_semantic", "electron_affinity",
		"electronegativity_pauling", "shells", "ionization_energies", "cpk-hex", "spectral_img", "source",
		"name" };

	for (int i = 0; i < elementArray.size(); i++) {
	    JSONObject j = elementArray.getJSONObject(i);
	    LinkedHashMap<String, Object> elementInfo = new LinkedHashMap<String, Object>();

	    for (String elementKey : elementKeys) {
		elementInfo.put(elementKey, j.get(elementKey));
	    }
	    elements.add(elementInfo);
	}
    }

    private void createElementComponents() {
	for (int i = 0; i < elementArray.size(); i++) {
	    JSONObject e = elementArray.getJSONObject(i);

	    String name = e.getString("name");
	    String symbol = e.getString("symbol");
	    int atomicNumber = e.getInt("number");
	    double weight = e.getDouble("atomic_mass");

	    int row = e.getInt("period") - 1;
	    int column = e.getInt("xpos") - 1;

	    String s = e.getString("category");
	    Series series;

	    if (s.equals("alkali metal")) {
		series = Series.ALKALI_METAL;
	    } else if (s.equals("alkaline earth metal")) {
		series = Series.ALKALINE_EARTH_METAL;
	    } else if (s.equals("lanthanide")) {
		series = Series.LANTHANOID;

		row += 3;
	    } else if (s.equals("actinide")) {
		series = Series.ACTINOID;

		row += 3;
	    } else if (s.equals("transition metal")) {
		series = Series.TRANSITION_METAL;
	    } else if (s.equals("post-transition metal")) {
		series = Series.POST_TRANSITION_METAL;
	    } else if (s.equals("metalloid")) {
		series = Series.METALLOID;
	    } else if (s.equals("noble gas")) {
		series = Series.NOBLE_GAS;
	    } else if (s.contains("nonmetal")) {
		series = Series.REACTIVE_NONMETAL;
	    } else {
		series = Series.unknown;
	    }

	    components.add(new ElementComponent(this, new Element(series, name, symbol, atomicNumber),
		    column * ElementComponent.RECT_SIZE, row * ElementComponent.RECT_SIZE));
	}
    }

    private void createButtons() {
	Button sort = new Button(this, 250, 20, "Sort");

	buttons.add(sort);
    }

    private void createInfoPanel(ElementComponent e) {
	new ElementInfoFrame(elements, components, e).setVisible(true);
    }

    private void createSortFrame() {
	new SortFrame(elements).setVisible(true);
    }
}