package com.semihsahinoglu.news_service.dto;

import java.util.Optional;

public record NewsFilterRequest(
        Optional<String> leagueName
) {
}
