package io.github.bambooisland.pine.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import io.github.bambooisland.pine.base.values.BoolValue;
import io.github.bambooisland.pine.base.values.DoubleValue;
import io.github.bambooisland.pine.base.values.StringValue;

@SuppressWarnings("serial")
public class Extractor extends Table {
    public Table parseTable() {
        return new Table(COLUMNS, elements.toArray(new Element[elements.size()]));
    }

    protected Extractor(Column[] columns, Element[] first) {
        super(columns, first);
    }

    public Extractor where(int i, Operator<BoolValue> operator, boolean target) throws VoidOperationException {
        return where(i, operator, new BoolValue(target));
    }

    public Extractor where(int i, Operator<BoolValue> operator, BoolValue target) throws VoidOperationException {
        List<Element> list = new ArrayList<>();
        for (Element element : elements) {
            if (operator.accept((BoolValue) element.getField(i).getValue(), target)) {
                list.add(element);
            }
        }
        this.elements = list;
        return this;
    }

    public Extractor where(int i, Operator<DoubleValue> operator, double target) throws VoidOperationException {
        return where(i, operator, new DoubleValue(target));
    }

    public Extractor where(int i, Operator<DoubleValue> operator, DoubleValue target) throws VoidOperationException {
        List<Element> list = new ArrayList<>();
        for (Element element : elements) {
            if (operator.accept((DoubleValue) element.getField(i).getValue(), target)) {
                list.add(element);
            }
        }
        this.elements = list;
        return this;
    }

    public <T> Extractor where(int i, BiPredicate<StringValue, T> operator, T target) throws VoidOperationException {
        List<Element> list = new ArrayList<>();
        for (Element element : elements) {
            if (operator.test((StringValue) element.getField(i).getValue(), target)) {
                list.add(element);
            }
        }
        this.elements = list;
        return this;
    }

    public Extractor where(int i, Operator<StringValue> operator, String target) throws VoidOperationException {
        List<Element> list = new ArrayList<>();
        for (Element element : elements) {
            if (operator.accept((StringValue) element.getField(i).getValue(), new StringValue(target))) {
                list.add(element);
            }
        }
        this.elements = list;
        return this;
    }

    public final Element[] extract() {
        return elements.toArray(new Element[elements.size()]);
    }

    @Override
    public final Extractor and(Table table) {
        List<Element> list = new ArrayList<>();
        for (Element Element : table.elements) {
            if (elements.contains(Element)) {
                list.add(Element);
            }
        }
        elements = list;
        return this;
    }

    @Override
    public Extractor or(Table table) {
        for (Element Element : table.elements) {
            if (!elements.contains(Element)) {
                elements.add(Element);
            }
        }
        return this;
    }

    @Override
    public final Extractor insert(Element element) throws VoidOperationException {
        if (element.getAllFields().length != COLUMNS.length) {
            throw new VoidOperationException("the number of columns in the element is not equal to that of the table");
        }
        for (int i = 0; i < element.getAllFields().length; i++) {
            if (!element.getField(i).getColumn().equals(COLUMNS[i])) {
                throw new VoidOperationException("the columns don't fit with the table");
            }
        }
        elements.add(element);
        return this;
    }

    @Override
    public Extractor delete(Element element) {
        elements.remove(element);
        return this;
    }

    @Override
    public Extractor sort(Comparator<Element> comp) {
        Collections.sort(elements, comp);
        return this;
    }

    @Override
    public Extractor distinct() {
        elements = elements.stream().distinct().collect(Collectors.toList());
        return this;
    }

    @Override
    protected Extractor clone() {
        List<Element> list = new ArrayList<>();
        for (Element element : elements) {
            list.add(element);
        }
        return new Extractor(this.COLUMNS.clone(), list.toArray(new Element[list.size()]));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        return true;
    }
}
