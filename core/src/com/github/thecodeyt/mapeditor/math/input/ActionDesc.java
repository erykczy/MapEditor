package com.github.thecodeyt.mapeditor.math.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ActionDesc {
    public List<Branch> branches = new ArrayList<>();

    public ActionDesc(Branch... branches) {
        this.branches.addAll(Arrays.asList(branches));
    }
    public boolean hasBranch(Branch... branches) {
        return new HashSet<>(this.branches).containsAll(Arrays.asList(branches));
    }
}
