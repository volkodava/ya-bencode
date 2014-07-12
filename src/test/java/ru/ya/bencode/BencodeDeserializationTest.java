package ru.ya.bencode;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import ru.ya.bencode.stream.BencodeInputFactory;
import ru.ya.bencode.stream.BencodeOutputFactory;
import ru.ya.bencode.stream.BencodeStreamException;
import ru.ya.bencode.stream.BencodeStreamReader;
import ru.ya.bencode.stream.BencodeStreamWriter;

public class BencodeDeserializationTest {

    @Test
    public void bencodeDeserializationTest() throws BencodeStreamException {
        assertThat(parse("4:spam"), hasItem("spam"));
        assertThat(parse("i42e"), hasItem(42));
        assertThat(parse("i-42e"), hasItem(-42));
        assertThat((List<Object>) parse("l4:spami42ee").get(0), contains((Object) "spam", (Object) 42));
        assertThat((Map<Object, Object>) parse("d3:bar4:spam3:fooi42ee").get(0), hasEntry((Object) "bar", (Object) "spam"));
        assertThat((Map<Object, Object>) parse("d3:bar4:spam3:fooi42ee").get(0), hasEntry((Object) "foo", (Object) 42));

        String expected;
        String actual;
        List<Object> result;

        expected = "l1:al2:a12:a2ee";
        result = parse(expected);
        actual = write(result);
        assertThat(actual, equalTo(expected));

        expected = "d1:ad2:a12:a2ee";
        result = parse(expected);
        actual = write(result);
        assertThat(actual, equalTo(expected));

        expected = "d1:ad2:a1l10:aaaaaaaaaaeee";
        result = parse(expected);
        actual = write(result);
        assertThat(actual, equalTo(expected));

        expected = "l1:ad2:a1l10:aaaaaaaaaaeee";
        result = parse(expected);
        actual = write(result);
        assertThat(actual, equalTo(expected));

        expected = "2:bcl1:ad2:a1l10:aaaaaaaaaaeee";
        result = parse(expected);
        actual = write(result);
        assertThat(actual, equalTo(expected));

        expected = "d1:ad2:a1d2:a22:a3eee";
        result = parse(expected);
        actual = write(result);
        assertThat(actual, equalTo(expected));
    }

    private List<Object> parse(String source) throws BencodeStreamException {
        StringReader reader = new StringReader(source);

        //
        // Get an input factory
        //
        BencodeInputFactory bif = BencodeInputFactory.newInstance();

        //
        // Instantiate a reader
        //
        BencodeStreamReader bencoder = bif.createBencodeStreamReader(reader);

        // Result container
        List<Object> resultList = new ArrayList<Object>();
        //
        // Parse the Bencode
        //
        while (bencoder.hasNext()) {
            Object result = bencoder.next();
            resultList.add(result);
        }
        //
        // Close the reader
        //
        bencoder.close();

        return resultList;
    }

    private String write(Object data) throws BencodeStreamException {
        StringWriter writer = new StringWriter();

        //
        // Get an output factory
        //
        BencodeOutputFactory bof = BencodeOutputFactory.newInstance();
        //
        // Instantiate a writer
        //
        BencodeStreamWriter bencoder = bof.createBencodeStreamWriter(writer);
        //
        // Generate the Bencode
        //
        bencoder.write(data);
        // Close the BencodeStreamWriter to free up resources
        bencoder.close();

        return writer.getBuffer().toString();
    }

}
