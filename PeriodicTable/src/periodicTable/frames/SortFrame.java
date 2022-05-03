package periodicTable.frames;

import periodicTable.source.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;

public final class SortFrame extends JFrame implements WindowListener {
    private static final long serialVersionUID = 1L;
    public static final String[] COLUMN_NAMES = { "Name", "Value" };
    public static final String[] SORT_OPTIONS = { "Name", "Number", "Atomic mass", "Melt", "Boil", "Density",
	    "Molar heat" };

    private ArrayList<LinkedHashMap<String, Object>> elements;

    private JTable table;
    private JScrollPane scrollPane;
    private JComboBox<String> optionMenu;
    private JButton enterButton;

    /**
     * Constructs a frame for sorting the elements on the periodic table.
     * 
     * @param elements information for all the elements
     */
    public SortFrame(ArrayList<LinkedHashMap<String, Object>> elements) {
	this.elements = elements;

	table = new JTable(Element.alphabeticSort(elements), SORT_OPTIONS);
	scrollPane = new JScrollPane(table);
	optionMenu = new JComboBox<>(SORT_OPTIONS);
	enterButton = new JButton("Enter");

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
		case "Melt":
		    table.setModel(new DefaultTableModel(Element.numericSort(elements, "melt"), COLUMN_NAMES));
		    break;
		case "Boil":
		    table.setModel(new DefaultTableModel(Element.numericSort(elements, "boil"), COLUMN_NAMES));
		    break;
		case "Density":
		    table.setModel(new DefaultTableModel(Element.numericSort(elements, "density"), COLUMN_NAMES));
		    break;
		case "Molar heat":
		    table.setModel(new DefaultTableModel(Element.numericSort(elements, "molar_heat"), COLUMN_NAMES));
		    break;
		case "Name":
		    table.setModel(new DefaultTableModel(Element.alphabeticSort(elements), SORT_OPTIONS));
		    break;
		default:
		    System.out.println("Table error");
		}
	    }
	});
    }

    private void configureFrame() {
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