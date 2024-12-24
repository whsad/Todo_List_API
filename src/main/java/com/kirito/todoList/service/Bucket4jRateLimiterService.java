package com.kirito.todoList.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Bucket4jRateLimiterService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean isRequestAllowed(String clientKey) {
        Bucket bucket = buckets.computeIfAbsent(clientKey, this::createNewBucket);
        return bucket.tryConsume(1);
    }

    private Bucket createNewBucket(String clientKey) {
        Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1))); // 每分钟允许 10 次请求
        return Bucket.builder().addLimit(limit).build();
    }
}
