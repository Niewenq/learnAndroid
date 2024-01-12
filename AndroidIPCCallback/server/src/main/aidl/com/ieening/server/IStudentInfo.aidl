// IStudentInfo.aidl
package com.ieening.server;

// Declare any non-default types here with import statements
import com.ieening.server.Student;
import com.ieening.server.ScoreChangedCallback;

interface IStudentInfo {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    // 主动获取信息
    Student getStudentInfo();
    // 注册回调函数
    oneway void register(in ScoreChangedCallback callback);
}