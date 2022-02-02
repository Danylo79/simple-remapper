package dev.dankom.sremapper;

import dev.dankom.sremapper.mapping.paired.PairedClassMapping;
import dev.dankom.sremapper.mapping.paired.PairedFieldMapping;
import dev.dankom.sremapper.mapping.paired.PairedMapping;
import dev.dankom.sremapper.mapping.paired.PairedMethodMapping;
import dev.dankom.sremapper.processor.MappingProcessor;
import dev.dankom.sremapper.writer.TsrgWriter;
import dev.dankom.util.general.DataStructureAdapter;

import java.io.BufferedWriter;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        PairedClassMapping clazz = new PairedClassMapping("aaa", "class");
        clazz.addField(new PairedFieldMapping("a", "field"));
        clazz.addMethod(new PairedMethodMapping("a", "method", "V", ""));
        new TsrgWriter(DataStructureAdapter.arrayToList(clazz), new File("./test.tsrg")).write();

        MappingProcessor<PairedMapping, PairedClassMapping> processor = new MappingProcessor<PairedMapping, PairedClassMapping>() {
            @Override
            public void writeClass(BufferedWriter buffer, PairedClassMapping mapping) {

            }

            @Override
            public void writeField(BufferedWriter buffer, PairedMapping mapping) {

            }

            @Override
            public void writeMethod(BufferedWriter buffer, PairedMapping mapping) {

            }

            @Override
            public boolean isComment(String line) {
                return line.startsWith("#");
            }

            @Override
            public boolean isClass(String line) {
                return !line.startsWith("\t");
            }

            @Override
            public boolean isField(String line) {
                return !isMethod(line);
            }

            @Override
            public boolean isMethod(String line) {
                return line.contains("(") && line.contains(")");
            }

            @Override
            public void readClass(String line) {
                System.out.println("Class: " + line);
            }

            @Override
            public void readField(String line) {
                System.out.println("Field: " + line);
            }

            @Override
            public void readMethod(String line) {
                System.out.println("Method: " + line);
            }
        };

        MappingProcessor.makeReader(new File("./test.tsrg"), processor).read();
    }
}
