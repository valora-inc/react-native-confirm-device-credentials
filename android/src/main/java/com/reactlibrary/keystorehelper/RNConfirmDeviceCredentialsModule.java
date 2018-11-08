package com.reactlibrary;

import android.widget.Toast;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;
import java.util.HashMap;

public class RNConfirmDeviceCredentialsModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext mReactContext;

    public RNConfirmDeviceCredentialsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNConfirmDeviceCredentials";
    }

  
}