package dev.dankom.sremapper.writer;

import dev.dankom.sremapper.mapping.paired.PairedClassMapping;
import dev.dankom.sremapper.mapping.paired.PairedFieldMapping;
import dev.dankom.sremapper.mapping.paired.PairedMapping;
import dev.dankom.sremapper.mapping.paired.PairedMethodMapping;

import java.io.File;

public class TsrgWriter extends MappingWriter<PairedMapping> {
    @Override
    public MappingGenerator<PairedMapping> getGenerator() {
        return new TsrgGenerator();
    }

    class TsrgGenerator implements PairedMappingGenerator {
        @Override
        public boolean isPaired() {
            return true;
        }

        @Override
        public boolean isNamespaced() {
            return false;
        }

        @Override
        public String generateClass(PairedClassMapping mapping) {
            return mapping.getObfName() + " " + mapping.getMappedName();
        }

        @Override
        public String generateField(PairedFieldMapping mapping) {
            return "\t" + mapping.getObfName() + " " + mapping.getMappedName();
        }

        @Override
        public String generateMethod(PairedMethodMapping mapping) {
            return "\t" + mapping.getObfName() + " (" + mapping.getParams() + ")" + mapping.getReturnType() + " " + mapping.getMappedName();
        }
    }
}
