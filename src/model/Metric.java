package model;

public class Metric {

    private String name;
    private int coefficient;
    private boolean higherIsBetter;
    private double rangeMin;
    private double rangeMax;
    private String unit;
    private double rawValue;

    public Metric(String name, int coefficient, boolean higherIsBetter,
                  double rangeMin, double rangeMax, String unit) {
        this.name = name;
        this.coefficient = coefficient;
        this.higherIsBetter = higherIsBetter;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.unit = unit;
        this.rawValue = -1;
    }

    public double calculateScore() {
        if (rawValue < 0) return 1.0;
        double range = rangeMax - rangeMin;
        if (range == 0) return 1.0;

        double score;
        if (higherIsBetter) {
            score = 1.0 + (rawValue - rangeMin) / range * 4.0;
        } else {
            score = 5.0 - (rawValue - rangeMin) / range * 4.0;
        }

        score = Math.max(1.0, Math.min(5.0, score));
        return Math.round(score * 2.0) / 2.0;
    }

    public String getName() { return name; }
    public int getCoefficient() { return coefficient; }
    public boolean isHigherIsBetter() { return higherIsBetter; }
    public double getRangeMin() { return rangeMin; }
    public double getRangeMax() { return rangeMax; }
    public String getUnit() { return unit; }
    public double getRawValue() { return rawValue; }
    public void setRawValue(double rawValue) { this.rawValue = rawValue; }

    public String getDirectionLabel() {
        return higherIsBetter ? "Higher ↑" : "Lower ↓";
    }

    public String getRangeLabel() {
        return (int) rangeMin + "–" + (int) rangeMax;
    }
}