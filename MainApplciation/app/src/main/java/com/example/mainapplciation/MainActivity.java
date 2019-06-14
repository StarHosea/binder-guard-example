package com.example.mainapplciation;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guardapp.IGuardService;

public class MainActivity extends AppCompatActivity {

  private static final String GUARD_APP_PACKAGE = "com.example.guardapp";
  private static final String GUARD_SERVICE_ACTION = "com.example.guard.GUARD";
  private Binder mBinder = new Binder();
  private ServiceConnection mConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      IGuardService guardService = IGuardService.Stub.asInterface(service);
      try {
        guardService.attachBinder(mBinder);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    bindGuardService();
  }

  private void bindGuardService() {
    Intent intent = new Intent();
    intent.setPackage(GUARD_APP_PACKAGE);
    intent.setAction(GUARD_SERVICE_ACTION);
    bindService(intent, mConnection, BIND_AUTO_CREATE);
  }
}
