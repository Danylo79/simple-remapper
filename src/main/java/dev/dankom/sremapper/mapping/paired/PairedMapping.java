package dev.dankom.sremapper.mapping.paired;

import dev.dankom.sremapper.mapping.Mapping;

public abstract class PairedMapping implements Mapping {
    private String mappedName;
    private String obfName;

    public PairedMapping(String mappedName, String obfName) {
        this.mappedName = mappedName;
        this.obfName = obfName;
    }

    public String getMappedName() {
        return mappedName;
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    public String getObfName() {
        return obfName;
    }

    public void setObfName(String obfName) {
        this.obfName = obfName;
    }

    @Override
    public boolean isPaired() {
        return true;
    }

    @Override
    public boolean isNamespaced() {
        return false;
    }

    @Override
    public PairedMapping asPaired() {
        return this;
    }

    public void reverse() {
        String temp = mappedName;
        setMappedName(obfName);
        setObfName(temp);
    }

    @Override
    public String toString() {
        return "PairedMapping{" +
                "mappedName='" + mappedName + '\'' +
                ", obfName='" + obfName + '\'' +
                '}';
    }
}
