
# react-native-confirm-device-credentials

## Getting started

`$ npm install react-native-confirm-device-credentials --save`

### Mostly automatic installation

`$ react-native link react-native-confirm-device-credentials`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-confirm-device-credentials` and add `RNReactNativeConfirmDeviceCredentials.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNReactNativeConfirmDeviceCredentials.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNReactNativeConfirmDeviceCredentialsPackage;` to the imports at the top of the file
  - Add `new RNReactNativeConfirmDeviceCredentialsPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-confirm-device-credentials'
  	project(':react-native-confirm-device-credentials').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-confirm-device-credentials/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-confirm-device-credentials')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNReactNativeConfirmDeviceCredentials.sln` in `node_modules/react-native-confirm-device-credentials/windows/RNReactNativeConfirmDeviceCredentials.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using React.Native.Confirm.Device.Credentials.RNReactNativeConfirmDeviceCredentials;` to the usings at the top of the file
  - Add `new RNReactNativeConfirmDeviceCredentialsPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNReactNativeConfirmDeviceCredentials from 'react-native-confirm-device-credentials';

// TODO: What to do with the module?
RNReactNativeConfirmDeviceCredentials;
```
  