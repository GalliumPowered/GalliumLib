package org.galliumpowered.api.config;

import java.nio.file.Path;

public interface Configuration {
    Path getDirectory();

    String getName();

    void setValue(String key, String value);

    String getValue(String key);
}
