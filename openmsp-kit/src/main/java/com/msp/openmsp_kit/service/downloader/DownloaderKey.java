package com.msp.openmsp_kit.service.downloader;

import com.msp.openmsp_kit.model.domain.common.EndPoint;
import com.msp.openmsp_kit.model.domain.common.Resource;
import com.msp.openmsp_kit.model.domain.common.Source;

import java.util.Objects;

public class DownloaderKey {
    private final Source source;
    private final Resource resource;
    private final EndPoint endPoint;

    public DownloaderKey(Source source, Resource resource, EndPoint endPoint) {
        this.source = source;
        this.resource = resource;
        this.endPoint = endPoint;
    }

    @Override
    public boolean equals(Object obj) {
        return this.source == ((DownloaderKey) obj).source &&
                this.resource == ((DownloaderKey) obj).resource &&
                this.endPoint == ((DownloaderKey) obj).endPoint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, resource, endPoint);
    }
}
