package ru.ya.bencode.stream;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BencodeStreamWriter {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final Writer writer;

    public BencodeStreamWriter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Writes an Object section
     *
     * @param data the data contained in the Object Section, may not be null
     * @throws BencodeStreamException
     */
    public void write(Object data) throws BencodeStreamException {
        logger.debug("Writing data: " + data);
        if (data == null) {
            logger.warn("Data is null");
            return;
        }

        if (data instanceof String) {
            logger.debug("Writing data as String");
            writeString((String) data);
        } else if (data instanceof Integer) {
            logger.debug("Writing data as Integer");
            writeInt((Integer) data);
        } else if (data instanceof List) {
            logger.debug("Writing data as List");
            writeList((List) data);
        } else if (data instanceof Map) {
            logger.debug("Writing data as Map");
            writeMap((Map) data);
        } else {
            throw new BencodeStreamException("Object type not supported: " + data.getClass().getName());
        }
    }

    /**
     * Writes a String section
     *
     * @param data the data contained in the String Section, may not be null
     * @throws BencodeStreamException
     */
    public void writeString(String data) throws BencodeStreamException {
        if (data == null || data.isEmpty()) {
            logger.warn("Data is null");
            return;
        }

        try {
            writer.write("" + data.length());
            writer.write(":");
            writer.write(data);
        } catch (IOException ex) {
            throw new BencodeStreamException("Can't write the data to the stream", ex);
        }
    }

    /**
     * Writes an Integer section
     *
     * @param data the data contained in the Integer Section, may not be null
     * @throws BencodeStreamException
     */
    public void writeInt(int data) throws BencodeStreamException {
        try {
            writer.write("i");
            writer.write("" + data);
            writer.write("e");
        } catch (IOException ex) {
            throw new BencodeStreamException("Can't write the data to the stream", ex);
        }
    }

    /**
     * Writes a List section
     *
     * @param data the data contained in the List Section, may not be null
     * @throws BencodeStreamException
     */
    public void writeList(List<Object> data) throws BencodeStreamException {
        if (data == null || data.isEmpty()) {
            logger.warn("Data is null");
            return;
        }

        try {
            writer.write("l");
            for (Object obj : data) {
                if (obj instanceof String) {
                    writeString((String) obj);
                } else if (obj instanceof Integer) {
                    writeInt((Integer) obj);
                } else if (obj instanceof List) {
                    writeList((List) obj);
                } else if (obj instanceof Map) {
                    writeMap((Map) obj);
                }
            }
            writer.write("e");
        } catch (IOException ex) {
            throw new BencodeStreamException("Can't write the data to the stream", ex);
        }
    }

    /**
     * Writes a Map section
     *
     * @param data the data contained in the Map Section, may not be null
     * @throws BencodeStreamException
     */
    public void writeMap(Map<Object, Object> data) throws BencodeStreamException {
        if (data == null || data.isEmpty()) {
            logger.warn("Data is null");
            return;
        }

        try {
            writer.write("d");
            for (Entry<Object, Object> entry : data.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();

                if (key instanceof String) {
                    writeString((String) key);
                } else if (key instanceof Integer) {
                    writeInt((Integer) key);
                } else if (key instanceof List) {
                    writeList((List) key);
                } else if (key instanceof Map) {
                    writeMap((Map) key);
                }

                if (value instanceof String) {
                    writeString((String) value);
                } else if (value instanceof Integer) {
                    writeInt((Integer) value);
                } else if (value instanceof List) {
                    writeList((List) value);
                } else if (value instanceof Map) {
                    writeMap((Map) value);
                }
            }
            writer.write("e");
        } catch (IOException ex) {
            throw new BencodeStreamException("Can't write the data to the stream", ex);
        }
    }

    /**
     * Close this writer and free any resources associated with the writer. This
     * must not close the underlying output stream.
     *
     * @throws BencodeStreamException
     */
    public void close() throws BencodeStreamException {
        try {
            writer.close();
        } catch (IOException ex) {
            throw new BencodeStreamException("Can't close the writer", ex);
        }
    }

}
