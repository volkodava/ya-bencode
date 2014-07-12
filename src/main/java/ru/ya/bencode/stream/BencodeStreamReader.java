package ru.ya.bencode.stream;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BencodeStreamReader {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final Reader reader;
    private int position = -1;
    private Object value;

    public BencodeStreamReader(Reader reader) {
        this.reader = reader;
    }

    /**
     * Returns true if there are more parsing objects and false if there are no
     * more objects.
     *
     * @return true if there are more objects, false otherwise
     * @throws BencodeStreamException if there is a fatal error detecting the
     * next state
     */
    public boolean hasNext() throws BencodeStreamException {
        logger.debug("Checking for next available value");
        value = null;
        try {
            value = readNextValue();
        } catch (IOException ex) {
            throw new BencodeStreamException("Can't check the next character from the stream", ex);
        }
        return value != null;
    }

    /**
     * Get next parsing object - a processor return the current parse object.
     *
     * @return the current parse object
     * @throws NoSuchElementException if this is called when hasNext() returns
     * false
     * @throws BencodeStreamException if there is an error processing the
     * underlying Bencode source
     */
    public Object next() throws BencodeStreamException {
        return value;
    }

    /**
     * Frees any resources associated with this Reader. This method does not
     * close the underlying input source.
     *
     * @throws BencodeStreamException if there are errors freeing associated
     * resources
     */
    public void close() throws BencodeStreamException {
        try {
            reader.close();
        } catch (IOException ex) {
            throw new BencodeStreamException("Can't close the reader", ex);
        }
    }

    private Object readNextValue() throws IOException {
        int iChar;
        char curCh;
        Object result = null;
        while ((iChar = reader.read()) != -1) {
            position++;
            curCh = (char) iChar;

            if (curCh == 'e') {
                return result;
            } else if (Character.isDigit(curCh)) {
                int numOfChars = readNumOfChars(curCh);
                String stringData = readString(numOfChars);
                return stringData;
            } else if (curCh == 'i') {
                String intData = readInt();
                return Integer.valueOf(intData);
            } else if (curCh == 'd') {
                Map<Object, Object> map = readMap(new LinkedHashMap<Object, Object>());
                return map;
            } else if (curCh == 'l') {
                List<Object> list = readList(new ArrayList<Object>());
                return list;
            }
        }

        return result;
    }

    private Map<Object, Object> readMap(Map<Object, Object> result) throws IOException {
        int iChar;
        char curCh;
        Object key = null;
        while ((iChar = reader.read()) != -1) {
            position++;
            curCh = (char) iChar;

            if (curCh == 'e') {
                return result;
            } else if (Character.isDigit(curCh)) {
                int numOfChars = readNumOfChars(curCh);
                String stringData = readString(numOfChars);
                if (key == null) {
                    key = stringData;
                } else {
                    result.put(key, stringData);
                    key = null;
                }
            } else if (curCh == 'i') {
                String intData = readInt();
                if (key == null) {
                    key = Integer.valueOf(intData);
                } else {
                    result.put(key, Integer.valueOf(intData));
                    key = null;
                }
            } else if (curCh == 'd') {
                if (position == 0) {
                    return readMap(result);
                }

                if (key == null) {
                    key = readMap(new LinkedHashMap<Object, Object>());
                } else {
                    result.put(key, readMap(new LinkedHashMap<Object, Object>()));
                    key = null;
                }
            } else if (curCh == 'l') {
                if (key == null) {
                    key = readList(new ArrayList<Object>());
                } else {
                    result.put(key, readList(new ArrayList<Object>()));
                    key = null;
                }
            }
        }

        return result;
    }

    private List<Object> readList(List<Object> result) throws IOException {
        int iChar;
        char curCh;
        while ((iChar = reader.read()) != -1) {
            position++;
            curCh = (char) iChar;

            if (curCh == 'e') {
                return result;
            } else if (Character.isDigit(curCh)) {
                int numOfChars = readNumOfChars(curCh);
                String stringData = readString(numOfChars);
                result.add(stringData);
            } else if (curCh == 'i') {
                String intData = readInt();
                result.add(Integer.valueOf(intData));
            } else if (curCh == 'l') {
                if (position == 0) {
                    return readList(result);
                }

                result.add(readList(new ArrayList<Object>()));
            } else if (curCh == 'd') {
                result.add(readMap(new LinkedHashMap<Object, Object>()));
            }
        }

        return result;
    }

    private int readNumOfChars(char firstDigit) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(firstDigit);
        int iChar;
        while ((iChar = reader.read()) != -1) {
            position++;
            char ch = (char) iChar;
            if (ch == ':') {
                break;
            }

            if (!Character.isDigit(ch)) {
                throw new IllegalStateException("Invalid data format. Expected digit, but got '" + ch + "'.");
            }

            sb.append(ch);
        }

        String strVal = sb.toString();
        return Integer.valueOf(strVal);
    }

    private String readString(int numOfChars) throws IOException {
        StringBuilder sb = new StringBuilder(numOfChars);
        int count = 0;
        int iChar;
        while (numOfChars > count && ((iChar = reader.read()) != -1)) {
            position++;
            sb.append((char) iChar);
            count++;
        }

        return sb.toString();
    }

    private String readInt() throws IOException {
        StringBuilder sb = new StringBuilder();
        int iChar;
        while (((iChar = reader.read()) != -1) && iChar != 'e') {
            position++;
            sb.append((char) iChar);
        }

        return sb.toString();
    }
}
