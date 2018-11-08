
# react-native-confirm-device-credentials

## Getting started

`$ npm install react-native-confirm-device-credentials --save`

### Mostly automatic installation

`$ react-native link react-native-confirm-device-credentials`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNConfirmDeviceCredentialsPackage;` to the imports at the top of the file
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
import RNConfirmDeviceCredentials from 'react-native-confirm-device-credentials';

// TODO: What to do with the module?
RNConfirmDeviceCredentials;
```
  