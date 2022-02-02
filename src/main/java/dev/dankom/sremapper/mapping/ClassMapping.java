package dev.dankom.sremapper.mapping;

import java.util.List;

public interface ClassMapping<T extends Mapping> {
    List<? extends T> getFields();
    List<? extends T> getMethods();
}
