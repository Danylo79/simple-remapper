package dev.dankom.sremapper.mapping;

import dev.dankom.sremapper.mapping.namespaced.NamespacedMapping;
import dev.dankom.sremapper.mapping.paired.PairedMapping;

public interface Mapping {
    boolean isPaired();
    boolean isNamespaced();

    PairedMapping asPaired();
    NamespacedMapping asNamespaced();

    void reverse();
}
