package org.entropy.shortchain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LinkState {
    ACTIVE("激活"), DISABLED("禁用");

    private final String label;
}
