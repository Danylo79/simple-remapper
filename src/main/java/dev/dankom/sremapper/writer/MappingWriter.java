package dev.dankom.sremapper.writer;

import dev.dankom.sremapper.mapping.Mapping;

import java.io.File;
import java.util.List;

public abstract class MappingWriter<T extends Mapping> {
    protected final List<T> mappings;
    protected final File out;

    public MappingWriter(List<T> mappings, File out) {
        this.mappings = mappings;
        this.out = out;
    }

    public abstract void write();
}
