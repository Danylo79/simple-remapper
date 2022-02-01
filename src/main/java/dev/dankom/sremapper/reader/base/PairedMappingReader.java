package dev.dankom.sremapper.reader.base;

import dev.dankom.sremapper.mapping.paired.PairedClassMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class PairedMappingReader extends MappingReader {
    protected List<PairedClassMapping> classes = new ArrayList<>();

    public PairedMappingReader(File in) {
        super(in);
    }

    public abstract void findClasses();

    public PairedClassMapping getClass(String className) {
        for (PairedClassMapping mapping : classes) {
            if (mapping.getMappedName().equals(className)) {
                return mapping;
            }
        }
        return null;
    }

    public PairedClassMapping getClassOrDefault(String className, String def) {
        for (PairedClassMapping mapping : classes) {
            if (mapping.getMappedName().equals(className)) {
                return mapping;
            }
        }

        PairedClassMapping c = new PairedClassMapping(className, def);
        classes.add(c);
        return c;
    }

    public String typeToDescriptor(String type) {
        if (type.endsWith("[]"))
            return "[" + typeToDescriptor(type.substring(0, type.length() - 2));
        if (type.contains("."))
            return "L" + getClassOrDefault(type.replaceAll("\\.", "/"), type.replaceAll("\\.", "/")).getObfName() + ";";

        switch (type) {
            case "void":
                return "V";
            case "int":
                return "I";
            case "float":
                return "F";
            case "char":
                return "C";
            case "byte":
                return "B";
            case "boolean":
                return "Z";
            case "double":
                return "D";
            case "long":
                return "J";
            case "short":
                return "S";
            default:
                return "";
        }
    }
}
