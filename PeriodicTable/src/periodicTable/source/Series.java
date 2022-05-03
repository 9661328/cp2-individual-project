package periodicTable.source;

public enum Series {
    ALKALI_METAL(true, new int[] {234, 206, 93}), 
    ALKALINE_EARTH_METAL(true, new int[] {241, 241, 101}), 
    LANTHANOID(true, new int[] {232, 209, 156}),
    ACTINOID(true, new int[] {245, 204, 218}), 
    TRANSITION_METAL(true, new int[] {250, 197, 183}),
    POST_TRANSITION_METAL(true, new int[] {172, 223, 236}), 
    METALLOID(false, new int[] {171, 232, 218}), 
    REACTIVE_NONMETAL(false, new int[] {155, 239, 155}),
    NOBLE_GAS(false, new int[] {232, 198, 232}),
    unknown(false, new int[] {230, 230, 230});

    public final boolean isMetal;
    public final int[] color;
    
    /**
     * @param isMetal whether or not the element is a metal
     * @param color an integer array for the RGB values of the color to be displayed
     */
    private Series(boolean isMetal, int[] color) {
	this.isMetal = isMetal;
	this.color = color;
    }
}