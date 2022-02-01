package dev.dankom.sremapper.decompiler;

import java.io.File;

public interface IDecompiler {
    void decompile(File in, File out);
}
