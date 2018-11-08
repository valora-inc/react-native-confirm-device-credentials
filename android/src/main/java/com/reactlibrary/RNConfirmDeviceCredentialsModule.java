
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNConfirmDeviceCredentialsModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNConfirmDeviceCredentialsModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNConfirmDeviceCredentials";
  }

  @ReactMethod
  public boolean isDeviceSecure() {
      KeyguardManager KeyguardManager = getReactApplicationContext().getSystemService(
        Context.KEYGUARD_SERVICE);
        return KeyguardManager.isDeviceSecure();
  }
}