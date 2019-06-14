// IGuardService.aidl
package com.example.guardapp;
import android.os.IBinder;

interface IGuardService {

    void attachBinder(IBinder token);
}
