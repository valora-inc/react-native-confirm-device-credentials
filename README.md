# react-native-confirm-device-credentials

A simple React Native module to access Android's native device authentication screen.

## Getting started

`$ npm install react-native-confirm-device-credentials --save`

### Mostly automatic installation

`$ react-native link react-native-confirm-device-credentials`

### Manual installation

#### Android (Only API 23 and above are supported)

1. Open up `android/app/src/main/java/[...]/MainActivity.java`

- Add `import org.celo.rncdd.RNConfirmDeviceCredentialsPackage;` to the imports at the top of the file
- Add `new RNConfirmDeviceCredentialsPackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:
   ```
   include ':react-native-confirm-device-credentials'
   project(':react-native-confirm-device-credentials').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-confirm-device-credentials/android')
   ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
   ```
     compile project(':react-native-confirm-device-credentials')
   ```

## Usage

```javascript
import ConfirmDeviceCredentials from "react-native-confirm-device-credentials";

ConfirmDeviceCredentials.isDeviceSecure().then(
  (isDeviceSecureResult: boolean) => {
    console.log("Is device secure: " + isDeviceSecureResult);
    if (!isDeviceSecureResult) {
      ConfirmDeviceCredentials.makeDeviceSecure(
        humanReadableMessage,
        securitySettingsButtonLabel
      )
        .then((result: boolean) => {
          console.log("Result of make device secure: " + result);
        })
        .catch((error: any) => {
          console.log("User canceled authentication: " + error);
        });
    }
    if (isDeviceSecureResult) {
      ConfirmDeviceCredentials.keystoreInit(keyName, 10, false).then(
        (keyStoreInitResult: boolean) => {
          console.log("Keystore init result: " + keyStoreInitResult);
          if (keyStoreInitResult) {
            ConfirmDeviceCredentials.storePin(keyName, pin)
              .then((storePinResult: boolean) => {
                console.log("Store Pin result: " + storePinResult);
                ConfirmDeviceCredentials.retrievePin(keyName).then(
                  (decryptedPin: string) => {
                    console.log("Decrypted Pin is : " + decryptedPin);
                  }
                );
              })
              .catch((reason: any) => {
                console.log("Store pin failed: " + reason);
              });
          }
        }
      );
    }
  }
);
```
