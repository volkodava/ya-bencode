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
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Defines the tests to cover serialization cases.
 *
 * @version 1.0
 * @author Anatolii Volkodav
 */
public class BencodeSerializationTest {

    BencodeTestHelper testHelper = new BencodeTestHelper();

    @Test
    public void bencodeSerializationTest() throws BencodeStreamException {
        assertThat(testHelper.writeString("spam"), equalTo("4:spam"));
        assertThat(testHelper.writeInt(42), equalTo("i42e"));
        assertThat(testHelper.writeInt(-42), equalTo("i-42e"));
        List<Object> list = Arrays.<Object>asList("spam", 42);
        assertThat(testHelper.writeList(list), equalTo("l4:spami42ee"));
        Map<Object, Object> map = new LinkedHashMap<Object, Object>();
        map.put("bar", "spam");
        map.put("foo", 42);
        assertThat(testHelper.writeMap(map), equalTo("d3:bar4:spam3:fooi42ee"));
    }

    @Test
    public void bencodeComplexSerializationTest() throws BencodeStreamException {
        List<Object> complexList1 = new ArrayList<Object>();
        complexList1.add("a");
        complexList1.add(Arrays.<Object>asList("a1", "a2"));
        assertThat(testHelper.write(complexList1), equalTo("l1:al2:a12:a2ee"));
        Map<Object, Object> complexMap1 = new LinkedHashMap<Object, Object>();
        complexMap1.put("a", Collections.singletonMap("a1", "a2"));
        assertThat(testHelper.write(complexMap1), equalTo("d1:ad2:a12:a2ee"));
        Map<Object, Object> complexMap2 = new LinkedHashMap<Object, Object>();
        complexMap2.put("a", Collections.singletonMap("a1", Collections.singletonList("aaaaaaaaaa")));
        assertThat(testHelper.write(complexMap2), equalTo("d1:ad2:a1l10:aaaaaaaaaaeee"));
        List<Object> complexList2 = new ArrayList<Object>();
        complexList2.add("a");
        complexList2.add(Collections.singletonMap("a1", Collections.singletonList("aaaaaaaaaa")));
        assertThat(testHelper.write(complexList2), equalTo("l1:ad2:a1l10:aaaaaaaaaaeee"));
        Map<Object, Object> complexMap3 = new LinkedHashMap<Object, Object>();
        complexMap3.put("a", Collections.singletonMap("a1", Collections.singletonMap("a2", "a3")));
        assertThat(testHelper.write(complexMap3), equalTo("d1:ad2:a1d2:a22:a3eee"));
    }
}
