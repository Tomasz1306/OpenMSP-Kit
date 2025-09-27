package com.msp.openmsp_kit.service.downloader;

public interface Downloader<T, K> {
    T fetch(K key);
}
