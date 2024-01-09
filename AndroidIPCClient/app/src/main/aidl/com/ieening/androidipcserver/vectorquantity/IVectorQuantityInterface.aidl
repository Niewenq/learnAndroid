// IVectorQuantityInterface.aidl
package com.ieening.androidipcserver.vectorquantity;

// Declare any non-default types here with import statements
import com.ieening.androidipcserver.vectorquantity.VectorQuantity;

interface IVectorQuantityInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void updateVectorQuantityIn(in VectorQuantity vectorQuantity);
    void updateVectorQuantityOut(out VectorQuantity vectorQuantity);
    void updateVectorQuantityInOut(inout VectorQuantity vectorQuantity);
}