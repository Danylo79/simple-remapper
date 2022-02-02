package dev.dankom.sremapper.mapping.namespaced;

import dev.dankom.sremapper.mapping.Mapping;

import java.util.HashMap;
import java.util.Map;

public abstract class NamespacedMapping implements Mapping {
    // Forge
    public static final String OBF = "obf";
    public static final String SRG = "srg";

    // Fabric
    public static final String OFFICIAL = "official";
    public static final String INTERMEDIARY = "intermediary";
    public static final String YARN = "named";

    public final Map<String, String> namespace = new HashMap<>();

    public NamespacedMapping(Map<String, String> namespace) {
        this.namespace.putAll(namespace);
    }

    public NamespacedMapping(String namespace, String name) {
        this.namespace.put(namespace, name);
    }

    public void setName(String namespace, String name) {
        this.namespace.put(namespace, name);
    }

    public String getName(String namespace) {
        return this.namespace.get(namespace);
    }

    @Override
    public boolean isPaired() {
        return false;
    }

    @Override
    public boolean isNamespaced() {
        return true;
    }

    @Override
    public NamespacedMapping asNamespaced() {
        return this;
    }

    @Override
    public void reverse() {
        for (Map.Entry<String, String> me : namespace.entrySet()) {
            namespace.put(me.getKey(), me.getValue());
            namespace.put(me.getValue(), me.getKey());
        }
    }

    @Override
    public String toString() {
        return "NamespacedMapping{" +
                "namespace=" + namespace +
                '}';
    }
}
