package io.github.bambooisland.pine.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("serial")
public class Table implements Serializable {
    protected final Column[] COLUMNS;
    protected List<Element> elements = new ArrayList<>();

    public Table(Column[] columns, Element[] elements) {
        this.COLUMNS = columns;
        List<Element> list = new ArrayList<>();
        for (Element ele : elements) {
            list.add(ele);
        }
        this.elements = list;
    }

    public Extractor cloneExtractor() {
        List<Element> list = new ArrayList<>();
        for (Element element : elements) {
            list.add(element);
        }
        return new Extractor(this.COLUMNS.clone(), list.toArray(new Element[list.size()]));
    }

    public int getNumberOfColumns() {
        return COLUMNS.length;
    }

    public Column getColumn(int i) {
        return COLUMNS[i];
    }

    public Column[] getAllColumns() {
        return COLUMNS;
    }

    public int getNumberOfElements() {
        return elements.size();
    }

    public Element getElement(int i) {
        return elements.get(i);
    }

    public Element[] getAllElements() {
        return elements.toArray(new Element[elements.size()]);
    }

    public void forEachElement(Consumer<Element> func) {
        for (Element element : elements) {
            func.accept(element);
        }
    }

    public Field<?> getField(int element, int column) {
        return this.elements.get(element).getField(column);
    }

    public String[] getAllKindsOfValues(int index) {
        List<String> list = new ArrayList<>();
        for (Element element : elements) {
            String string;
            if (!list.contains(string = element.getField(index).getStringValue())) {
                list.add(string);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    public Extractor and(Table table) {
        return this.cloneExtractor().and(table);
    }

    public Extractor or(Table table) {
        return this.cloneExtractor().or(table);
    }

    public Extractor insert(Element element) throws VoidOperationException {
        return this.cloneExtractor().insert(element);
    }

    public Extractor delete(Element element) {
        return this.cloneExtractor().delete(element);
    }

    public Extractor sort(Comparator<Element> comp) {
        return this.cloneExtractor().sort(comp);
    }

    public Extractor distinct() {
        return this.cloneExtractor().distinct();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(COLUMNS);
        result = prime * result + ((elements == null) ? 0 : elements.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Table other = (Table) obj;
        if (!Arrays.equals(COLUMNS, other.COLUMNS))
            return false;
        if (elements == null) {
            if (other.elements != null)
                return false;
        } else if (!elements.equals(other.elements))
            return false;
        return true;
    }
}
