package dev.dankom.sremapper.writer;

import dev.dankom.sremapper.mapping.Mapping;
import dev.dankom.sremapper.mapping.namespaced.NamespacedClassMapping;
import dev.dankom.sremapper.mapping.namespaced.NamespacedFieldMapping;
import dev.dankom.sremapper.mapping.namespaced.NamespacedMapping;
import dev.dankom.sremapper.mapping.namespaced.NamespacedMethodMapping;
import dev.dankom.sremapper.mapping.paired.PairedClassMapping;
import dev.dankom.sremapper.mapping.paired.PairedFieldMapping;
import dev.dankom.sremapper.mapping.paired.PairedMapping;
import dev.dankom.sremapper.mapping.paired.PairedMethodMapping;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class MappingWriter<T extends Mapping> {
    private List<String> buf = new ArrayList<>();

    public void writePairedMappings(List<PairedClassMapping> mapping) {
        mapping.forEach(pcm -> writePairedMapping(pcm));
    }

    public void writePairedMapping(PairedClassMapping pcm) {
        if (getGenerator().isPaired()) {
            synchronized (buf) {
                buf.add(getGenerator().asPaired().generateClass(pcm));
                pcm.getFields().parallelStream().map(getGenerator().asPaired()::generateField).forEach(field -> buf.add(field));
                pcm.getMethods().parallelStream().map(getGenerator().asPaired()::generateMethod).forEach(method -> buf.add(method));
            }
        }
    }

    public void writeNamespacedMappings(List<NamespacedClassMapping> mapping) {
        mapping.forEach(ncm -> writeNamespacedMapping(ncm));
    }

    public void writeNamespacedMapping(NamespacedClassMapping ncm) {
        if (getGenerator().isNamespaced()) {
            synchronized (buf) {
                buf.add(getGenerator().asNamespaced().generateClass(ncm));
                ncm.getFields().parallelStream().map(getGenerator().asNamespaced()::generateField).forEach(field -> buf.add(field));
                ncm.getMethods().parallelStream().map(getGenerator().asNamespaced()::generateMethod).forEach(method -> buf.add(method));
            }
        }
    }

    public final void writeTo(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            synchronized (buf) {
                String header = getHeader();
                if (!header.isEmpty()) writer.write(header);
                writer.write(String.join("\n", buf));
                buf.clear();
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract MappingGenerator<T> getGenerator();

    public boolean shouldLock() {
        return false;
    }

    public String getHeader() {
        return "";
    }

    public interface MappingGenerator<T extends Mapping> {
        boolean isPaired();

        boolean isNamespaced();

        default PairedMappingGenerator asPaired() {
            return (PairedMappingGenerator) this;
        }

        default NamespacedMappingGenerator asNamespaced() {
            return (NamespacedMappingGenerator) this;
        }
    }

    interface PairedMappingGenerator extends MappingGenerator<PairedMapping> {
        String generateClass(PairedClassMapping mapping);

        String generateField(PairedFieldMapping mapping);

        String generateMethod(PairedMethodMapping mapping);
    }

    interface NamespacedMappingGenerator extends MappingGenerator<NamespacedMapping> {
        String generateClass(NamespacedClassMapping mapping);

        String generateField(NamespacedFieldMapping mapping);

        String generateMethod(NamespacedMethodMapping mapping);
    }
}
