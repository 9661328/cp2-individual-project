package periodicTable.frames;

import periodicTable.source.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ElementInfoFrame extends JFrame implements WindowListener {
    private static final long serialVersionUID = 1L;

    ArrayList<LinkedHashMap<String, Object>> elements;
    ArrayList<ElementComponent> components;

    JPanel panel;
    JTable textArea;
    JScrollPane scrollPane;
    JButton url;

    public ElementInfoFrame(ArrayList<LinkedHashMap<String, Object>> elements, ArrayList<ElementComponent> components,
	    ElementComponent e) {

	this.elements = elements;
	this.components = components;

	setLookAndFeel();
	createComponents(e);

	setTitle("Information");
	setLayout(new BorderLayout());
	setResizable(false);
	addWindowListener(this);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public void setLookAndFeel() {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
		| UnsupportedLookAndFeelException e) {
	    e.printStackTrace();
	}
    }

    public void createComponents(ElementComponent e) {
	panel = new JPanel();
	//textArea = new JTextArea(20, 50);


	panel.setBorder(new TitledBorder(e.getElement().getName()));

	
//	textArea.setEditable(false);
//	textArea.setLineWrap(true);
//	textArea.setWrapStyleWord(true);
	

	StringBuilder text = new StringBuilder("");
	LinkedHashMap<String, Object> elementInfo = elements.get(e.getElement().getAtomicNumber() - 1);

	String[] keys = elementInfo.keySet().toArray(new String[0]);
	String[][] tableData = new String[keys.length][keys.length];
	


	for (int i = 0; i < keys.length; i++) {
	    String keyDisplayed = keys[i].toString().replaceAll("_", " ").toUpperCase();

	    //text.append(keyDisplayed + ": " + elementInfo.get(keys[i]) + "\n\n");
	    
	    tableData[i][0] = keyDisplayed;
	    tableData[i][1] = elementInfo.get(keys[i]).toString();
	}
	
	textArea = new JTable(tableData, new String[] {"e", "e"});
	textArea.setRowHeight(20);

	scrollPane = new JScrollPane(textArea);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	panel.add(scrollPane, BorderLayout.CENTER);

	//textArea.setText(text.toString().trim());

	url = new JButton((String) elementInfo.get("source"));
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