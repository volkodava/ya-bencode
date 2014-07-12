package ru.ya.bencode;

import ru.ya.bencode.core.BencodeTestHelper;
import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import ru.ya.bencode.stream.BencodeStreamException;

public class BencodeDeserializationTest {

    BencodeTestHelper testHelper = new BencodeTestHelper();

    @Test
    public void bencodeDeserializationTest() throws BencodeStreamException {
        assertThat(testHelper.parse("4:spam").get(0), equalTo((Object) "spam"));
        assertThat(testHelper.parse("i42e").get(0), equalTo((Object) new Integer(42)));
        assertThat(testHelper.parse("i-42e").get(0), equalTo((Object) new Integer(-42)));
        assertThat((List<Object>) testHelper.parse("l4:spami42ee").get(0), contains((Object) "spam", (Object) 42));
        assertThat((Map<Object, Object>) testHelper.parse("d3:bar4:spam3:fooi42ee").get(0), hasEntry((Object) "bar", (Object) "spam"));
        assertThat((Map<Object, Object>) testHelper.parse("d3:bar4:spam3:fooi42ee").get(0), hasEntry((Object) "foo", (Object) 42));
    }

    @Test
    public void bencodeComplexDeserializationTest() throws BencodeStreamException {

        String source;
        String expected;
        String actual;
        Object result;

        source = "l1:al2:a12:a2ee";
        expected = source;
        result = testHelper.parse(expected).get(0);
        actual = testHelper.write(result);
        assertThat(actual, equalTo(expected));

        source = "d1:ad2:a12:a2ee";
        expected = source;
        result = testHelper.parse(expected).get(0);
        actual = testHelper.write(result);
        assertThat(actual, equalTo(expected));

        source = "d1:ad2:a1l10:aaaaaaaaaaeee";
        expected = source;
        result = testHelper.parse(expected).get(0);
        actual = testHelper.write(result);
        assertThat(actual, equalTo(expected));

        source = "l1:ad2:a1l10:aaaaaaaaaaeee";
        expected = source;
        result = testHelper.parse(expected).get(0);
        actual = testHelper.write(result);
        assertThat(actual, equalTo(expected));

        source = "2:bcl1:ad2:a1l10:aaaaaaaaaaeee";
        expected = "2:bc";
        result = testHelper.parse(source).get(0);
        actual = testHelper.write(result);
        assertThat(actual, equalTo(expected));
        expected = "l1:ad2:a1l10:aaaaaaaaaaeee";
        result = testHelper.parse(source).get(1);
        actual = testHelper.write(result);
        assertThat(actual, equalTo(expected));

        source = "d1:ad2:a1d2:a22:a3eee";
        expected = source;
        result = testHelper.parse(expected).get(0);
        actual = testHelper.write(result);
        assertThat(actual, equalTo(expected));
    }
}
