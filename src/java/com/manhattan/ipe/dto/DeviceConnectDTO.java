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
public class DeviceConnectDTO {
    private UUID deviceId;
    private String imei;
    private String ip;
    private Long time;
    private UUID accessToken;
    private String ssidName;
    
    public static DeviceConnectDTO fromJson(String data){
        JSONObject obj = new JSONObject(data);
        DeviceConnectDTO dto = new DeviceConnectDTO();
        dto.setDeviceId(UUID.fromString(obj.getString("deviceId")));
        dto.setImei(obj.getString("imei"));
        dto.setIp(obj.getString("ip"));
        dto.setTime(obj.getLong("time"));
        dto.setAccessToken(UUID.fromString(obj.getString("accessToken")));
        dto.setSsidName(obj.getString("ssidName"));
        return dto;
    }
    
    public JSONObject toJsonObject(){
        JSONObject obj = new JSONObject();
        obj.put("deviceId", deviceId.toString());
        obj.put("imei", imei);
        obj.put("ip", ip);
        obj.put("time", time);
        obj.put("accessToken", accessToken.toString());
        obj.put("ssidName", ssidName);
        return obj;
    }
    
    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }


    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    public UUID getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(UUID accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "DeviceConnectDTO{" + "deviceId=" + deviceId + ", imei=" + imei + ", ip=" + ip + ", time=" + time + ", accessToken=" + accessToken + '}';
    }

    public String getSsidName() {
        return ssidName;
    }

    public void setSsidName(String ssidName) {
        this.ssidName = ssidName;
    }
            
    
    
}
