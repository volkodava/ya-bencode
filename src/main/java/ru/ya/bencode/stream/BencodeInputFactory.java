package ru.ya.bencode.stream;

import java.io.Reader;
import org.apache.commons.lang3.Validate;

/**
 * Defines an implementation of a factory for getting BencodeStreamReader.
 *
 * @version 1.0
 * @author Anatolii Volkodav
 */
public class BencodeInputFactory {

    private static final class INSTANCE_HOLDER {

        private static final BencodeInputFactory INSTANCE = new BencodeInputFactory();
    }

    public static BencodeInputFactory newInstance() {
        return INSTANCE_HOLDER.INSTANCE;
    }

    public BencodeStreamReader createBencodeStreamReader(Reader reader) {
        Validate.notNull(reader, "Reader must not be null");

        return new BencodeStreamReader(reader);
    }
}
