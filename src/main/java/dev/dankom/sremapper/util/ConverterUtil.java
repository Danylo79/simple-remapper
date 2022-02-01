package dev.dankom.sremapper.util;

import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.sremapper.reader.McpReader;
import dev.dankom.sremapper.writer.TsrgWriter;

import java.io.File;

public class ConverterUtil {
    public static File convertMojang2TSRG(ILogger logger, File in, File out) {
        logger.debug("Converter", "Converting mcp mappings to tsrg");
        McpReader reader = new McpReader(in);
        reader.read();

        TsrgWriter writer = new TsrgWriter(reader.getClasses(), out);
        writer.write();
        return out;
    }
}