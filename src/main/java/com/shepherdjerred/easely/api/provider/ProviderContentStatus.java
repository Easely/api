package com.shepherdjerred.easely.api.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProviderContentStatus {
    @Getter
    public UUID queueUuid;
    @Getter
    public Object data;
    @Getter
    public boolean isLoaded;
}
