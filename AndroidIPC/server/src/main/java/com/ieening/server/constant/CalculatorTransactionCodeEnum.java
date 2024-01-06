package com.ieening.server.constant;


public enum CalculatorTransactionCodeEnum {
    ADD(0x110),
    SUBTRACT(0x111),
    MULTIPLY(0x1000),
    DIVIDE(0x1001);


    public int getCode() {
        return code;
    }

    private final int code;

    CalculatorTransactionCodeEnum(int code) {
        this.code = code;
    }

    public static CalculatorTransactionCodeEnum getCalculatorTransactionCodeEnumByCode(int code) {
        for (CalculatorTransactionCodeEnum calculatorTransactionCodeEnum :
                CalculatorTransactionCodeEnum.values()) {
            if (code == calculatorTransactionCodeEnum.code) {
                return calculatorTransactionCodeEnum;
            }
        }
        return null;
    }
}

