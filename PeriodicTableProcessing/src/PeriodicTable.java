import processing.core.*;
import processing.data.*;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class PeriodicTable extends PApplet implements WindowListener {
    public static PFont fontNumber, fontSymbol;
    private PImage iconImage;
    private JSONArray elementArray;
    private ArrayList<ElementComponent> components;
    private boolean infoPanelOpen;

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
	iconImage = loadImage("icon_image.png");
	elementArray = loadJSONObject("periodic_table.json").getJSONArray("elements");
	components = new ArrayList<ElementComponent>();
	infoPanelOpen = false;

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
		if (!infoPanelOpen) {
		    createInfoPanel(components.get(e.getElement().getAtomicNumber() - 1));
		}
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
	frame.setLayout(new BorderLayout());
	frame.setResizable(false);
	frame.addWindowListener(this);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	JPanel panel = new JPanel();
	panel.setSize(30, 40);
	panel.setBorder(new TitledBorder(e.getElement().getName()));

	JTextArea textArea = new JTextArea(panel.getWidth(), panel.getHeight());
	textArea.setEditable(false);
	textArea.setLineWrap(true);
	textArea.setWrapStyleWord(true);

	JSONObject j = elementArray.getJSONObject(e.getElement().getAtomicNumber() - 1);

	String[] elementKeys = { "summary", "symbol", "appearance", "category", "atomic_mass", "boil", "melt",
		"density", "discovered_by", "named_by", "number", "period", "phase", "spectral_img", "xpos", "ypos",
		"molar_heat", "electron_configuration", "electron_configuration_semantic", "electron_affinity",
		"electronegativity_pauling", "shells", "ionization_energies", "cpk-hex" };

	LinkedHashMap<String, Object> elementInfo = new LinkedHashMap<String, Object>();
	Set<String> elementInfoKeys = elementInfo.keySet();

	for (String elementKey : elementKeys) {
	    elementInfo.put(elementKey, j.get(elementKey));
	}

	StringBuilder text = new StringBuilder("");

	for (String key : elementInfoKeys) {
	    String keyDisplayed = key.replaceAll("_", " ").toUpperCase();

	    text.append(keyDisplayed + ": " + elementInfo.get(key) + "\n\n");
	}

	textArea.setText(text.toString().trim());

	JLabel url = new JLabel((String) elementInfo.get("source"));
	url.setForeground(Color.BLUE.darker());
	url.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	url.addMouseListener(new MouseAdapter() {
	    public void mouseEntered(MouseEvent me) {
		url.setForeground(Color.CYAN.darker());
	    }

	    public void mouseExited(MouseEvent me) {
		url.setForeground(Color.BLUE.darker());
	    }

	    public void mouseClicked(MouseEvent me) {
		try {
		    Desktop.getDesktop().browse(new URI(url.getText()));
		} catch (URISyntaxException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	});

	JScrollPane scrollPane = new JScrollPane(textArea);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	scrollPane.setViewportView(textArea);
	panel.add(scrollPane);

	frame.add(panel, BorderLayout.NORTH);
	frame.add(url, BorderLayout.SOUTH);
	frame.pack();
	frame.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
	infoPanelOpen = true;
    }

    @Override
    public void windowClosed(WindowEvent e) {
	infoPanelOpen = false;
    }

    @Override
    public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
    }

    @Override
    public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
    }

    @Override
    public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
    }
}