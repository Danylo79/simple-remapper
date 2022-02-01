package dev.dankom.sremapper.reader;

import dev.dankom.sremapper.mapping.paired.PairedClassMapping;
import dev.dankom.sremapper.mapping.paired.PairedFieldMapping;
import dev.dankom.sremapper.mapping.paired.PairedMethodMapping;
import dev.dankom.sremapper.reader.base.PairedMappingReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class McpReader extends PairedMappingReader {
    public McpReader(File in) {
        super(in);
    }

    @Override
    public void read() {
        findClasses();

        try {
            FileReader reader = new FileReader(in);
            BufferedReader buf = new BufferedReader(reader);
            boolean loop = true;
            String currentClass = null;
            while (loop) {
                String s = buf.readLine();
                if (s != null && !s.isEmpty()) {
                    if (s.startsWith("#")) continue;

                    if (s.startsWith(" ")) {
                        s = s.substring(4);
                        String parts[] = s.split(" ");
                        assert parts.length == 4;

                        if (parts[1].endsWith(")")) {
                            String returnType = parts[0].contains(":") ? parts[0].split(":")[2] : parts[0];
                            String obfName = parts[3];
                            String methodName = parts[1].split("\\(")[0];
                            String params = parts[1].split("\\(")[1];
                            params = params.substring(0, params.length() - 1);

                            returnType = typeToDescriptor(returnType);
                            params = Arrays.stream(params.split(",")).map(this::typeToDescriptor).collect(Collectors.joining());

                            if (!methodName.equals("<init>") && !methodName.equals("<clinit>"))
                                getClass(currentClass).addMethod(new PairedMethodMapping(methodName, obfName, returnType, params));
                        } else {
                            String fieldName = parts[1];
                            String obfName = parts[3];

                            getClass(currentClass).addField(new PairedFieldMapping(fieldName, obfName));
                        }
                    } else {
                        String parts[] = s.split(" ");
                        assert parts.length == 3;

                        currentClass = parts[0].replaceAll("\\.", "/");
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
                    if (s.startsWith("#")) continue;

                    if (s.startsWith(" "))
                        continue;
                    else {
                        String parts[] = s.split(" ");
                        assert parts.length == 3;

                        String className = parts[0].replaceAll("\\.", "/");
                        String obfName = parts[2].substring(0, parts[2].length() - 1);
                        if (obfName.contains("."))
                            obfName = obfName.replaceAll("\\.", "/");

                        classes.add(new PairedClassMapping(className, obfName));
                    }
                } else loop = false;
            }

            buf.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PairedClassMapping> getClasses() {
        return classes;
    }
}
