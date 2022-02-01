package dev.dankom.sremapper.decompiler;

import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.logger.type.LogLevel;
import org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FernFlowerDecompiler implements IDecompiler {
    private final ILogger logger;

    public FernFlowerDecompiler(ILogger logger) {
        this.logger = logger;
    }

    @Override
    public void decompile(File in, File out) {
        Map<String, Object> options = new HashMap<>();
        options.put("log", "TRACE");
        options.put("dgs", "1");
        options.put("asc", "1");
        options.put("rsy", "1");
        FernConsoleDecompiler decompiler = new FernConsoleDecompiler(out, options, new IFernflowerLogger() {
            @Override
            public void writeMessage(String s, Severity severity) {
                LogLevel level = null;
                if (severity == Severity.INFO) {
                    level = LogLevel.INFO;
                } else if (severity == Severity.WARN) {
                    level = LogLevel.WARNING;
                } else if (severity == Severity.TRACE) {
                    level = LogLevel.DEBUG;
                } else if (severity == Severity.ERROR) {
                    level = LogLevel.ERROR;
                }
                logger.log(level, "FernDecompiler", s);
            }

            @Override
            public void writeMessage(String s, Severity severity, Throwable throwable) {
                writeMessage(s, severity);
                throwable.printStackTrace();
            }
        });

        decompiler.addSource(in);
        decompiler.decompileContext();
    }

    public class FernConsoleDecompiler extends ConsoleDecompiler {
        public FernConsoleDecompiler(File destination, Map<String, Object> options, IFernflowerLogger logger) {
            super(destination, options, logger);
        }
    }
}
