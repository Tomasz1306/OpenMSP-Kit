package com.msp.openmsp_kit.model.mapper;

public interface ToEntityFromDomainMapper<T, K> {
    T toEntityFromDomain(K domain);
}
