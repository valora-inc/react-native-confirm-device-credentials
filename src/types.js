// @flow

/**
 * A typical flow of using this would 
 * 1. Check `isDeviceSecure`. If this promise fails then something bad is going on with the system. If this resolves to
 *    false then call `makeDeviceSecure`
 * 2. `makeDeviceSecure` will prompt user to secure the device and will resolve to true if the user ends up doing so.
 *    It will lead to Promise rejection if the user cancels the prompt or if the user goes to security screen but
 *    decides not to setup a PIN/password/pattern.
 * 3. If the device is secure then `keystoreInit` won't fail unless something bad is going on with the system.
 *    This method is no-op if repeated. So, feel free to call it multiple times. It should be called at least once
 *    before storePin. To avoid overloading functionality and error cases, for now, `keystoreInit` won't call
 *    `makeDeviceSecure`. Therefore, check `isDeviceSecure` before calling, `keystoreInit`.
 * 4. `storePin` will store the Pin. If `isDeviceSecure` is true and `keystoreInit` has been called then this
 *    promise rejection means something bad is going on. This will prompt the user to reauthenticate, if required.
 *    This won't prompt the user to make their device secure.
 * 5. `retrievePin` will return the plain-text PIN. It will prompt the user to reauthenticate, if required. This
 *    won't prompt the user to make their device secure.
 */
type ConfirmDeviceCredentialsModule = {
  isDeviceSecure: () => Promise<boolean>,
  makeDeviceSecure: (message: string, actionButtonLabel: string) => Promise<boolean>,
  keystoreInit: (keyName: string, reauthenticationTimeoutInSecs: number, invalidateKeyByNewBiometricEnrollment: boolean) => Promise<boolean>,
  storePin: (keyName: string, pinValue: string) => Promise<boolean>,
  retrievePin: (keyName: string) => Promise<string>,
}

export type {
  ConfirmDeviceCredentialsModule
}
