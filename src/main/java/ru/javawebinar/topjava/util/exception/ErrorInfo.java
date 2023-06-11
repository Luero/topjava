package ru.javawebinar.topjava.util.exception;

import java.util.List;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final List<String> detail;

    public ErrorInfo(CharSequence url, ErrorType type, List<String> detail) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
    }
}