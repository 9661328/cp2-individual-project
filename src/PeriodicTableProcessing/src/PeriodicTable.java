import java.util.*;
import processing.core.*;
import processing.data.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PeriodicTable extends PApplet {
    public static PFont fontNumber, fontSymbol;
    private PImage iconImage;
    private JSONObject json;
    private JSONArray elementArray;
    private ArrayList<ElementComponent> components = new ArrayList<ElementComponent>();


    public static void main(String[] args) {
	String[] appletArgs = new String[] { "PeriodicTable" };
	
	PApplet.main(appletArgs);
    }
    
    public void settings() {
	size(ElementComponent.RECT_SIZE * 18, ElementComponent.RECT_SIZE * 10);
    }

    public void setup() {
	fontNumber = createFont("space-age.ttf", 20, true);
	fontSymbol = createFont("space-age.ttf", 32, true);

	json = loadJSONObject("periodic_table.json");
	elementArray = json.getJSONArray("elements");
	
	iconImage = loadImage("icon_image.png");
	
	surface.setTitle("Periodic Table");
	surface.setResizable(false);
	surface.setIcon(iconImage);
	
	createElementComponents();
    }

    public void draw() {
	background(255);

	for (ElementComponent component : components) {
	    component.render();
	}
    }

    public void mouseClicked() {
	for (ElementComponent e : components) {
	    if (e.inBounds()) {
		println(e.getElement().getName());

		createInfoPanel(components.get(e.getElement().getAtomicNumber() - 1));
	    }
	}
    }

    public void createElementComponents() {
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

	    components.add(new ElementComponent(this, new Element(series, name, symbol, atomicNumber, weight),
		    column * ElementComponent.RECT_SIZE, row * ElementComponent.RECT_SIZE));
	}
    }
    
    public void createInfoPanel(ElementComponent e) {
	JFrame frame = new JFrame("Info");
	frame.setSize(500, 500);
	frame.setVisible(true);
	frame.setResizable(false);
	
	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), e.getElement().getName()));
	
	JTextArea textArea = new JTextArea(30, 40);
	textArea.setEditable(false);
	textArea.setLineWrap(true);
	textArea.setWrapStyleWord(true);
	
	JSONObject j = elementArray.getJSONObject(e.getElement().getAtomicNumber() - 1);
	
	Set<String> elementKeys = j.keys();
	LinkedHashMap<String, Object> elementInfo = new LinkedHashMap<String, Object>();
	
	for (String elementKey: elementKeys) {
	    elementInfo.put(elementKey, j.get(elementKey));
	}
	

	
	textArea.setText(elementInfo.toString());
	
	JScrollPane scrollPane = new JScrollPane(textArea);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	panel.add(scrollPane);
	
	frame.add(panel);
	frame.pack();
    }
}