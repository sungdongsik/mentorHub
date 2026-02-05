package com.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public abstract class VectorIdUtil {
    public static String createId(Long writingId) {
        return UUID.nameUUIDFromBytes(
                ("mentee-" + writingId).getBytes(StandardCharsets.UTF_8)
        ).toString();
    }
}
