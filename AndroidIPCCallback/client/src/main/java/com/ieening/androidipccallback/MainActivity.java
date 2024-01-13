package com.ieening.androidipccallback;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ieening.androidipccallback.databinding.ActivityMainBinding;
import com.ieening.server.IStudentInfo;
import com.ieening.server.ScoreChangedCallback;
import com.ieening.server.Student;
import com.ieening.server.StudentService;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private IStudentInfo studentServiceRemoteBinder = null;

    Handler studentInfoHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Student student = (Student) Objects.requireNonNull(msg.getData().getParcelable("student"));
                binding.studentInfoAutoUpdateEditText.setText(student.toString());
            }
        }
    };
    ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            studentServiceRemoteBinder = IStudentInfo.Stub.asInterface(service);
            try {
                studentServiceRemoteBinder.register(new ScoreChangedCallback.Stub() {
                    @Override
                    public void onCallback(Student student) {
                        //在子线程中创建一个消息对象
                        Message studentInfoMessage = new Message();
                        studentInfoMessage.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("student", student);
                        studentInfoMessage.setData(bundle);
                        //将该消息放入主线程的消息队列中
                        studentInfoHandler.sendMessage(studentInfoMessage);
                    }

                });
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            studentServiceRemoteBinder = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setBindUnbindButtonOnClickListener();

        binding.getStudentInfoButton.setOnClickListener(v -> {
            try {
                binding.studentInfoEditText.setText(studentServiceRemoteBinder.getStudentInfo().toString());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setBindUnbindButtonOnClickListener() {
        binding.bindStudentServiceButton.setOnClickListener(v -> {
            Intent bindIntent = new Intent(this, StudentService.class);
            boolean bindResult = bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);
            if (bindResult) {
                binding.bindStudentServiceButton.setEnabled(false);
                binding.unbindStudentServiceButton.setEnabled(true);
                binding.getStudentInfoButton.setEnabled(true);
                Toast.makeText(this, "bind student service success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "bind student service failed", Toast.LENGTH_SHORT).show();
            }

        });
        binding.unbindStudentServiceButton.setOnClickListener(v -> {
            unbindService(serviceConnection);
            binding.bindStudentServiceButton.setEnabled(true);
            binding.unbindStudentServiceButton.setEnabled(false);
            binding.getStudentInfoButton.setEnabled(false);
        });
    }

    @Override
    protected void onDestroy() {
        if (!Objects.isNull(studentServiceRemoteBinder) && studentServiceRemoteBinder.asBinder().isBinderAlive()) {
            unbindService(serviceConnection);
        }
        super.onDestroy();
    }
}