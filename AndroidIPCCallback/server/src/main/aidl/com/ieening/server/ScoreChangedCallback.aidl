// ScoreChangedCallback.aidl
package com.ieening.server;

// Declare any non-default types here with import statements
import com.ieening.server.Student;

interface ScoreChangedCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    oneway void onCallback(in Student student);
}