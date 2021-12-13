package org.optaplanner.solutions.pas.shared;

import java.math.BigDecimal;

public class ScoreCalculator {

    private static BigDecimal _oneHundredValue = new BigDecimal(100);
    
    public static int calculate(BigDecimal param1, BigDecimal param2) {
        return _calculate(param1, param2);
    }

    public static int calculate(BigDecimal param1, int param2) {
        BigDecimal param2Converted = new BigDecimal(param2);
        return _calculate(param1, param2Converted);
    }

    public static int calculate(int param1, BigDecimal param2) {
        BigDecimal param1Converted = new BigDecimal(param1);
        return _calculate(param1Converted, param2);
    }

    public static int calculate(int param1, double param2) {
        BigDecimal param1Converted = new BigDecimal(param1);
        BigDecimal param2Converted = new BigDecimal(param2);
        return _calculate(param1Converted, param2Converted);
    }

    public static int calculate(double param1, int param2) {
        BigDecimal param1Converted = new BigDecimal(param1);
        BigDecimal param2Converted = new BigDecimal(param2);
        return _calculate(param1Converted, param2Converted);
    }

    public static int calculate(double param1, double param2) {
        BigDecimal param1Converted = new BigDecimal(param1);
        BigDecimal param2Converted = new BigDecimal(param2);
        return param1Converted
            .subtract(param2Converted)
            .intValue();
    }

    private static int _calculate(BigDecimal param1, BigDecimal param2) {        
        return param1
            .subtract(param2)
            .multiply(_oneHundredValue)
            .intValue();
    }

}
