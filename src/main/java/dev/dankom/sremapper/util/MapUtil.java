package dev.dankom.sremapper.util;

import net.md_5.specialsource.JarMapping;
import net.md_5.specialsource.JarRemapper;

import java.io.File;

public class MapUtil {
    public static JarRemapper createJarRemapper(JarMapping mapping) {
        return new JarRemapper(null, mapping, null);
    }

    public static JarMapping createJarMapping(File mappings) {
        JarMapping mapping = new JarMapping();
        try {
            mapping.loadMappings(mappings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping;
    }
}
