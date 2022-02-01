package dev.dankom.sremapper.reader.base;

import java.io.File;

public abstract class MappingReader {
    protected final File in;

    public MappingReader(File in) {
        this.in = in;
    }

    public abstract void read();
}
