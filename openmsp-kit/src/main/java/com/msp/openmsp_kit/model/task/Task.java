package com.msp.openmsp_kit.model.task;

import com.msp.openmsp_kit.model.EndPoint;
import com.msp.openmsp_kit.model.Resource;
import com.msp.openmsp_kit.model.Source;

import java.util.Set;

public record Task (
        String id,
        Source source,
        Resource resource,
        Set<EndPoint> endpoints
) {}
