package dev.dankom.sremapper.mapping.paired;

import dev.dankom.sremapper.mapping.namespaced.NamespacedMapping;
import dev.dankom.sremapper.mapping.namespaced.NamespacedMethodMapping;

public class PairedMethodMapping extends PairedMapping {
    private final String returnType;
    private final String params;

    public PairedMethodMapping(String mappedName, String unmappedName, String returnType, String params) {
        super(mappedName, unmappedName);
        this.returnType = returnType;
        this.params = params;
    }

    @Override
    public NamespacedMapping asNamespaced() {
        return new NamespacedMethodMapping(getMappedName(), getObfName(), returnType, params);
    }

    public String getReturnType() {
        return returnType;
    }

    public String getParams() {
        return params;
    }
}
