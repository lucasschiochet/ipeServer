/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manhattan.ipe.analytics;

import com.manhattan.foundation.uuid.JSONHandler;
import com.manhattan.ipe.dto.DeviceConnectDTO;
import java.io.IOException;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author lucasschiochet
 */
public class DeviceConnectAnalytics {
    public static void onDeviceConnect(final DeviceConnectDTO dto){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = toMap(dto);
                try {
                    MixpanelSender.sendEvent("DEVICE CONNECT", map);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    
    public static void onDeviceDisconnect(final DeviceConnectDTO dto){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = toMap(dto);
                try {
                    MixpanelSender.sendEvent("DEVICE DISCONNECT", map);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    
    public static Map<String, Object> toMap(DeviceConnectDTO dto){
        JSONObject obj = dto.toJsonObject();
        return JSONHandler.jsonToMap(obj);
    }
}
