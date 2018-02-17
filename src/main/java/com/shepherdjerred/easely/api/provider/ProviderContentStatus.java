package com.shepherdjerred.easely.api.provider;

import lombok.Getter;

public class ProviderContentStatus<T> {
    @Getter
    private boolean isLoaded;
    @Getter
    private T content;
}
