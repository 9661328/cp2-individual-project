package frames;

import src.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;

public class SortFrame extends JFrame implements WindowListener {
    public static final String[] COLUMN_NAMES = {"Name", "Value"};
    public static final String[] SORT_OPTIONS = { "Atomic number", "Atomic mass", "Melting point", "Boiling point", "Density",
		"Molar heat", "Alphabetically by name" };
    
    public SortFrame(ArrayList<LinkedHashMap<String, Object>> elements) {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	JTable table = new JTable(Element.numericSort(elements, "number"), COLUMN_NAMES);
	JScrollPane scrollPane = new JScrollPane(table);
	JComboBox<String> optionMenu = new JComboBox<>(SORT_OPTIONS);
	JButton enterButton = new JButton("Enter");
	
	enterButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		String selectedSort = optionMenu.getSelectedItem().toString();
		switch (selectedSort) {
		case "Atomic number":
		    table.setModel(new DefaultTableModel(Element.numericSort(elements, "number"), COLUMN_NAMES));
		    break;
		case "Atomic mass":
		    table.setModel(new DefaultTableModel(Element.numericSort(elements, "atomic_mass"), COLUMN_NAMES));
		    break;
		case "Melting point":
		    table.setModel(new DefaultTableModel(Element.numericSort(elements, "melt"), COLUMN_NAMES));
		    break;
		case "Boiling point":
		    table.setModel(new DefaultTableModel(Element.numericSort(elements, "boil"), COLUMN_NAMES));
		    break;
		case "Density": 
		    table.setModel(new DefaultTableModel(Element.numericSort(elements, "density"), COLUMN_NAMES));
		    break;
		case "Molar heat":
		    table.setModel(new DefaultTableModel(Element.numericSort(elements, "molar_heat"), COLUMN_NAMES));
		    break;
		case "Alphabetically by name":
		    table.setModel(new DefaultTableModel(Element.alphabeticSort(elements), COLUMN_NAMES));
		    break;
		default:
		    System.out.println("Table error");
		}
	    }
	});
	
	setTitle("Sorter");
	setSize(500, 500);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	addWindowListener(this);
	setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	add(optionMenu);
	add(enterButton);
	add(scrollPane);
    }
    
    @Override
    public void windowDeactivated(WindowEvent e) {
	e.getWindow().dispose();
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void windowClosed(WindowEvent e) {
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
}