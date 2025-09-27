package com.msp.openmsp_kit.service.rateLimiter.impl;

import com.msp.openmsp_kit.service.rateLimiter.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterImpl implements RateLimiter {
    private int availableTokens = 50;
    private long lastRefillTime = System.currentTimeMillis();
    private final int maxTokens = 50;
    private final long refillInterval = 1000;

    @Override
    public void acquire() {
        while (availableTokens <= 0) {
            refillTokens();
            if (availableTokens <= 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }
        availableTokens--;
    }

    private void refillTokens() {
        long now = System.currentTimeMillis();
        if (now - lastRefillTime > refillInterval) {
            lastRefillTime = now;
            availableTokens = maxTokens;
        }
    }
}
