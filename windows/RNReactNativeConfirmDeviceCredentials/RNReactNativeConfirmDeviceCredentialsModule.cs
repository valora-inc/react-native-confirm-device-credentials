using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace React.Native.Confirm.Device.Credentials.RNReactNativeConfirmDeviceCredentials
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNReactNativeConfirmDeviceCredentialsModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNReactNativeConfirmDeviceCredentialsModule"/>.
        /// </summary>
        internal RNReactNativeConfirmDeviceCredentialsModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNReactNativeConfirmDeviceCredentials";
            }
        }
    }
}
