package dev.dankom.sremapper.mapping.namespaced;

import dev.dankom.sremapper.mapping.paired.PairedMapping;

import java.util.Map;

public class NamespacedFieldMapping extends NamespacedMapping {
    public NamespacedFieldMapping(Map<String, String> namespace) {
        super(namespace);
    }

    public NamespacedFieldMapping(String namespace, String name) {
        super(namespace, name);
    }

    @Override
    public PairedMapping asPaired() {
        return null;
    }
}
