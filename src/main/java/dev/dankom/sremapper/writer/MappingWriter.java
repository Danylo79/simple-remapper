package dev.dankom.sremapper.writer;

import dev.dankom.sremapper.mapping.ClassMapping;
import dev.dankom.sremapper.mapping.Mapping;

import java.io.File;
import java.util.List;

public abstract class MappingWriter<T extends Mapping, E extends ClassMapping<T>> {
    protected final List<E> mappings;
    protected final File out;

    public MappingWriter(List<E> mappings, File out) {
        this.mappings = mappings;
        this.out = out;
    }

    public abstract void write();
}
