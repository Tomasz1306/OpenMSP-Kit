package com.msp.openmsp_kit.service.rateLimiter.impl;

import com.msp.openmsp_kit.service.rateLimiter.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TMDBFilesRateLimiterImpl implements RateLimiter {
    private AtomicInteger availableTokens = new AtomicInteger(50);
    private AtomicLong lastRefillTime = new AtomicLong(System.currentTimeMillis());
    private final int maxTokens = 50;
    private final long refillInterval = 1000;


    @Override
    public synchronized void acquire() {
        while (availableTokens.get() <= 0) {
            refillTokens();
            if (availableTokens.get() <= 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }
        availableTokens.decrementAndGet();
    }

    private void refillTokens() {
        long now = System.currentTimeMillis();
        if (now - lastRefillTime.get() > refillInterval) {
            lastRefillTime.set(now);
            availableTokens.set(maxTokens);
        }
    }
}
