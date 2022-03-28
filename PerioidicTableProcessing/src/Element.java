import java.util.ArrayList;
import java.util.Collection;

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

    public static <E> ArrayList<Element> sort(Collection<E> c, String sort) {
	ArrayList<Element> result = new ArrayList<Element>();

	switch (sort) {
	case "boil":
	    System.out.println("sort by boiling point");
	    
	    
	    
	    
	    break;
	case "melt":
	    System.out.println("sort by melting point:");
	    break;
	}
	
	return result;
    }
}