package com.example.fp_predictor.optimization.knapsack.dynamic;

import com.example.fp_predictor.optimization.knapsack.FantasyTeam;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Обертка над структурой данных HashMap<MapKey, FantasyTeam>.
 */
public class MatrixElement {

    /** Мапа, хранящая информацию о командах по различным ключам. */
    private Map<MapKey, FantasyTeam> matrixElement = new HashMap<>();

    /**
     * Проверка на наличие ключа в мапе.
     * @param key - проверяемый ключ.
     * @return - TRUE, если ключ содержится в мапе; FALSE, если не содержится.
     */
    public boolean containsKey(MapKey key) {
        return matrixElement.containsKey(key);
    }

    public void put(MapKey key, FantasyTeam value) {
        matrixElement.put(key, value);
    }

    public void combine(MatrixElement matrixElement) {
        for (MapKey key : matrixElement.getKeySet()) {
            if (!this.matrixElement.containsKey(key) || newTeamBetter(matrixElement, key)) {
                this.matrixElement.put(key, matrixElement.get(key));
            }
        }
    }

    private boolean newTeamBetter(MatrixElement matrixElement, MapKey key) {
        return this.matrixElement.containsKey(key)
                && matrixElement.getKeyTeamExpectedPoints(key) > this.getKeyTeamExpectedPoints(key);
    }

    public double getKeyTeamExpectedPoints(MapKey key) {
        return matrixElement.get(key).getExpectedPoints();
    }

    public Set<MapKey> getKeySet() {
        Set<MapKey> keys = new HashSet<>(matrixElement.keySet());
        return keys;
    }

    public FantasyTeam get(MapKey key) {
        return matrixElement.get(key);
    }
}
