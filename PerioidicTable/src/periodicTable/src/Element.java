package periodicTable.src;

import java.util.*;

public class Element {
    private Series series;
    private String name, symbol;
    private int atomicNumber;

    public Element(Series series, String name, String symbol, int atomicNumber) {
	this.series = series;
	this.name = name;
	this.symbol = symbol;
	this.atomicNumber = atomicNumber;
    }

    public Series getSeries() {
	return series;
    }

    public String getName() {
	return name;
    }

    public String getSymbol() {
	return symbol;
    }
    
    public int getAtomicNumber() {
	return atomicNumber;
    }

    public static String[][] alphabeticSort(ArrayList<LinkedHashMap<String, Object>> m) {
	String[][] result = new String[m.size()][2];

	for (int i = 0; i < m.size(); i++) {
	    LinkedHashMap<String, Object> element = m.get(i);
	    
	    result[i][0] = element.get("name").toString();
	    result[i][1] = "";
	}

	Arrays.sort(result, (a, b) -> a[0].compareTo(b[0]));
	return result;
    }

    public static String[][] numericSort(ArrayList<LinkedHashMap<String, Object>> m, String sort) {
	String[][] result = new String[m.size()][2];

	for (int i = 0; i < m.size(); i++) {
	    LinkedHashMap<String, Object> element = m.get(i);

	    if (!element.get(sort).toString().equals("null")) {
		result[i][0] = element.get("name").toString();
		result[i][1] = String.valueOf(Double.parseDouble(element.get(sort).toString()));
	    } else {
		result[i][0] = element.get("name").toString();
		result[i][1] = String.valueOf(Double.NaN);
	    }
	}

	Arrays.sort(result, (a, b) -> Double.compare(Double.parseDouble(a[1]), Double.parseDouble(b[1])));
	return result;
    }
}