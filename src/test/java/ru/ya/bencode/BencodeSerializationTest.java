package ru.ya.bencode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import ru.ya.bencode.core.BencodeTestHelper;
import ru.ya.bencode.stream.BencodeStreamException;

public class BencodeSerializationTest {

    BencodeTestHelper testHelper = new BencodeTestHelper();

    @Test
    public void bencodeSerializationTest() throws BencodeStreamException {
        testHelper.writeString("spam");
        testHelper.writeInt(42);
        testHelper.writeInt(-42);
        List<Object> list = Arrays.<Object>asList("spam", 42);
        testHelper.writeList(list);
        Map<Object, Object> map = new LinkedHashMap<Object, Object>();
        map.put("bar", "spam");
        map.put("foo", 42);
        testHelper.writeMap(map);
    }

    @Test
    public void bencodeComplexSerializationTest() throws BencodeStreamException {
        List<Object> complexList1 = new ArrayList<Object>();
        complexList1.add("a");
        complexList1.add(Arrays.<Object>asList("a1", "a2"));
        testHelper.write(complexList1);
        Map<Object, Object> complexMap1 = new LinkedHashMap<Object, Object>();
        complexMap1.put("a", Collections.singletonMap("a1", "a2"));
        testHelper.write(complexMap1);
        Map<Object, Object> complexMap2 = new LinkedHashMap<Object, Object>();
        complexMap2.put("a", Collections.singletonMap("a1", Collections.singletonList("aaaaaaaaaa")));
        testHelper.write(complexMap2);
        List<Object> complexList2 = new ArrayList<Object>();
        complexList2.add("a");
        complexList2.add(Collections.singletonMap("a1", Collections.singletonList("aaaaaaaaaa")));
        testHelper.write(complexList2);
        Map<Object, Object> complexMap3 = new LinkedHashMap<Object, Object>();
        complexMap3.put("a", Collections.singletonMap("a1", Collections.singletonMap("a2", "a3")));
        testHelper.write(complexMap3);
    }
}
