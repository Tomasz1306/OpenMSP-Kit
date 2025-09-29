package com.msp.openmsp_kit.model.mapper;

public interface ToEntityFromApiMapper<T, K> {
    T toEntityFromApi(K domain);
}
