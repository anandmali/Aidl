package com.anand.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.anand.aidlserver.IAidlDemoInterface;

import java.util.List;

/**
 * Aidl client main activity
 * Steps :
 * Create server connection and generate proxy if needed
 * Bind with service from aidl server client
 * Call the IAidlDemoInterface method to get job done
 */
public class MainActivity extends AppCompatActivity {

    //Aidl interface
    private IAidlDemoInterface aidlDemoInterface;

    private static final String ACTION = "com.anand.aidlserver.ANAGRAM";

    //Connect with aidl server
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("Check ", "Binder created");
            //Cast communication iBinder to IAidlDemoInterface and create proxy
            aidlDemoInterface = IAidlDemoInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBind = findViewById(R.id.btnBind);
        Button btnCheck = findViewById(R.id.btnCheck);

        btnBind.setOnClickListener(view -> bindWithService());

        btnCheck.setOnClickListener(view -> {
            if (aidlDemoInterface == null) {
                Log.e("Error ", "Bind with aidl server");
                checkAnagram();
            }
        });
    }

    //Check anagram
    private void checkAnagram() {
        int occurrence = 0;

        Log.e("Check ", aidlDemoInterface + " ");

        try {
            occurrence = aidlDemoInterface.checkAnagram("dan");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Occurrence of 'dan' = " + occurrence, Toast.LENGTH_LONG).show();
    }

    //Bind service
    private Boolean bindWithService() {
        try {
            Intent intent = new Intent(ACTION);
            bindService(createExplicitIntent(intent), serviceConnection, BIND_AUTO_CREATE);
            Log.e("Inten", intent + "");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Install aidl server app", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //Create to explicit intent
    public Intent createExplicitIntent(Intent implicitIntent) {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryIntentServices(implicitIntent, 0);
        if (resolveInfoList == null || resolveInfoList.size() != 1) {
            return null;
        }
        ResolveInfo serviceInfo = resolveInfoList.get(0);
        ComponentName component = new ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name);
        Intent explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(component);
        return explicitIntent;
    }
}
