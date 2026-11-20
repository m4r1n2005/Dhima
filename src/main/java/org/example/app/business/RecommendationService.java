package org.example.app.business;

import org.example.app.domain.Media;

import java.util.List;

public interface RecommendationService {
    List<Media> getRecommendations(int userId, String type);
}
