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
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class MappingWriter<T extends Mapping> {
    private List<String> buf = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    private void ensurePackageGenerator() {
        if (!(getGenerator() instanceof PackageMappingGenerator))
            throw new UnsupportedOperationException("This type of mapping doesn't support package mappings");
    }

    public void writePairedPackage(PairedMapping pkg) {
        ensurePackageGenerator();

        if (getGenerator().isPaired()) {
            synchronized (buf) {
                buf.add(((PackageMappingGenerator) getGenerator()).generatePackage(pkg));
            }
        }
    }

    public void writeNamespacedPackage(NamespacedMapping pkg) {
        ensurePackageGenerator();

        if (getGenerator().isNamespaced()) {
            synchronized (buf) {
                buf.add(((PackageMappingGenerator) getGenerator()).generatePackage(pkg));
            }
        }
    }

    public void writePairedMappings(List<PairedClassMapping> mapping) {
        mapping.forEach(pcm -> writePairedMapping(pcm));
    }

    public void writePairedMapping(PairedClassMapping pcm) {
        if (shouldLock()) lock.lock();

        if (getGenerator().isPaired()) {
            try {
                synchronized (buf) {
                    buf.add(getGenerator().asPaired().generateClass(pcm));
                    pcm.getFields().parallelStream().map(getGenerator().asPaired()::generateField).forEach(field -> buf.add(field));
                    pcm.getMethods().parallelStream().map(getGenerator().asPaired()::generateMethod).forEach(method -> buf.add(method));
                }
            } finally {
                if (shouldLock()) lock.lock();
            }
        }
    }

    public void writeNamespacedMappings(List<NamespacedClassMapping> mapping) {
        mapping.forEach(ncm -> writeNamespacedMapping(ncm));
    }

    public void writeNamespacedMapping(NamespacedClassMapping ncm) {
        if (shouldLock()) lock.lock();

        if (getGenerator().isPaired()) {
            try {
                synchronized (buf) {
                    buf.add(getGenerator().asNamespaced().generateClass(ncm));
                    ncm.getFields().parallelStream().map(getGenerator().asNamespaced()::generateField).forEach(field -> buf.add(field));
                    ncm.getMethods().parallelStream().map(getGenerator().asNamespaced()::generateMethod).forEach(method -> buf.add(method));
                }
            } finally {
                if (shouldLock()) lock.lock();
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

    interface PackageMappingGenerator {
        String generatePackage(Mapping mapping);
    }
}
