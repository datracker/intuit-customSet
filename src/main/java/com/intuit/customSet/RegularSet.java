package com.intuit.customSet;

import java.util.HashSet;
import java.util.Set;

public class RegularSet {
    private Set<Integer> set = new HashSet<>();

    public RegularSet() {
    }

    public void add(int item) {
        set.add(item);
    }

    public void remove(int item) {
        set.remove(item);
    }

    public boolean contains(int item) {
        return set.contains(item);
    }
}
