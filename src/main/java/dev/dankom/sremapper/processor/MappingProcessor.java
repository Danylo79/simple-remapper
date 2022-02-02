package dev.dankom.sremapper.processor;

import dev.dankom.sremapper.mapping.ClassMapping;
import dev.dankom.sremapper.mapping.Mapping;
import dev.dankom.sremapper.reader.base.MappingReader;
import dev.dankom.sremapper.writer.MappingWriter;

import java.io.File;
import java.util.List;

public interface MappingProcessor<T extends Mapping> {
    void writeClass(StringBuffer buffer, ClassMapping<T> mapping);
    void writeField(StringBuffer buffer, T mapping);
    void writeMethod(StringBuffer buffer, T mapping);

    boolean isClass(String line);
    boolean isField(String line);
    boolean isMethod(String line);

    T readClass(String line);
    T readField(String line);
    T readMethod(String line);

    static <T extends Mapping> MappingProcessorReader makeReader(File in, MappingProcessor<T> processor) {
        return new MappingProcessorReader(in, processor);
    }

    static <T extends Mapping> MappingProcessorWriter makeWriter(List<T> mappings, File out, MappingProcessor<T> processor) {
        return new MappingProcessorWriter(mappings, out, processor);
    }

    class MappingProcessorReader<T extends Mapping> extends MappingReader {
        private MappingProcessor<T> processor;

        public MappingProcessorReader(File in, MappingProcessor<T> processor) {
            super(in);
            this.processor = processor;
        }

        @Override
        public void read() {

        }
    }

    class MappingProcessorWriter<T extends Mapping, E extends ClassMapping<T>> extends MappingWriter<T, E> {
        private MappingProcessor<T> processor;

        public MappingProcessorWriter(List<E> mappings, File out, MappingProcessor<T> processor) {
            super(mappings, out);
            this.processor = processor;
        }

        @Override
        public void write() {
            StringBuffer buffer = new StringBuffer();
            for (ClassMapping<T> mapping : mappings) {
                processor.writeClass(buffer, mapping);

                for (T field : mapping.getFields()) {
                    processor.writeField(buffer, field);
                }

                for (T method : mapping.getMethods()) {
                    processor.writeMethod(buffer, method);
                }
            }
        }
    }
}
