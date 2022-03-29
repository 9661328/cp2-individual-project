import java.util.*;

public class Element {
    public Series series;
    private String name, symbol;
    private int atomicNumber;
    private double weight;

    /**
     * @param series
     * @param name
     * @param symbol
     * @param atomicNumber
     * @param weight
     */
    public Element(Series series, String name, String symbol, int atomicNumber, double weight) {
	this.series = series;
	this.name = name;
	this.symbol = symbol;
	this.atomicNumber = atomicNumber;
	this.weight = weight;

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

    public double getWeight() {
	return weight;
    }

    public static <E> TreeMap<Object, Object> sort(ArrayList<LinkedHashMap<String, Object>> m, String sort) {
	TreeMap<Object, Object> result = new TreeMap<Object, Object>();

	// first: thing being compared, second: name

	switch (sort) {
	case "Melt":
	    for (int i = 0; i < m.size(); i++) {
		LinkedHashMap<String, Object> element = m.get(i);

		if (!element.get("melt").toString().equals("null")) {
		    result.put(Double.parseDouble(element.get("melt").toString()), element.get("name"));
		} else {
		    result.put(Double.NaN, element.get("name"));
		}
	    }
	    break;
	case "Boil":
	    for (int i = 0; i < m.size(); i++) {
		LinkedHashMap<String, Object> element = m.get(i);

		if (!element.get("boil").toString().equals("null")) {
		    result.put(Double.parseDouble(element.get("boil").toString()), element.get("name"));
		} else {
		    result.put(Double.NaN, element.get("name"));
		}
	    }
	    break;
	case "Alphabetically":
	    for (int i = 0; i < m.size(); i++) {
		LinkedHashMap<String, Object> element = m.get(i);

		result.put(element.get("name").toString(), null);
	    }
	    break;
	}

	return result;
    }
}