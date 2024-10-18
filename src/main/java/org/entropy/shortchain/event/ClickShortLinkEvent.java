package org.entropy.shortchain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClickShortLinkEvent {
    private String shortCode;
}
