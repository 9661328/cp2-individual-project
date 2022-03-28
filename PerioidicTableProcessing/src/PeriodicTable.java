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
    public static PFont fontNumber, fontSymbol, fontButton;
    private PImage iconImage;
    private JSONArray elementArray;
    private ArrayList<ElementComponent> components;
    private ArrayList<Button> buttons;
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
	fontButton = createFont("uni-sans-heavy.otf", 20, true);
	iconImage = loadImage("icon_image.png");
	elementArray = loadJSONObject("periodic_table.json").getJSONArray("elements");
	components = new ArrayList<ElementComponent>();
	buttons = new ArrayList<Button>();
	infoPanelOpen = false;

	surface.setTitle("Periodic Table");
	surface.setResizable(false);
	surface.setIcon(iconImage);

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
	System.out.println(mouseX + ", " + mouseY);
	
	for (Button b: buttons) {
	    if (b.getText().equals("Sort") && b.inBounds()) {
		if (!infoPanelOpen) {
		    JFrame f = new JFrame("Sorter");
		    f.setSize(500, 500);
		    f.addWindowListener(this);
		    f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));
		    
		    
		    String[] sortOptions = {"Atomic number", "Atomic mass", "Melting point", "Boiling point", "Density", "Molar heat", "Alphabetically by name"};
		    JComboBox<String> j = new JComboBox<>(sortOptions);
		    JButton enter = new JButton("Enter");
		    enter.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		        	System.out.println(j.getItemAt(j.getSelectedIndex()));
		            }
		        });
		    
		    JTextArea textArea = new JTextArea(30, 40);
		    textArea.setText("aoisjelkfjalsdjf");
		    JScrollPane scrollPane = new JScrollPane(textArea);
		    
		    
		    f.add(j);
		    f.add(enter);
		    f.add(scrollPane);
		    f.setVisible(true);
		}
	    }
	}

	for (ElementComponent e : components) {
	    if (e.inBounds()) {
		if (!infoPanelOpen) {
		    createInfoPanel(components.get(e.getElement().getAtomicNumber() - 1));
		}

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

    /**
     * Creates element components from JSON and puts them in an ArrayList.
     */
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

	    components.add(new ElementComponent(this, new Element(series, name, symbol, atomicNumber, weight),
		    column * ElementComponent.RECT_SIZE, row * ElementComponent.RECT_SIZE));
	}
    }

    public void createButtons() {
	Button sort = new Button(this, 250, 20, "Sort");
	
	buttons.add(sort);
	
    }

    /**
     * Creates an information panel about the element clicked.
     * 
     * @param e The element to create the info panel for.
     */
    private void createInfoPanel(ElementComponent e) {
	JFrame frame = new JFrame("Info");
	frame.setLayout(new BorderLayout());
	frame.setResizable(false);
	frame.addWindowListener(this);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(e.getElement().getName()));

	JTextArea textArea = new JTextArea(30, 40);
	JScrollPane scrollPane = new JScrollPane(textArea);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	textArea.setEditable(false);
	textArea.setLineWrap(true);
	textArea.setWrapStyleWord(true);

	JSONObject j = elementArray.getJSONObject(e.getElement().getAtomicNumber() - 1);

	String[] elementKeys = { "summary", "symbol", "number", "appearance", "category", "atomic_mass", "boil", "melt",
		"density", "discovered_by", "named_by", "period", "phase", "xpos", "ypos", "molar_heat",
		"electron_configuration", "electron_configuration_semantic", "electron_affinity",
		"electronegativity_pauling", "shells", "ionization_energies", "cpk-hex", "spectral_img", "source" };

	LinkedHashMap<String, Object> elementInfo = new LinkedHashMap<String, Object>();
	StringBuilder text = new StringBuilder("");

	for (String elementKey : elementKeys) {
	    elementInfo.put(elementKey, j.get(elementKey));

	    String keyDisplayed = elementKey.replaceAll("_", " ").toUpperCase();
	    text.append(keyDisplayed + ": " + elementInfo.get(elementKey) + "\n\n");
	}

	textArea.setText(text.toString().trim());

	JButton url = new JButton((String) elementInfo.get("source"));
	url.setForeground(Color.BLUE.darker());
	url.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	url.addMouseListener(new MouseAdapter() {
	    public void mouseEntered(MouseEvent me) {
		Color c = elementInfo.get("cpk-hex").toString().equals("null") ? Color.CYAN.darker()
			: Color.decode("#" + elementInfo.get("cpk-hex").toString().toUpperCase());
		url.setForeground(c);
	    }

	    public void mouseExited(MouseEvent me) {
		url.setForeground(Color.BLUE.darker());
	    }

	    public void mouseClicked(MouseEvent me) {
		try {
		    Desktop.getDesktop().browse(new URI(url.getText()));
		} catch (IOException | URISyntaxException e) {
		    e.printStackTrace();
		}
	    }
	});

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

	for (ElementComponent c : components) {
	    Series s = c.getElement().getSeries();
	    c.toOriginalColor();
	}
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
	e.getWindow().dispose();
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }
}