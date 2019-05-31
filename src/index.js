// @flow

import { Platform, NativeModules } from "react-native";
import type { ConfirmDeviceCredentialsNativeModule } from "./types";

const { ConfirmDeviceCredentials } = NativeModules;

export default NativeModules.ConfirmDeviceCredentials;
