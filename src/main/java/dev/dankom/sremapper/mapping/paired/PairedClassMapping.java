package dev.dankom.sremapper.mapping.paired;

import dev.dankom.sremapper.mapping.ClassMapping;
import dev.dankom.sremapper.mapping.namespaced.NamespacedClassMapping;
import dev.dankom.sremapper.mapping.namespaced.NamespacedFieldMapping;
import dev.dankom.sremapper.mapping.namespaced.NamespacedMapping;
import dev.dankom.sremapper.mapping.namespaced.NamespacedMethodMapping;

import java.util.ArrayList;
import java.util.List;

public class PairedClassMapping extends PairedMapping implements ClassMapping<PairedMapping> {
    private List<PairedMethodMapping> methods = new ArrayList<>();
    private List<PairedFieldMapping> fields = new ArrayList<>();

    public PairedClassMapping(String mappedName, String unmappedName) {
        super(mappedName, unmappedName);
    }

    public void addMethod(PairedMethodMapping mapping) {
        methods.add(mapping);
    }

    public List<PairedMethodMapping> getMethods() {
        return methods;
    }

    public void addField(PairedFieldMapping mapping) {
        fields.add(mapping);
    }

    public List<PairedFieldMapping> getFields() {
        return fields;
    }

    @Override
    public NamespacedMapping asNamespaced() {
        NamespacedClassMapping classMapping = new NamespacedClassMapping(getMappedName(), getObfName());
        for (PairedFieldMapping field : fields) {
            classMapping.addField((NamespacedFieldMapping) field.asNamespaced());
        }
        for (PairedMethodMapping method : methods) {
            classMapping.addMethod((NamespacedMethodMapping) method.asNamespaced());
        }
        return classMapping;
    }
}
