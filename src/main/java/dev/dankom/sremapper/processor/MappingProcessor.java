package dev.dankom.sremapper.processor;

import dev.dankom.sremapper.mapping.ClassMapping;
import dev.dankom.sremapper.mapping.Mapping;
import dev.dankom.sremapper.reader.base.MappingReader;
import dev.dankom.sremapper.writer.MappingWriter;

import java.io.*;
import java.util.List;

public interface MappingProcessor<T extends Mapping, E extends ClassMapping<T>> {
    void writeClass(BufferedWriter buffer, E mapping);
    void writeField(BufferedWriter buffer, T mapping);
    void writeMethod(BufferedWriter buffer, T mapping);

    boolean isComment(String line);
    boolean isClass(String line);
    boolean isField(String line);
    boolean isMethod(String line);

    void readClass(String line);
    void readField(String line);
    void readMethod(String line);

    static <T extends Mapping, E extends ClassMapping<T>> MappingProcessorReader makeReader(File in, MappingProcessor<T, E> processor) {
        return new MappingProcessorReader(in, processor);
    }

    static <T extends Mapping, E extends ClassMapping<T>> MappingProcessorWriter makeWriter(List<T> mappings, File out, MappingProcessor<T, E> processor) {
        return new MappingProcessorWriter(mappings, out, processor);
    }

    class MappingProcessorReader<T extends Mapping, E extends ClassMapping<T>> extends MappingReader {
        private MappingProcessor<T, E> processor;

        public MappingProcessorReader(File in, MappingProcessor<T, E> processor) {
            super(in);
            this.processor = processor;
        }

        @Override
        public void read() {
            findClasses();

            try {
                FileReader reader = new FileReader(in);
                BufferedReader buf = new BufferedReader(reader);
                boolean loop = true;
                while (loop) {
                    String s = buf.readLine();
                    if (s != null && !s.isEmpty()) {
                        if (processor.isComment(s)) continue;

                        if (!processor.isClass(s)) {
                            if (processor.isMethod(s)) {
                                processor.readMethod(s);
                            } else if (processor.isField(s)) {
                                processor.readField(s);
                            }
                        } else {
                            processor.readClass(s);
                        }
                    } else loop = false;
                }

                buf.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void findClasses() {
            try {
                FileReader reader = new FileReader(in);
                BufferedReader buf = new BufferedReader(reader);
                boolean loop = true;
                while (loop) {
                    String s = buf.readLine();

                    if (s != null && !s.isEmpty()) {
                        if (processor.isComment(s)) continue;

                        if (processor.isClass(s)) {
                            processor.readClass(s);
                        }
                    } else loop = false;
                }

                buf.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class MappingProcessorWriter<T extends Mapping, E extends ClassMapping<T>> extends MappingWriter<T, E> {
        private MappingProcessor<T, E> processor;

        public MappingProcessorWriter(List<E> mappings, File out, MappingProcessor<T, E> processor) {
            super(mappings, out);
            this.processor = processor;
        }

        @Override
        public void write() {
            try {
                FileWriter fw = new FileWriter(out);
                BufferedWriter buffer = new BufferedWriter(fw);
                for (E mapping : mappings) {
                    processor.writeClass(buffer, mapping);

                    for (T field : mapping.getFields()) {
                        processor.writeField(buffer, field);
                    }

                    for (T method : mapping.getMethods()) {
                        processor.writeMethod(buffer, method);
                    }
                }
                buffer.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
