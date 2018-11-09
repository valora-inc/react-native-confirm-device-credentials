// @flow

// import { Platform, NativeModules } from 'react-native'
// import type {
//   ConfirmDeviceCredentialsNativeModule
// } from './types'

// /**
//  * Geth object
//  * @param {Object} config
//  * @param {number} config.networkID Network identifier (integer, 0=Olympic (disused), 1=Frontier, 2=Morden (disused), 3=Ropsten) (default: 1)
//  * @param {number} config.maxPeers Maximum number of network peers (network disabled if set to 0) (default: 25)
//  * @param {string} config.genesis genesis.json file
//  * @param {string} config.nodeDir Data directory for the databases and keystore
//  * @param {string} config.keyStoreDir Directory for the keystore (default = inside the datadir)
//  * @param {string} config.enodes Comma separated enode URLs for P2P discovery bootstrap
//  */
// class ConfirmDeviceCredentials {
//   confirmDeviceCredentials: ConfirmDeviceCredentialsNativeModule = Platform.select({
//       android: NativeModules.ConfirmDeviceCredentials
//   });

//   constructor(): void {
//   }

//   async isDeviceSecure(): Promise<boolean> {
//     return await this.confirmDeviceCredentials.isDeviceSecure()
//   }

//   // async keystoreInit(
//   //   keyName: string,
//   //   reauthenticationTimeoutInSecs: number,
//   //    invalidateKeyByNewBiometricEnrollment: boolean): Promise<boolean> {
//   //      return await this.geth.keyStoreInit(keyName,
//   //         reauthenticationTimeoutInSecs,
//   //          invalidateKeyByNewBiometricEnrollment)
//   // }

//   // async storePin(
//   //   keyName: string,
//   //   pinValue: string): Promise<boolean> {
//   //     return await this.geth.storePin(keyName, pinValue)
//   //   }

//   // async retrievePin(keyName: string): Promise<string> {
//   //   return await this.geth.retrievePin(keyName)
//   // }
// }


import { Platform, NativeModules } from 'react-native'
import type {
  ConfirmDeviceCredentialsNativeModule
} from './types'

const {ConfirmDeviceCredentials } = NativeModules

// export default {
//   isDeviceSecure: () => {
//     console.log(NativeModules);
//     debugger;
//     return ConfirmDeviceCredentials.isDeviceSecure()
//   }
// }

export default NativeModules.ConfirmDeviceCredentials
