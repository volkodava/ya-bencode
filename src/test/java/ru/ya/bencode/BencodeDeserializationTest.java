package ru.ya.bencode;

import java.io.StringReader;
import org.junit.Test;
import ru.ya.bencode.stream.BencodeInputFactory;
import ru.ya.bencode.stream.BencodeStreamException;
import ru.ya.bencode.stream.BencodeStreamReader;

public class BencodeDeserializationTest {

    @Test
    public void bencodeDeserializationTest() throws BencodeStreamException {
        parse("4:spam");
        parse("i42e");
        parse("i-42e");
        parse("l4:spami42ee");
        parse("d3:bar4:spam3:fooi42ee");

        parse("l1:al2:a12:a2ee");
        parse("d1:ad2:a12:a2ee");
        parse("d1:ad2:a1l10:aaaaaaaaaaeee");
        parse("l1:ad2:a1l10:aaaaaaaaaaeee");
        parse("2:bcl1:ad2:a1l10:aaaaaaaaaaeee");
        parse("d1:ad2:a1d2:a22:a3eee");
    }

    private void parse(String source) throws BencodeStreamException {
        StringReader reader = new StringReader(source);

        //
        // Get an input factory
        //
        BencodeInputFactory bif = BencodeInputFactory.newInstance();

        //
        // Instantiate a reader
        //
        BencodeStreamReader bencoder = bif.createBencodeStreamReader(reader);
        //
        // Parse the Bencode
        //
        while (bencoder.hasNext()) {
            Object result = bencoder.next();
            printResult(result);
        }
        //
        // Close the reader
        //
        bencoder.close();
    }

    private void printResult(Object result) {
        System.out.println("Deserialized: " + result);
    }
}
