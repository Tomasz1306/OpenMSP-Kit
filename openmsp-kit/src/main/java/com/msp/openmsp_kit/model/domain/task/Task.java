package com.msp.openmsp_kit.model.task;

import com.msp.openmsp_kit.model.common.EndPoint;
import com.msp.openmsp_kit.model.common.Resource;
import com.msp.openmsp_kit.model.common.Source;

import java.util.Set;

public record Task (
        String id,
        Source source,
        Resource resource,
        Set<EndPoint> endpoints
) {}
