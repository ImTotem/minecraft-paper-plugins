package io.github.imtotem.tracker;

import java.util.HashMap;
import java.util.Map;

public class Target {
    Map<String, String> target = new HashMap<>();

    public Map<String, String> getTarget() {
        return target;
    }

    public void injectTarget(String key, String value) {
        this.target.put(key, value);
    }
}
