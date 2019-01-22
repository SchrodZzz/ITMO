class AdditionalPair<FirstDataType, SecondDataType> {

    private final FirstDataType first;
    private final SecondDataType second;

    AdditionalPair(FirstDataType first, SecondDataType second) {
        this.first = first;
        this.second = second;
    }

    FirstDataType getFirstElement() {
        return first;
    }

    SecondDataType getSecondElement() {
        return second;
    }
}

//pair in javaFX 2.0 +