package io.github.bambooisland.pine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import io.github.bambooisland.pine.base.Extractor;
import io.github.bambooisland.pine.base.Table;
import io.github.bambooisland.pine.base.values.BoolValue;
import io.github.bambooisland.pine.base.values.DoubleValue;
import io.github.bambooisland.pine.base.values.StringValue;
import io.github.bambooisland.pine.poi.PoiTableBuilder;

public class TMTest {
    @Test
    void test() throws IOException {
        Table table = PoiTableBuilder.getTable(new File(getClass().getResource("shinkin.xlsx").getPath()), 0);
        assertEquals(table.getAllColumns().length, 8);
        assertFalse(((BoolValue) table.getField(205, 7).getValue()).getBool());
        testImpl(table);
        System.out.println("xlsx test finished");

        table = PoiTableBuilder.getTable(new File(getClass().getResource("shinkin.xls").getPath()), 0);
        assertEquals(table.getAllColumns().length, 7);
        testImpl(table);
        System.out.println("xls test finished");
    }

    void testImpl(Table table) throws IOException {
        assertTrue(table.getElement(74).getField(1).getValue().equals(new StringValue("東京都中央区")));
        assertEquals(table.getNumberOfElements(), 254);

        Extractor ex = table.cloneExtractor();
        assertEquals(ex.where(1, StringValue.LIKE_PREDICATE, Pattern.compile(".*県.*")).getNumberOfElements(), 201);

        ex.cloneExtractor().where(1, StringValue.LIKE_PREDICATE, Pattern.compile(".*区")).forEachElement(ex::delete);
        assertEquals(ex.getNumberOfElements(), 181);

        assertEquals(ex.and(table.cloneExtractor().where(3, DoubleValue.LESS, 1000)).getNumberOfElements(), 27);
    }
}
