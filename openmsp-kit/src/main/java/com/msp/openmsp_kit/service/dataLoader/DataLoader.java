package com.msp.openmsp_kit.service.dataLoader;

import java.util.List;

public interface DataLoader<T> {
    List<T> loadData();
}
