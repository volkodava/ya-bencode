package ru.ya.bencode;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import ru.ya.bencode.stream.BencodeOutputFactory;
import ru.ya.bencode.stream.BencodeStreamException;
import ru.ya.bencode.stream.BencodeStreamWriter;

public class BencodeSerializationTest {

    @Test
    public void bencodeSerializationTest() throws BencodeStreamException {
        writeString("spam");
        writeInt(42);
        writeInt(-42);
        List<Object> list = Arrays.<Object>asList("spam", 42);
        writeList(list);
        Map<Object, Object> map = new LinkedHashMap<Object, Object>();
        map.put("bar", "spam");
        map.put("foo", 42);
        writeMap(map);

        // complex tests
        List<Object> complexList1 = new ArrayList<Object>();
        complexList1.add("a");
        complexList1.add(Arrays.<Object>asList("a1", "a2"));
        write(complexList1);
        Map<Object, Object> complexMap1 = new LinkedHashMap<Object, Object>();
        complexMap1.put("a", Collections.singletonMap("a1", "a2"));
        write(complexMap1);
        Map<Object, Object> complexMap2 = new LinkedHashMap<Object, Object>();
        complexMap2.put("a", Collections.singletonMap("a1", Collections.singletonList("aaaaaaaaaa")));
        write(complexMap2);
        List<Object> complexList2 = new ArrayList<Object>();
        complexList2.add("a");
        complexList2.add(Collections.singletonMap("a1", Collections.singletonList("aaaaaaaaaa")));
        write(complexList2);
        Map<Object, Object> complexMap3 = new LinkedHashMap<Object, Object>();
        complexMap3.put("a", Collections.singletonMap("a1", Collections.singletonMap("a2", "a3")));
        write(complexMap3);
    }

    private void write(Object data) throws BencodeStreamException {
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

        System.out.println("Serialized: " + writer.getBuffer().toString());
    }

    private void writeString(String data) throws BencodeStreamException {
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
        bencoder.writeString(data);
        // Close the BencodeStreamWriter to free up resources
        bencoder.close();

        System.out.println("Serialized: " + writer.getBuffer().toString());
    }

    private void writeInt(int data) throws BencodeStreamException {
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
        bencoder.writeInt(data);
        // Close the BencodeStreamWriter to free up resources
        bencoder.close();

        System.out.println("Serialized: " + writer.getBuffer().toString());
    }

    private void writeList(List<Object> data) throws BencodeStreamException {
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
        bencoder.writeList(data);
        // Close the BencodeStreamWriter to free up resources
        bencoder.close();

        System.out.println("Serialized: " + writer.getBuffer().toString());
    }

    private void writeMap(Map<Object, Object> data) throws BencodeStreamException {
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
        bencoder.writeMap(data);
        // Close the BencodeStreamWriter to free up resources
        bencoder.close();

        System.out.println("Serialized: " + writer.getBuffer().toString());
    }
}
