package com.dozen.hangman.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
/**
 * 
 * @author deniz.ozen
 *
 */
public enum GameStatus {
	ongoing, won, lost;
	
    private static Map<String, GameStatus> namesMap = new HashMap<String, GameStatus>(3);

    static {
        namesMap.put("ongoing", ongoing);
        namesMap.put("won", won);
        namesMap.put("lost", lost);
    }

    @JsonCreator
    public static GameStatus forValue(String value) {
        return namesMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Entry<String, GameStatus> entry : namesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }
}
