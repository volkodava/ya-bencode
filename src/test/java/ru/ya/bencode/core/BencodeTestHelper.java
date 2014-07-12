package ru.ya.bencode.core;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ru.ya.bencode.stream.BencodeInputFactory;
import ru.ya.bencode.stream.BencodeOutputFactory;
import ru.ya.bencode.stream.BencodeStreamException;
import ru.ya.bencode.stream.BencodeStreamReader;
import ru.ya.bencode.stream.BencodeStreamWriter;

public class BencodeTestHelper {

    public List<Object> parse(String source) throws BencodeStreamException {
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

    public String write(Object data) throws BencodeStreamException {
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

    public String writeString(String data) throws BencodeStreamException {
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

        return writer.getBuffer().toString();
    }

    public String writeInt(int data) throws BencodeStreamException {
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

        return writer.getBuffer().toString();
    }

    public String writeList(List<Object> data) throws BencodeStreamException {
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

        return writer.getBuffer().toString();
    }

    public String writeMap(Map<Object, Object> data) throws BencodeStreamException {
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

        return writer.getBuffer().toString();
    }
}
