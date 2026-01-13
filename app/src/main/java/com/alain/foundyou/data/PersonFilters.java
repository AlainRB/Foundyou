package com.alain.foundyou.data;

import java.util.HashMap;
import java.util.Map;

public class PersonFilters {
    private Integer results;
    private String gender;
    private String seed;

    // Getters y Setters o un Builder
    public PersonFilters setResults(int results) { this.results = results; return this; }
    public PersonFilters setGender(String gender) { this.gender = gender; return this; }
    public PersonFilters setSeed(String seed) { this.seed = seed; return this; }

    // Método clave: Convierte solo lo que existe a un Mapa para Retrofit
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (results != null) map.put("results", String.valueOf(results));
        if (gender != null) map.put("gender", gender);
        if (seed != null) map.put("seed", seed);
        return map;
    }
}
