package dev.dankom.sremapper.remapper;

import dev.dankom.logger.interfaces.ILogger;

import java.io.File;

public abstract class Remapper {
    protected final ILogger logger;
    protected final File mappingsIn;

    public Remapper(ILogger logger, File mappingsIn) {
        this.logger = logger;
        this.mappingsIn = mappingsIn;
    }

    public abstract void remap(File in, File out);
}
