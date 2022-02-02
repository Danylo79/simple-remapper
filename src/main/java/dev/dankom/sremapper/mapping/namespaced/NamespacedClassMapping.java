package dev.dankom.sremapper.mapping.namespaced;

import dev.dankom.sremapper.mapping.ClassMapping;
import dev.dankom.sremapper.mapping.paired.PairedMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NamespacedClassMapping extends NamespacedMapping implements ClassMapping<NamespacedMapping> {
    private List<NamespacedMethodMapping> methods = new ArrayList<>();
    private List<NamespacedFieldMapping> fields = new ArrayList<>();

    public NamespacedClassMapping(Map<String, String> namespace) {
        super(namespace);
    }

    public NamespacedClassMapping(String namespace, String name) {
        super(namespace, name);
    }

    public void addMethod(NamespacedMethodMapping mapping) {
        methods.add(mapping);
    }

    public List<NamespacedMethodMapping> getMethods() {
        return methods;
    }

    public void addField(NamespacedFieldMapping mapping) {
        fields.add(mapping);
    }

    public List<NamespacedFieldMapping> getFields() {
        return fields;
    }

    @Override
    public PairedMapping asPaired() {
        return null;
    }
}
