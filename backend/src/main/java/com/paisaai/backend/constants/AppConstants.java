package com.paisaai.backend.constants;

public final class AppConstants {

    private AppConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String DEFAULT_CURRENCY = "USD";

    public static final String API_VERSION = "/api/v1";

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String DEFAULT_TIMEZONE = "UTC";
}
