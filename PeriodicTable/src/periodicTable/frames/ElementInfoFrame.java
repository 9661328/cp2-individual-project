package periodicTable.frames;

import periodicTable.source.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;
import java.net.*;

public final class ElementInfoFrame extends JFrame implements WindowListener {
    private static final long serialVersionUID = 1L;

    private ArrayList<ElementComponent> components;
    private LinkedHashMap<String, Object> elementInfo;
    private ElementComponent element;

    private JPanel panel;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton urlButton;
    
    /**
     * Constructs a frame displaying information for a specific element.
     * 
     * @param elements information for all the elementss
     * @param components an ArrayList of the the components displayed on the GUI
     * @param element the specific element to create the frame for
     * */
    public ElementInfoFrame(ArrayList<LinkedHashMap<String, Object>> elements, ArrayList<ElementComponent> components,
	    ElementComponent element) {
	this.components = components;
	this.elementInfo = elements.get(element.getElement().getAtomicNumber() - 1);
	this.element = element;

	this.panel = new JPanel();
	this.textArea = new JTextArea(20, 30);
	this.scrollPane = new JScrollPane(textArea);
	this.urlButton = new JButton((String) elementInfo.get("source"));

	setLookAndFeel();
	createComponents();
	configureFrame();
    }

    private void setLookAndFeel() {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
		| UnsupportedLookAndFeelException e) {
	    e.printStackTrace();
	}
    }

    private void createComponents() {
	panel.setBorder(new TitledBorder(element.getElement().getName()));
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	textArea.setEditable(false);
	textArea.setLineWrap(true);
	textArea.setWrapStyleWord(true);

	StringBuilder text = new StringBuilder("");

	for (String elementKey : elementInfo.keySet()) {
	    String keyDisplayed = elementKey.replaceAll("_", " ").toUpperCase();
	    text.append(keyDisplayed + ": " + elementInfo.get(elementKey) + "\n\n");
	}

	textArea.setText(text.toString().trim());

	urlButton.setForeground(Color.BLUE.darker());
	urlButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	urlButton.addMouseListener(new MouseAdapter() {
	    public void mouseEntered(MouseEvent me) {
		Color c = elementInfo.get("cpk-hex").toString().equals("null") ? Color.CYAN.darker()
			: Color.decode("#" + elementInfo.get("cpk-hex").toString().toUpperCase());
		urlButton.setForeground(c);
	    }

	    public void mouseExited(MouseEvent me) {
		urlButton.setForeground(Color.BLUE.darker());
	    }

	    public void mouseClicked(MouseEvent me) {
		try {
		    Desktop.getDesktop().browse(new URI(urlButton.getText()));
		} catch (IOException | URISyntaxException e) {
		    e.printStackTrace();
		}
	    }
	});
    }
    
    private void configureFrame() {
	setTitle("Information");
	setLayout(new BorderLayout());
	setResizable(false);
	addWindowListener(this);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	panel.add(scrollPane);
	add(panel, BorderLayout.NORTH);
	add(urlButton, BorderLayout.SOUTH);
	pack();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
	e.getWindow().dispose();

	for (ElementComponent c : components) {
	    c.toOriginalColor();
	}
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
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