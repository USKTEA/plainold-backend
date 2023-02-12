package com.usktea.plainold.sessions;

import com.usktea.plainold.exceptions.TidNotFound;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TidSession {
    private final Map<Integer, String> tids = new HashMap<>();

    public TidSession() {
    }

    public Integer save(String tid) {
        Integer id = generateId();

        tids.put(id, tid);

        return id;
    }

    public String getTid(Integer tidId) {
        String tid = tids.get(tidId);

        if (Objects.isNull(tid)) {
            throw new TidNotFound();
        }

        return tid;
    }

    private Integer generateId() {
        return tids.size() + 1;
    }
}
