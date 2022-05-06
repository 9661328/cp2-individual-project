# cp2-individual-project

## 📝 Description

An interactive periodic table app that displays information about elements and sorts. Also comes with quiz games.

``` 
Year two programming concepts used:

> GUI controls - Processing and some Java
> Enhanced for loop
> Ternary statement
> Overloading
> IO and Exception Handling
> Collections (ArrayList, JSONArray, Set)
```

## 📚 Documentation

### Class diagram

```mermaid
%%{init: {'theme': 'base', 'themeVariables': { 'primaryColor': '#ffcccc', 'edgeLabelBackground':'#ffffee', 'tertiaryColor': '#fff0f0'}}}%%

flowchart TB
subgraph src
subgraph periodicTable.frames
id1(SortFrame.java)
id2(ElementInfoFrame.java)
end
subgraph periodicTable.source
id3(Button.java)
id4(Element.java)
id5(ElementComponent.java)
id6(PeriodicTable.java)
id7(Series.java)
end
end
```

```mermaid
%%{init: {'theme': 'base', 'themeVariables': { 'primaryColor': '#ffcccc', 'edgeLabelBackground':'#ffffee', 'tertiaryColor': '#fff0f0'}}}%%

classDiagram

class ElementInfoFrame{
-serialVersionUID: long
-components: ArrayList~ElementComponent~
-elementInfo: LinkedHashMap~String, Object~
-element: ElementComponent
-panel: JPanel
-textArea: JTextArea
-scrollPane: JScrollPane
-urlButton: JButton
 
-setLookAndFeel() void
-createComponents() void
-configureFrame() void
+windowDeactived(WindowEvent) void
+windowOpened(WindowEvent) void
+windowClosing(WindowEvent) void
+windowClosed(WindowEvent) void
+windowIconified(WindowEvent) void
+windowDeiconified(WindowEvent) void
+windowActivated(WindowEvent) void
}

class SortFrame{
-serialVersionUID: long
+COLUMN_NAMES: String[]
+SORT_OPTIONS: String[]
-elements: 
-table: JTable
-scrollPane: JScrollPane
-optionMenu: JComboBox~String~
-enterButton: JButton

+SortFrame(ArrayList~LinkedHashMap~)
-setLookAndFeel() void
-createComponents() void
-configureFrame() void
+windowDeactived(WindowEvent) void
+windowOpened(WindowEvent) void
+windowClosing(WindowEvent) void
+windowClosed(WindowEvent) void
+windowIconified(WindowEvent) void
+windowDeiconified(WindowEvent) void
+windowActivated(WindowEvent) void
}

class Button{
-sketch: PApplet
-x: float
-y: float
-width: float
-height: float
-color: int[]
-components: ArrayList~ElementComponent~

+Button(sketch: PApplet, text: String, x: float, y: float)
+Button(sketch: PApplet, components: ArrayList~ElementComponent~, x: float, y: float, series: Series)
+render() void
+ getText() String
+ inBounds() boolean
}

class Element{
-series: Series
-name: String
-symbol: String
-atomicNumber: int

+alphabeticSort(ArrayList~LinkedHashMap~) String[][]
+numericSort(ArrayList~LinkedHashMap~) String[][]
+getSeries() Series
+getName() String
+getSymbol(): String
+getAtomicNumber() int
}

class ElementComponent{
+RECT_SIZE: int
+CORNER_RADIUS: int
-sketch: PApplet
-element: Element
-x: float
-y: float
-r: int
-g: int
-b: int

+render() void
+getElement() Element
-changeColor(r: int, g: int, b: int)
+greyOut() void
+toOriginalColor() void
+inBounds() boolean
}

class PeriodicTable{
+fontNumber: PFont
+fontSymbol: PFont
+fontButton: PFont
-iconImage: PImage
-elementArray: JSONArray

+main() void
+settings() void
+setup() void
+draw() void
+mouseClicked() void
-populateElementInfo() void
-createElementComponents() void
-createButtons() void
-createInfoPanel(e: ElementComponent) void
-createSortFrame() void
}

class Series{
<<enumeration>>
ALKALI_METAL
ALKALINE_EARTH_METAL
LANTHANOID
ACTINOID
TRANSITION_METAL
POST_TRANSITION_METAL
METALLOID
REACTIVE_NONMETAL
NOBLE_GAS
unknown

-Series(isMetal: boolean, color: int[])
}

Element<--Series
ElementComponent<--Element
PeriodicTable<--Button
PeriodicTable<--SortFrame
PeriodicTable<--ElementInfoFrame
```

![GUI mockups]()
![Possibly logic diagram]

## 🛠️ Installation Instructions

1. Go to [PeriodicTable/dist/PeriodicTable.jar](https://github.com/9661328/cp2-individual-project/blob/main/PeriodicTable/dist/PeriodicTable.jar) and download the JAR file.
2. Run the file by double clicking it[^1].

[^1]: On Mac, you may need to go to System Preferences > Security & Privacy and click "Open Anyway" if the file is being prevented from running.

## 📸 Graphics

/screenshots here

## 🗒️ To-do: 

- [ ] Create flowchart and class diagram.
- [ ] Fix the documentation for the repository.
- [ ] Upload screenshots or a video
- [ ] Add repository to a portfolio.
