package com.example.fp_predictor.analysis.xg_difference;

/**
 * Экземпляр данных, необходимых для анализа зависимости между итоговыми позициями команд и расхождением между
 * ожидаемыми и фактическими голами.
 *
 * Извлекается из строки считываемого файла.
 */
public class DataInstance {

    /** Итоговая позиция команды по окончании сезона. */
    private int position;

    /** Разница между ожидаемым и фактическим количеством голов. */
    private double xG_difference;

    /** Количество голов. */
    private int numberOfGoals;

    public DataInstance(int position, double xG_difference, int numberOfGoals) {
        this.position = position;
        this.xG_difference = xG_difference;
        this.numberOfGoals = numberOfGoals;
    }

    @Override
    public String toString() {
        return "StatsInstance{" +
                "position=" + position +
                ", xG_difference=" + xG_difference +
                '}';
    }

    public int getPosition() {
        return position;
    }

    public double get_xG_difference() {
        return xG_difference;
    }

    public int getNumberOfGoals() {
        return numberOfGoals;
    }
}
