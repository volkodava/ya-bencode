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

/**
 * Defines the helper class for serialization and deserialization strings.
 *
 * @version 1.0
 * @author Anatolii Volkodav
 */
public class BencodeTestHelper {

    /**
     * Parse any Bencoded string into the list of java objects.
     *
     * @param source the Bencoded string
     * @return the list of parsed java objects
     * @throws BencodeStreamException used to report unexpected processing
     * conditions
     */
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

        // return list of parsed java objects
        return resultList;
    }

    /**
     * Serialize java objects into Bencoded string.
     * <p>
     * Supports next types (<code>Bencoded type</code> ---
     * <code>Java type</code>):
     * <ul>
     * <li><code>Bencoded Strings</code> --- <code>Java</code>
     * {@link java.lang.String}</li>
     * <li><code>Integers</code> --- <code>Java</code>
     * {@link java.lang.Integer}</li>
     * <li><code>Lists</code> --- <code>Java</code> {@link java.util.List}</li>
     * <li><code>Dictionaries</code> --- <code>Java</code>
     * {@link java.util.Map}</li>
     * </ul>
     * <p>
     *
     * @param data one of java objects:
     * {@link java.lang.String}, {@link java.lang.Integer}, {@link java.util.List}, {@link java.util.Map}
     * @return Bencoded string
     * @throws BencodeStreamException used to report unexpected processing
     * conditions
     * @throws IllegalArgumentException used to report illegal or inappropriate
     * object type
     */
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

        // return serialized object as string
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
