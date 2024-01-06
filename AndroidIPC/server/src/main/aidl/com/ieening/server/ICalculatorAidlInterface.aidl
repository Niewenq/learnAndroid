// ICalculatorAidlInterface.aidl
package com.ieening.server;

// Declare any non-default types here with import statements

interface ICalculatorAidlInterface {
    float twoNumberAdd(float firstNumber, float secondNumber);
    
    float twoNumberSubtract(float firstNumber, float secondNumber);
    
    float twoNumberMultiply(float firstNumber, float secondNumber);
    
    float twoNumberDivide(float firstNumber, float secondNumber);
}