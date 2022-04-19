package src;

import frames.*;
import processing.core.*;
import processing.data.*;
import java.util.*;

public class PeriodicTable extends PApplet {
	public static PFont fontNumber, fontSymbol, fontButton;
	private PImage iconImage;
	private JSONArray elementArray;
	public ArrayList<ElementComponent> components;
	private ArrayList<Button> buttons;
	private ArrayList<LinkedHashMap<String, Object>> elements;

	public static void main(String[] args) {
		String[] appletArgs = new String[] { "src.PeriodicTable" };

		PApplet.main(appletArgs);
	}

	public void settings() {
		size(ElementComponent.RECT_SIZE * 18, ElementComponent.RECT_SIZE * 10);
	}

	public void setup() {
		fontNumber = createFont("space-age.ttf", 20, true);
		fontSymbol = createFont("space-age.ttf", 32, true);
		fontButton = createFont("uni-sans-heavy.otf", 10, true);
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

		Random random = new Random();
		System.out.println(components.get(random.nextInt(components.size())).getElement().getName());

		for (ElementComponent e : components) {
			System.out.println(e.getElement().getName().length());
		}

	}

	public void draw() {
		background(255);

		for (ElementComponent component : components) {
			component.render();
		}

		for (Button button : buttons) {
			button.render();
		}

		if ((!buttons.get(0).inBounds()) && (!buttons.get(1).inBounds()) && (!buttons.get(2).inBounds())
				&& (!buttons.get(3).inBounds()) && (!buttons.get(4).inBounds()) && (!buttons.get(5).inBounds())
				&& (!buttons.get(6).inBounds()) && (!buttons.get(7).inBounds())
				&& (!buttons.get(8).inBounds() && (!buttons.get(9).inBounds()))) {
			for (ElementComponent e : components) {
				e.toOriginalColor();
			}
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
		float buttonHeight = textAscent() + textDescent() + 20;
		float[] panel = { 500, 80 };

		Button alkaliMetal = new Button(this, components, panel[0], panel[1], Series.ALKALI_METAL);
		Button alkaliEarthMetal = new Button(this, components, panel[0], panel[1] + buttonHeight,
				Series.ALKALINE_EARTH_METAL);
		Button lanthanoid = new Button(this, components, panel[0], panel[1] + (2 * buttonHeight), Series.LANTHANOID);
		Button actinoid = new Button(this, components, panel[0], panel[1] + (3 * buttonHeight), Series.ACTINOID);
		Button transitionMetal = new Button(this, components, panel[0] + 150, panel[1], Series.TRANSITION_METAL);
		Button postTransitionMetal = new Button(this, components, panel[0] + 150, panel[1] + buttonHeight,
				Series.POST_TRANSITION_METAL);
		Button metalloid = new Button(this, components, panel[0] + 150, panel[1] + (2 * buttonHeight),
				Series.METALLOID);
		Button unknown = new Button(this, components, panel[0] + 150, panel[1] + (3 * buttonHeight), Series.unknown);
		Button reactiveNonmetal = new Button(this, components, panel[0] + 300, panel[1], Series.REACTIVE_NONMETAL);
		Button nobleGas = new Button(this, components, panel[0] + 300, panel[1] + buttonHeight, Series.NOBLE_GAS);

		Button sort = new Button(this, "Sort", 300, panel[1]);

		buttons.add(alkaliMetal);
		buttons.add(alkaliEarthMetal);
		buttons.add(lanthanoid);
		buttons.add(actinoid);
		buttons.add(transitionMetal);
		buttons.add(postTransitionMetal);
		buttons.add(metalloid);
		buttons.add(unknown);
		buttons.add(reactiveNonmetal);
		buttons.add(nobleGas);

		buttons.add(sort);
	}

	private void createInfoPanel(ElementComponent e) {
		new ElementInfoFrame(elements, components, e).setVisible(true);
	}

	private void createSortFrame() {
		new SortFrame(elements).setVisible(true);
	}
}