package ru.ya.bencode.stream;

import java.io.Writer;
import org.apache.commons.lang3.Validate;

/**
 * Defines an implementation of a factory for getting BencodeStreamWriter.
 *
 * @version 1.0
 * @author Anatolii Volkodav
 */
public class BencodeOutputFactory {

    private static final class INSTANCE_HOLDER {

        private static final BencodeOutputFactory INSTANCE = new BencodeOutputFactory();
    }

    public static BencodeOutputFactory newInstance() {
        return INSTANCE_HOLDER.INSTANCE;
    }

    public BencodeStreamWriter createBencodeStreamWriter(Writer writer) {
        Validate.notNull(writer, "Writer must not be null");

        return new BencodeStreamWriter(writer);
    }
}
