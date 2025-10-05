package com.msp.openmsp_kit.model.domain.task;

import com.msp.openmsp_kit.model.domain.common.EndPoint;
import com.msp.openmsp_kit.model.domain.common.Resource;
import com.msp.openmsp_kit.model.domain.common.Source;

import java.util.Set;

public record Task (
        String id,
        Source source,
        Resource resource,
        Set<EndPoint> endpoints
) {}
