package com.ieening.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Objects;

public class StudentService extends Service {
    private Student student;
    private ScoreChangedCallback remoteCallback;
    private boolean serviceThreadStopFlag = false;

    public StudentService() {
    }

    private final IStudentInfo.Stub studentInfoBinder = new IStudentInfo.Stub() {
        @Override
        public Student getStudentInfo() {
            return student;
        }

        @Override
        public void register(ScoreChangedCallback callback) {
            remoteCallback = callback;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        student = new Student("ieening", 28);
        new Thread(() -> {
            while (!serviceThreadStopFlag) {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                student.setScore((float) (Math.random() * 100));
                try {
                    if (!Objects.isNull(remoteCallback)) {
                        remoteCallback.onCallback(student);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        serviceThreadStopFlag = true;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return studentInfoBinder;
    }
}