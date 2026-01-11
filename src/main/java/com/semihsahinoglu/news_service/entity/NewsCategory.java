package com.semihsahinoglu.news_service.entity;

public enum NewsCategory {
    SUPER_LIG("Süper Lig"),
    PREMIER_LEAGUE("Premier League"),
    BUNDESLIGA("Bundesliga"),
    SERIE_A("Serie A"),
    LA_LIGA("La Liga"),
    LIGUE_1("Ligue 1"),
    TRANSFER("Transfer"),
    AVRUPA("Avrupa"),
    AVRUPA_KUPALARI("Avrupa Kupaları"),
    DUNYA_FUTBOLU("Dünya Futbolu"),
    MILLI_TAKIM("Milli Takım"),
    HAKEM("Hakem"),
    KULUPLER("Kulüpler"),
    GENEL("Genel");

    private String category;

    NewsCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }
}
