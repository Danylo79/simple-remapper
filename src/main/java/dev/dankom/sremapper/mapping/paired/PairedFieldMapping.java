package dev.dankom.sremapper.mapping.paired;

import dev.dankom.sremapper.mapping.namespaced.NamespacedFieldMapping;
import dev.dankom.sremapper.mapping.namespaced.NamespacedMapping;

public class PairedFieldMapping extends PairedMapping {
    public PairedFieldMapping(String mappedName, String unmappedName) {
        super(mappedName, unmappedName);
    }

    @Override
    public NamespacedMapping asNamespaced() {
        return new NamespacedFieldMapping(getMappedName(), getObfName());
    }
}
