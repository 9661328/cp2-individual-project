public class Element {
    public Series series;
    private String name, symbol;
    private int atomicNumber;
    private double weight;

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
}