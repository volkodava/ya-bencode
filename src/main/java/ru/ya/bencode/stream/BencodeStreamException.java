package ru.ya.bencode.stream;

/**
 * The base exception for unexpected processing errors. This Exception class is
 * used to report well-formedness errors as well as unexpected processing
 * conditions.
 *
 * @version 1.0
 * @author Anatolii Volkodav
 */
public class BencodeStreamException extends Exception {

    public BencodeStreamException() {
    }

    public BencodeStreamException(String message) {
        super(message);
    }

    public BencodeStreamException(String message, Throwable cause) {
        super(message, cause);
    }

    public BencodeStreamException(Throwable cause) {
        super(cause);
    }
}
