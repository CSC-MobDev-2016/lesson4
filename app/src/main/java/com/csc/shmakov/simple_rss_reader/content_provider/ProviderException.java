package com.csc.shmakov.simple_rss_reader.content_provider;

/**
 * Created by Pavel on 3/20/2016.
 */
public class ProviderException extends RuntimeException {
    public enum Type {
        PARSE_ERROR, NO_CONNECTION;
    }

    public final Type type;

    public ProviderException(Type type) {
        super();
        this.type = type;
    }

    public ProviderException(Type type, Throwable throwable) {
        super(throwable);
        this.type = type;
    }
}

