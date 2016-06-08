/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manhattan.ipe.analytics;

import com.manhattan.foundation.uuid.JSONHandler; 
import com.manhattan.ipe.dto.DeviceOperationDTO;
import java.io.IOException;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author lucasschiochet
 */
public class DeviceOperationAnalytics {
    public static void onRequestAccess(final DeviceOperationDTO dto){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = toMap(dto);
                try {
                    MixpanelSender.sendEvent("REQUEST ACCESS", map);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    
    public static Map<String, Object> toMap(DeviceOperationDTO dto){
        JSONObject obj = dto.toJsonObject();
        return JSONHandler.jsonToMap(obj);
    }
        
}
