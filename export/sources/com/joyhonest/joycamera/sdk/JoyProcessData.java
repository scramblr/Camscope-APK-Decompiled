package com.joyhonest.joycamera.sdk;

import java.util.ArrayList;
import kotlin.UByte;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
class JoyProcessData {
    JoyProcessData() {
    }

    /* JADX WARN: Code duplicated, block: B:96:0x02bb  */
    public static boolean PressData(byte[] bArr) {
        String str;
        String str2;
        if (GP4225_Device.MacAddress == null) {
            GP4225_Device.MacAddress = new byte[6];
            for (int i = 0; i < 6; i++) {
                GP4225_Device.MacAddress[0] = 0;
            }
        }
        if (bArr == null || bArr.length <= 10 || (bArr[0] & UByte.MAX_VALUE) != 70 || (bArr[1] & UByte.MAX_VALUE) != 68 || (bArr[2] & UByte.MAX_VALUE) != 87 || (bArr[3] & UByte.MAX_VALUE) != 78) {
            return false;
        }
        int i2 = (bArr[4] & UByte.MAX_VALUE) + ((bArr[5] & UByte.MAX_VALUE) * 256);
        int i3 = (bArr[6] & UByte.MAX_VALUE) + ((bArr[7] & UByte.MAX_VALUE) * 256);
        int i4 = (bArr[8] & 255) + ((bArr[9] & 255) * 256);
        if (i4 == 0 || i4 + 10 > bArr.length) {
            return false;
        }
        if (i2 == 0 && i3 == 1) {
            GP4225_Device.nMode = bArr[10] & UByte.MAX_VALUE;
            GP4225_Device.bSD = (bArr[11] & 1) == 0;
            GP4225_Device.bSDRecording = (bArr[11] & 2) != 0;
            GP4225_Device.VideosCount = (bArr[12] & UByte.MAX_VALUE) | ((bArr[13] & UByte.MAX_VALUE) * 256) | ((bArr[14] & UByte.MAX_VALUE) * 65536) | ((bArr[15] & UByte.MAX_VALUE) * 16777216);
            GP4225_Device.LockedCount = (bArr[16] & UByte.MAX_VALUE) | ((bArr[17] & UByte.MAX_VALUE) * 256) | ((bArr[18] & UByte.MAX_VALUE) * 65536) | ((bArr[19] & UByte.MAX_VALUE) * 16777216);
            GP4225_Device.PhotoCount = (bArr[20] & UByte.MAX_VALUE) | ((bArr[21] & UByte.MAX_VALUE) * 256) | ((bArr[22] & UByte.MAX_VALUE) * 65536) | ((bArr[23] & UByte.MAX_VALUE) * 16777216);
            if (i4 >= 26) {
                GP4225_Device.nSDAllSize = ((long) ((bArr[24] & UByte.MAX_VALUE) | ((bArr[25] & UByte.MAX_VALUE) * 256) | ((bArr[26] & UByte.MAX_VALUE) * 65536))) | (((long) (bArr[27] & UByte.MAX_VALUE)) * 16777216) | (((long) (bArr[34] & UByte.MAX_VALUE)) * 4294967296L);
                GP4225_Device.nSDAvaildSize = ((long) ((bArr[28] & UByte.MAX_VALUE) | ((bArr[29] & UByte.MAX_VALUE) * 256) | ((bArr[30] & UByte.MAX_VALUE) * 65536))) | (((long) (bArr[31] & UByte.MAX_VALUE)) * 16777216) | (((long) (bArr[35] & UByte.MAX_VALUE)) * 4294967296L);
            }
            if (bArr.length >= 34) {
                GP4225_Device.nBattery = bArr[32] & UByte.MAX_VALUE;
                GP4225_Device.bAdjfocus = bArr[33] != 0;
                if (bArr.length >= 35) {
                    GP4225_Device.nFuncMask = bArr[34] & UByte.MAX_VALUE;
                }
                if (bArr.length >= 40) {
                    GP4225_Device.nSDRecordTime = (bArr[36] & UByte.MAX_VALUE) | ((bArr[37] & UByte.MAX_VALUE) * 256) | ((bArr[38] & UByte.MAX_VALUE) * 65536) | ((bArr[39] & UByte.MAX_VALUE) * 16777216);
                } else {
                    GP4225_Device.nSDRecordTime = 0;
                }
                if (bArr.length >= 48) {
                    System.arraycopy(bArr, 40, GP4225_Device.MacAddress, 0, 6);
                    GP4225_Device.CustermerType = bArr[46];
                    GP4225_Device.DeviceType = bArr[47];
                }
            } else {
                GP4225_Device.nFuncMask = 0;
                GP4225_Device.nBattery = 4;
                GP4225_Device.bAdjfocus = false;
                GP4225_Device.nSDRecordTime = 0;
            }
            EventBus.getDefault().post(Integer.valueOf(GP4225_Device.nBattery), "onGetBattery");
            EventBus.getDefault().post("", "GP4225_GetStatus");
            return true;
        }
        if (i2 == 65535) {
            int i5 = i4 + 6;
            byte[] bArr2 = new byte[i5];
            System.arraycopy(bArr, 4, bArr2, 0, i5);
            EventBus.getDefault().post(bArr2, "onGetCustomData");
            return true;
        }
        if (i2 == 2) {
            if (i3 == 1 || i3 == 2 || i3 == 3) {
                int i6 = (bArr[10] & UByte.MAX_VALUE) | ((bArr[11] & UByte.MAX_VALUE) * 256);
                int i7 = (bArr[12] & UByte.MAX_VALUE) | ((bArr[13] & UByte.MAX_VALUE) * 256);
                JoyGetFiles joyGetFiles = new JoyGetFiles();
                joyGetFiles.files = new ArrayList();
                for (int i8 = 0; i8 <= i7 - i6; i8++) {
                    int i9 = i8 * 68;
                    int i10 = (bArr[i9 + 46] & UByte.MAX_VALUE) | ((bArr[i9 + 47] & UByte.MAX_VALUE) * 256) | ((bArr[i9 + 48] & UByte.MAX_VALUE) * 65536) | ((bArr[i9 + 49] & UByte.MAX_VALUE) * 16777216);
                    int i11 = i9 + 50;
                    int i12 = 0;
                    for (int i13 = 0; i13 < 32; i13++) {
                        if (bArr[i11 + i13] != 0) {
                            i12++;
                        }
                    }
                    if (i12 == 0) {
                        str2 = "";
                    } else {
                        byte[] bArr3 = new byte[i12];
                        System.arraycopy(bArr, i11, bArr3, 0, i12);
                        str2 = new String(bArr3, 0, i12);
                    }
                    joyGetFiles.files.add(new JoyFile("", str2, i10));
                }
                EventBus.getDefault().post(joyGetFiles, "onGetSdFliesList");
            }
            return true;
        }
        if (i2 == 9) {
            byte b = bArr[10];
            if (i4 <= 64) {
                str = "";
            } else {
                int i14 = 0;
                for (int i15 = 0; i15 < 32 && bArr[i15 + 43] != 0; i15++) {
                    i14++;
                }
                if (i14 == 0) {
                    str = "";
                } else {
                    str = new String(bArr, 43, i14);
                }
            }
            if (i3 == 1) {
                EventBus.getDefault().post(new JoyFile("", str, b), "GP4225_DeleteFile");
            } else if (i3 == 2) {
                EventBus.getDefault().post(Integer.valueOf(b), "GP4225_DeleteAllFile");
            }
            return true;
        }
        if (i2 == 33) {
            if (i3 != 1) {
                return false;
            }
            byte[] bArr4 = new byte[i4];
            System.arraycopy(bArr, 10, bArr4, 0, i4);
            EventBus.getDefault().post(bArr4, "GP4225_Get_Transfer_data");
            return true;
        }
        if (i2 != 32) {
            return false;
        }
        if (i3 == 14) {
            Integer numValueOf = Integer.valueOf(bArr[10]);
            EventBus.getDefault().post(numValueOf, "onGetLed");
            EventBus.getDefault().post(numValueOf, "GP4225_GetLed");
        } else if (i3 == 16) {
            EventBus.getDefault().post(Integer.valueOf(bArr[10]), "GP4225_GetResolution");
        } else if (i3 == 40) {
            byte[] bArr5 = new byte[8];
            System.arraycopy(bArr, 10, bArr5, 0, 8);
            EventBus.getDefault().post(bArr5, "onSensorWarn_res");
        } else if (i3 == 80) {
            byte[] bArr6 = new byte[i4];
            System.arraycopy(bArr, 10, bArr6, 0, i4);
            EventBus.getDefault().post(bArr6, "GP4225_GetDeviceInfo");
        } else if (i3 == 18) {
            byte[] bArr7 = new byte[i4];
            System.arraycopy(bArr, 10, bArr7, 0, i4);
            EventBus.getDefault().post(bArr7, "onGetSensorData");
        } else if (i3 != 19) {
            try {
                switch (i3) {
                    case 1:
                        byte[] bArr8 = new byte[i4];
                        System.arraycopy(bArr, 10, bArr8, 0, i4);
                        EventBus.getDefault().post(bArr8, "GP4225_GetDeviceDateTime");
                        break;
                    case 2:
                        EventBus.getDefault().post(Integer.valueOf(bArr[10]), "GP4225_GetDeviceOsdStatus");
                        break;
                    case 3:
                        EventBus.getDefault().post(Integer.valueOf(bArr[10]), "GP4225_GetDeviceReversaltatus");
                        break;
                    case 4:
                        EventBus.getDefault().post(Integer.valueOf(bArr[10]), "GP4225_GetDeviceRecordTime");
                        break;
                    case 5:
                        byte[] bArr9 = new byte[i4];
                        System.arraycopy(bArr, 10, bArr9, 0, i4);
                        EventBus.getDefault().post(new String(bArr9), "onGetWiFiSSID");
                        break;
                    case 6:
                        byte[] bArr10 = new byte[i4];
                        System.arraycopy(bArr, 10, bArr10, 0, i4);
                        EventBus.getDefault().post(new String(bArr10), "onGetWiFiPassword");
                        break;
                    case 7:
                        EventBus.getDefault().post(Integer.valueOf(bArr[10]), "GP4225_WifiChannel");
                        break;
                    case 8:
                        EventBus.getDefault().post(Integer.valueOf(bArr[10]), "GP4225_FormatSD_Status");
                        break;
                    case 9:
                        byte[] bArr11 = new byte[i4];
                        System.arraycopy(bArr, 10, bArr11, 0, i4);
                        GP4225_Device.sVer = new String(bArr11);
                        EventBus.getDefault().post(GP4225_Device.sVer, "GP4225_GetFireWareVersion");
                        EventBus.getDefault().post(GP4225_Device.sVer, "onGetFirmwareVersion");
                        break;
                    case 10:
                        EventBus.getDefault().post(Integer.valueOf(bArr[10]), "GP4225_Reset_Status");
                        break;
                    case 11:
                        EventBus.getDefault().post(Integer.valueOf((((bArr[10] & UByte.MAX_VALUE) + ((bArr[11] & UByte.MAX_VALUE) * 256)) >> 4) & 1023), "GP4225_34_GetAdjFocus");
                        break;
                    case 12:
                        EventBus.getDefault().post(Integer.valueOf((bArr[10] & UByte.MAX_VALUE) + ((bArr[11] & UByte.MAX_VALUE) * 256)), "GP4225_GetVcm");
                        break;
                    default:
                        switch (i3) {
                            case 21:
                                EventBus.getDefault().post(Integer.valueOf(bArr[10]), "GP4225_GetIR_Status");
                                break;
                            case 22:
                                EventBus.getDefault().post(Integer.valueOf(bArr[10]), "GP4225_GetPIR_Status");
                                break;
                            case 23:
                                EventBus.getDefault().post(Integer.valueOf(bArr[10]), "GP4225_GetLed_Status");
                                break;
                            case 24:
                                if (i4 != 4) {
                                    return false;
                                }
                                byte[] bArr12 = new byte[4];
                                System.arraycopy(bArr, 10, bArr12, 0, 4);
                                EventBus.getDefault().post(bArr12, "GP4225_GetKey");
                                EventBus.getDefault().post(bArr12, "onGetKey_new");
                                break;
                                break;
                            case 25:
                                if (i4 != 13) {
                                    return false;
                                }
                                byte[] bArr13 = new byte[13];
                                System.arraycopy(bArr, 10, bArr13, 0, 13);
                                EventBus.getDefault().post(bArr13, "GP4225_GetRadarData");
                                break;
                                break;
                            default:
                                switch (i3) {
                                    case 32:
                                        EventBus.getDefault().post(Integer.valueOf(bArr[11]), "onGetLedMode");
                                        break;
                                    case 33:
                                        byte[] bArr14 = new byte[4];
                                        System.arraycopy(bArr, 10, bArr14, 0, 4);
                                        EventBus.getDefault().post(bArr14, "onGetZoom_res");
                                        break;
                                    case 34:
                                        byte[] bArr15 = new byte[4];
                                        System.arraycopy(bArr, 10, bArr15, 0, 4);
                                        EventBus.getDefault().post(bArr15, "onSetZoom_res");
                                        break;
                                    default:
                                        switch (i3) {
                                            case 42:
                                                byte[] bArr16 = new byte[i4];
                                                System.arraycopy(bArr, 10, bArr16, 0, i4);
                                                EventBus.getDefault().post(bArr16, "onGetBatteryInfo");
                                                break;
                                            case 43:
                                                byte[] bArr17 = new byte[i4];
                                                System.arraycopy(bArr, 10, bArr17, 0, i4);
                                                EventBus.getDefault().post(bArr17, "onGetCameraPara");
                                                break;
                                            case 44:
                                                byte[] bArr18 = new byte[i4];
                                                System.arraycopy(bArr, 10, bArr18, 0, i4);
                                                EventBus.getDefault().post(bArr18, "onGetPcmInfo");
                                                break;
                                            case 45:
                                                byte[] bArr19 = new byte[i4];
                                                System.arraycopy(bArr, 10, bArr19, 0, i4);
                                                EventBus.getDefault().post(bArr19, "onGetDeviceCategory");
                                                break;
                                            case 46:
                                                byte[] bArr20 = new byte[i4];
                                                System.arraycopy(bArr, 10, bArr20, 0, i4);
                                                EventBus.getDefault().post(bArr20, "GetSystemControlData");
                                                break;
                                            default:
                                                return false;
                                        }
                                        break;
                                }
                                break;
                        }
                        break;
                }
            } catch (Exception unused) {
            }
        } else {
            EventBus.getDefault().post(Integer.valueOf(bArr[10]), "GP4225_GetAC_Data");
        }
        return true;
    }

    public static boolean PressData_20000(byte[] bArr) {
        if (bArr == null || bArr.length < 3) {
            return true;
        }
        int i = (bArr[0] & UByte.MAX_VALUE) | ((bArr[1] & UByte.MAX_VALUE) * 256);
        int i2 = bArr[2] & UByte.MAX_VALUE;
        if (i == 32 && i2 >= 33) {
            EventBus.getDefault().post(Integer.valueOf(((bArr[36] & UByte.MAX_VALUE) * 256) | (bArr[35] & UByte.MAX_VALUE)), "onGetFirewareVerB");
        }
        return true;
    }
}
