package com.msp.openmsp_kit.model.mapper;

public interface ToDomainFromApiMapper<T, K> {
    T toDomainFromApi(K response);
}
