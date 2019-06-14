package com.example.guardapp;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class GuardService extends Service {

  private static final String TAG = "GuardService";
  private static final String MAIN_APP_PACKAGE = "com.example.mainapplciation";
  private static final String MAIN_MAIN_ACTIVITY = "com.example.mainapplciation.MainActivity";

  private IGuardService.Stub mStub = new IGuardService.Stub() {
    @Override
    public void attachBinder(IBinder token) throws RemoteException {
      Log.d(TAG, "attachBinder: ");
      token.linkToDeath(recipient, 0);
    }
  };

  private IBinder.DeathRecipient recipient = new IBinder.DeathRecipient() {
    @Override
    public void binderDied() {
      Log.d(TAG, "binderDied: ");
      restartMainApp();
    }
  };

  private void restartMainApp() {
    Log.d(TAG, "restartMainApp: ");
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_LAUNCHER);
    ComponentName cn = new ComponentName(MAIN_APP_PACKAGE, MAIN_MAIN_ACTIVITY);
    intent.setComponent(cn);

    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return mStub;
  }
}
