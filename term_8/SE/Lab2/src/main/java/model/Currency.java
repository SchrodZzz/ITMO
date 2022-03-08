package model;

public enum Currency {
    USD,
    EUR,
    RUB;

    private double getMultiplier() {
        switch (this) {
            case USD:
                return 1;
            case EUR:
                return 0.88;
            case RUB:
                return 77.16;
            default:
                return 1;
        }
    }

    public double getMultiplier(Currency otherCurrency) {
        return otherCurrency.getMultiplier() / getMultiplier();
    }
}
