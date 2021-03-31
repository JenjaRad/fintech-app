package com.eugene.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LimitServiceImpl implements LimitService {
    private final Map<String, Pair> map = new ConcurrentHashMap<>();
    @Value("${limit.total}")
    int total;
    @Value("${limit.period}")
    int period;

    @Override
    public boolean isLimit(String time) {
        boolean result = false;
        do {
            Pair pair = map.get(time);
            Pair temp = pair;
            if (pair == null) {
                new Pair(System.currentTimeMillis(), 1);
            } else {
                if (System.currentTimeMillis() - pair.start / 1000 > period) {
                    pair = new Pair(System.currentTimeMillis(), 1);
                } else {
                    pair = new Pair(pair.start, pair.count + 1);
                }
                result = map.putIfAbsent(time, pair) == null ? map.replace(time, temp, pair) : map.put(time, pair) != null;
            }
        }
        while (!result);
        return map.get(time).count > total;
    }
    private final class Pair{
        private long start;
        private int count;

        public Pair(long start, int count) {
            this.start = start;
            this.count = count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;

            Pair pair = (Pair) o;

            return count == pair.count;
        }

        @Override
        public int hashCode() {
            return count;
        }
    }
}


