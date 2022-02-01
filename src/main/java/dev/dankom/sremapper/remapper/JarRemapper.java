package dev.dankom.sremapper.remapper;

import dev.dankom.logger.interfaces.ILogger;
import net.md_5.specialsource.Jar;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static dev.dankom.sremapper.util.MapUtil.createJarMapping;
import static dev.dankom.sremapper.util.MapUtil.createJarRemapper;

public class JarRemapper extends Remapper {
    public JarRemapper(ILogger logger, File mappingsIn) {
        super(logger, mappingsIn);
    }

    @Override
    public void remap(File in, File out) {
        try {
            logger.info("Remapper", "Remapping jar");
            FileUtils.copyFile(in, out);
            createJarRemapper(createJarMapping(mappingsIn)).remapJar(Jar.init(in), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
