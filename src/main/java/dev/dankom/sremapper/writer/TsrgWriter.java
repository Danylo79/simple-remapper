package dev.dankom.sremapper.writer;

import dev.dankom.sremapper.mapping.paired.PairedClassMapping;
import dev.dankom.sremapper.mapping.paired.PairedFieldMapping;
import dev.dankom.sremapper.mapping.paired.PairedMethodMapping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class TsrgWriter extends MappingWriter<PairedClassMapping> {
    public TsrgWriter(List<PairedClassMapping> mappings, File out) {
        super(mappings, out);
    }

    @Override
    public void write() {
        try {
            FileWriter writer = new FileWriter(out);
            BufferedWriter buf = new BufferedWriter(writer);
            for (PairedClassMapping clazz : mappings) {
                if (clazz.getMappedName().equals(clazz.getObfName())) continue;

                buf.write(clazz.getObfName() + " " + clazz.getMappedName() + "\n");
                for (PairedFieldMapping field : clazz.getFields()) {
                    buf.write("\t" + field.getObfName() + " " + field.getMappedName() + "\n");
                }

                for (PairedMethodMapping method : clazz.getMethods()) {
                    buf.write("\t" + method.getObfName() + " (" + method.getParams() + ")" + method.getReturnType() + " " + method.getMappedName() + "\n");
                }
            }

            buf.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
