/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.manhattan.ipe.dto;

import java.util.UUID;
import org.json.JSONObject;

/**
 *
 * @author lucasschiochet
 */
public class DeviceClientConnectedDTO {
    private UUID connectId;
    private UUID deviceId;
    private String macAddress;
    private String ip;
    private Long time;
    
   public JSONObject toJsonObject(){
        JSONObject obj = new JSONObject();
        obj.put("connectId", connectId.toString());
        obj.put("deviceId", deviceId.toString());
        obj.put("macAddress", macAddress);
        obj.put("ip", ip);
        obj.put("time", time);
        return obj;
    }
    

    public UUID getConnectId() {
        return connectId;
    }

    public void setConnectId(UUID connectId) {
        this.connectId = connectId;
    }


    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
    
    
}
