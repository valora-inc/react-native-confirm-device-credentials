
package org.celo.rncdc;


import android.app.Activity;
import android.content.Intent;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import java.security.UnrecoverableKeyException;


public class RNConfirmDeviceCredentialsModule extends ReactContextBaseJavaModule {

    private static final String TAG = "RNonfirmDeviceCredentialsModule";
    private static final String DEVICE_SECURE_ERROR = "DEVICE_SECURE_ERROR";
    private static final String MAKE_DEVICE_SECURE_ERROR = "MAKE_DEVICE_SECURE_ERROR";
    private static final String KEYSTORE_INIT_ERROR = "KEYSTORE_INIT_ERROR";
    private static final String USER_NOT_AUTHENTICATED_ERROR = "USER_NOT_AUTHENTICATED_ERROR";
    private static final String STORE_PIN_ERROR = "STORE_PIN_ERROR";
    private static final String RETRIEVE_PIN_ERROR = "RETRIEVE_PIN_ERROR";
    private static final String UNRECOVERABLE_PIN_ERROR = "UNRECOVERABLE_PIN_ERROR";
    private static final String DELETE_KEY_ERROR = "DELETE_KEY_ERROR";

    private static final int AUTH_FOR_ENCRYPT_REQUEST_CODE = 1;
    private static final int AUTH_FOR_DECRYPT_REQUEST_CODE = 2;
    private static final int REQUEST_CODE_FOR_SET_PASSWORD_ACTION = 3;


    public RNConfirmDeviceCredentialsModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "ConfirmDeviceCredentials";
    }

    @ReactMethod
    public void isDeviceSecure(Promise promise) {
        try {
            promise.resolve(AndroidKeyStoreHelper.isDeviceSecure(getReactApplicationContext()));
        } catch (Exception e) {
            promise.reject(DEVICE_SECURE_ERROR, e);
        }
    }

    @ReactMethod
    public void makeDeviceSecure(String message, String actionButtonLabel, final Promise promise) {
        final ActivityEventListener activityEventListener = new ActivityEventListener() {
            @Override
            public void onActivityResult(Activity activity,
                                         int requestcode,
                                         int resultCode,
                                         Intent intent) {
                if (requestcode == REQUEST_CODE_FOR_SET_PASSWORD_ACTION) {
                    if (AndroidKeyStoreHelper.isDeviceSecure(getReactApplicationContext())) {
                        promise.resolve(true);
                    } else if (resultCode == Activity.RESULT_OK) {
                        // Retry since now the user is authenticated.
                        Log.d(TAG, "makeDeviceSecure/onActivityResult/ok");
                        promise.resolve(AndroidKeyStoreHelper.isDeviceSecure(
                                getReactApplicationContext()));
                    } else {
                        // User decided to reject authentication.
                        Log.d(TAG, "makeDeviceSecure/onActivityResult/user-canceled-setup/" + resultCode);
                        promise.reject(MAKE_DEVICE_SECURE_ERROR, "User canceled setup");
                    }
                }
                getReactApplicationContext().removeActivityEventListener(this);
            }

            @Override
            public void onNewIntent(Intent intent) {
                // Do nothing
            }
        };

        AndroidKeyStoreHelper.MakeDeviceSecureCallback makeDeviceSecureCallback =
                new AndroidKeyStoreHelper.MakeDeviceSecureCallback() {
                    @Override
                    public void onUserCancelled() {
                        Log.d(TAG, "makeDeviceSecure/onUserCancelled");
                        promise.reject(MAKE_DEVICE_SECURE_ERROR, "User dismissed dialog");
                    }

                    @Override
                    public void onUserTransitionToSetupDeviceLock() {
                        getReactApplicationContext().addActivityEventListener(activityEventListener);
                    }
                };
        try {
            AndroidKeyStoreHelper.makeDeviceSecure(
                    getCurrentActivity(),
                    message,
                    actionButtonLabel,
                    REQUEST_CODE_FOR_SET_PASSWORD_ACTION,
                    makeDeviceSecureCallback);
        } catch (Exception e) {
            Log.d(TAG, "makeDeviceSecure/error", e);
            promise.reject(MAKE_DEVICE_SECURE_ERROR, e);
        }
    }

    /**
     * This can be called multiple times, it won't recreate the key if the key already exists.
     * reauthenticationTimeoutInSecs and invalidateKeyByNewBiometricEnrollment cannot be
     * changed after this call. The only way to re-configure them is to first {@see #deleteKey()}
     */
    @ReactMethod
    public void keystoreInit(String keyName,
                             int reauthenticationTimeoutInSecs,
                             boolean invalidateKeyByNewBiometricEnrollment,
                             Promise promise) {
        try {
            if (!AndroidKeyStoreHelper.isDeviceSecure(getReactApplicationContext())) {
                promise.reject(DEVICE_SECURE_ERROR, new Exception("Device is not secure"));
                return;
            }
            if (!AndroidKeyStoreHelper.keyExists(keyName)) {
                Log.i(TAG, "keyStoreInit/key does not exist, creating it");
                createKey(keyName, reauthenticationTimeoutInSecs, invalidateKeyByNewBiometricEnrollment);
                promise.resolve(true);
            } else {
                Log.i(TAG, "keyStoreInit/key exists");
                promise.resolve(true);
            }
            Log.d("GethModule", "key created");
        } catch (Exception e) {
            promise.reject(KEYSTORE_INIT_ERROR, e);
        }
    }

    @ReactMethod
    public void deleteKey(String keyName, Promise promise) {
        try {
            if (AndroidKeyStoreHelper.keyExists(keyName)) {
                promise.reject(DELETE_KEY_ERROR, "Key not found");
            }
            boolean result = AndroidKeyStoreHelper.deleteKey(keyName);
            promise.resolve(result);
        } catch (Exception e) {
            promise.reject(DELETE_KEY_ERROR, e);
        }
    }

    /**
     * Creates a symmetric key in the Android Key Store which can only be used after
     * the user has authenticated with device credentials within the last X seconds.
     */
    private void createKey(String keyName, int reauthenticationTimeoutInSecs,
                           boolean invalidateKeyByNewBiometricEnrollment) {
        try {
            boolean result = AndroidKeyStoreHelper.createKey(keyName,
                    reauthenticationTimeoutInSecs, invalidateKeyByNewBiometricEnrollment);
            Log.i(TAG, "createKey/key creation result: " + result);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create a symmetric key", e);
        }
    }

    /**
     * storePin always requires user to confirm device credentials, irrespective of when they
     * last confirmed them.
     * @param keyName This key must have been created before with {@see #createKey}
     * @param pinValue An arbitrary string you want to store as the PIN
     * @param promise promise which resolves/rejects depending on whether pin storage succeeded or not
     */
    @ReactMethod
    public void storePin(final String keyName, final String pinValue, final Promise promise) {
        Runnable storePinRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    boolean result = AndroidKeyStoreHelper.storePin(
                            getReactApplicationContext(),
                            keyName,
                            pinValue);
                    promise.resolve(result);
                } catch (final UserNotAuthenticatedException e) {
                    promise.reject(USER_NOT_AUTHENTICATED_ERROR, e);
                } catch (Exception e) {
                    promise.reject(STORE_PIN_ERROR, e);
                }
            }
        };
        performAuthentication(promise,
                new UserNotAuthenticatedException("User failed to authenticate"),
                AUTH_FOR_ENCRYPT_REQUEST_CODE,
                storePinRunnable);
    }

    @ReactMethod
    public void retrievePin(final String keyName, final Promise promise) {
        try {
            String result = AndroidKeyStoreHelper.retrievePin(getReactApplicationContext(),
                    keyName);
            promise.resolve(result);
        } catch (UserNotAuthenticatedException e) {
            final Runnable retryRunnable = new Runnable() {
                @Override
                public void run() {
                    retrievePin(keyName, promise);
                }
            };
            performAuthentication(promise, e, AUTH_FOR_DECRYPT_REQUEST_CODE, retryRunnable);
        } catch (UnrecoverableKeyException e) {
            // The user removed the screen lock. The encryption key is unrecoverable, even if,
            // user puts the screen lock back on.
            promise.reject(UNRECOVERABLE_PIN_ERROR, e);
        } catch (Exception e) {
            promise.reject(RETRIEVE_PIN_ERROR, e);
        }
    }

    private void performAuthentication(final Promise promise,
                                       final UserNotAuthenticatedException e,
                                       final int requestCode,
                                       final Runnable retryRunnable) {
        ActivityEventListener activityEventListener = new ActivityEventListener() {
            @Override
            public void onActivityResult(Activity activity,
                                         int requestcode,
                                         int resultCode,
                                         Intent intent) {
                if (requestcode == requestCode) {
                    if (resultCode == Activity.RESULT_OK) {
                        // Retry since now the user is authenticated.
                        retryRunnable.run();
                    } else {
                        // User decided to reject authentication.
                        promise.reject(USER_NOT_AUTHENTICATED_ERROR, e);
                    }
                    getReactApplicationContext().removeActivityEventListener(this);
                }
            }

            @Override
            public void onNewIntent(Intent intent) {
                // Do nothing
            }
        };
        getReactApplicationContext().addActivityEventListener(activityEventListener);
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            // Current activity is null, cannot authenticate.
            promise.reject(USER_NOT_AUTHENTICATED_ERROR, e);
        } else {
            AndroidKeyStoreHelper.authenticateUser(
                    currentActivity,
                    requestCode);
        }
    }
}
