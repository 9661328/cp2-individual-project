package frames;

import src.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ElementInfoFrame extends JFrame implements WindowListener {
    ArrayList<ElementComponent> components;

    public ElementInfoFrame(ArrayList<LinkedHashMap<String, Object>> elements, ArrayList<ElementComponent> components,
	    ElementComponent e) {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	this.components = components;

	JPanel panel = new JPanel();
	JTextArea textArea = new JTextArea(20, 50);
	JScrollPane scrollPane = new JScrollPane(textArea);

	panel.setBorder(new TitledBorder(e.getElement().getName()));
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	textArea.setEditable(false);
	textArea.setLineWrap(true);
	textArea.setWrapStyleWord(true);

	StringBuilder text = new StringBuilder("");
	LinkedHashMap<String, Object> elementInfo = elements.get(e.getElement().getAtomicNumber() - 1);

	for (String elementKey : elementInfo.keySet()) {
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

	setTitle("Information");
	setLayout(new BorderLayout());
	setResizable(false);
	addWindowListener(this);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	panel.add(scrollPane);
	add(panel, BorderLayout.NORTH);
	add(url, BorderLayout.SOUTH);
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
    public void windowOpened(WindowEvent e) {}
    
    @Override
    public void windowClosing(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}
}