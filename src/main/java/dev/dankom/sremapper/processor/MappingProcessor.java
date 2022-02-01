package dev.dankom.sremapper.processor;

import dev.dankom.sremapper.mapping.Mapping;
import dev.dankom.sremapper.reader.base.MappingReader;
import dev.dankom.sremapper.writer.MappingWriter;

import java.io.File;
import java.util.List;

public interface MappingProcessor<T extends Mapping> {
    void writeClass(StringBuffer buffer, T mapping);
    void writeField(StringBuffer buffer, T mapping);
    void writeMethod(StringBuffer buffer, T mapping);

    boolean isClass(String line);
    boolean isField(String line);
    boolean isMethod(String line);

    T readLine();

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

    class MappingProcessorWriter<T extends Mapping> extends MappingWriter<T> {
        private MappingProcessor<T> processor;

        public MappingProcessorWriter(List<T> mappings, File out, MappingProcessor<T> processor) {
            super(mappings, out);
            this.processor = processor;
        }

        @Override
        public void write() {

        }
    }
}
