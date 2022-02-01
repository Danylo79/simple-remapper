package dev.dankom.sremapper.mapping.namespaced;

import dev.dankom.sremapper.mapping.paired.PairedMapping;

import java.util.Map;

public class NamespacedMethodMapping extends NamespacedMapping {
    private final String returnType;
    private final String params;

    public NamespacedMethodMapping(Map<String, String> namespace, String returnType, String params) {
        super(namespace);
        this.returnType = returnType;
        this.params = params;
    }

    public NamespacedMethodMapping(String namespace, String name, String returnType, String params) {
        super(namespace, name);
        this.returnType = returnType;
        this.params = params;
    }

    @Override
    public PairedMapping asPaired() {
        return null;
    }
}
