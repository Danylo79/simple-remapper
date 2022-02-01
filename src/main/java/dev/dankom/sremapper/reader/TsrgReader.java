package dev.dankom.sremapper.reader;

import dev.dankom.sremapper.mapping.paired.PairedClassMapping;
import dev.dankom.sremapper.mapping.paired.PairedFieldMapping;
import dev.dankom.sremapper.mapping.paired.PairedMethodMapping;
import dev.dankom.sremapper.reader.base.PairedMappingReader;
import dev.dankom.sremapper.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TsrgReader extends PairedMappingReader {
    public TsrgReader(File in) {
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

                    if (s.startsWith("\t")) {
                        s = s.replace("\t", "");
                        String parts[] = s.split(" ");
                        assert parts.length == 4;

                        if (parts[1].contains("(") && parts[1].contains(")")) {
                            String returnType = StringUtil.getAfter(parts[1], ')');
                            String obfName = parts[0];
                            String methodName = parts[2].split("\\(")[0];
                            String params = StringUtil.getBetween(parts[1], '(', ')');
                            returnType = typeToDescriptor(returnType);
                            params = Arrays.stream(params.split(",")).map(this::typeToDescriptor).collect(Collectors.joining());

                            if (!methodName.equals("<init>") && !methodName.equals("<clinit>"))
                                getClass(currentClass).addMethod(new PairedMethodMapping(methodName, obfName, returnType, params));
                        } else {
                            String fieldName = parts[0];
                            String obfName = parts[1];

                            getClass(currentClass).addField(new PairedFieldMapping(fieldName, obfName));
                        }
                    } else {
                        String parts[] = s.split(" ");
                        assert parts.length == 3;

                        currentClass = parts[1].replaceAll("\\.", "/");
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

                    if (s.startsWith("\t"))
                        continue;
                    else {
                        String parts[] = s.split(" ");
                        assert parts.length == 3;

                        String className = parts[1].replaceAll("\\.", "/");
                        String obfName = parts[0];
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

    @Override
    public String typeToDescriptor(String type) {
        if (type.endsWith("[]"))
            return "[" + typeToDescriptor(type.substring(0, type.length() - 2));
        if (type.contains("."))
            return "L" + getClassOrDefault(type.replaceAll("\\.", "/"), type.replaceAll("\\.", "/")).getObfName() + ";";

        return type;
    }
}
