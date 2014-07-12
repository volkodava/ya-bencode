package ru.ya.bencode.stream;

/**
 * The base exception for unexpected processing errors. This Exception class is
 * used to report well-formedness errors as well as unexpected processing
 * conditions.
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
