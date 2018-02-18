package com.shepherdjerred.easely.api.easel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class AdapterContent<T> {
    @Getter
    @Setter
    private boolean isLoaded;
    @Getter
    private T content;
    @Getter
    @Setter
    private String statusMessage;
    @Getter
    @Setter
    private boolean indeterminate;
    @Getter
    @Setter
    private int currentValue;
    @Getter
    @Setter
    private int maxValue;
}
