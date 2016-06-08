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
public class DeviceOperationDTO {
    private UUID operationId;
    private UUID deviceId;
    private Integer operationType;
    private String ip;
    private Long time;

    public static DeviceOperationDTO fromJson(String data){
        JSONObject obj = new JSONObject(data);
        DeviceOperationDTO dto = new DeviceOperationDTO();
        dto.setOperationId(UUID.fromString(obj.getString("operationId")));
        dto.setDeviceId(UUID.fromString(obj.getString("deviceId")));
        dto.setOperationType(obj.getInt("operationType"));
        dto.setIp(obj.getString("ip"));
        dto.setTime(obj.getLong("time")); 
        return dto;
    }
    
    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();
        obj.put("operationId", operationId.toString());
        obj.put("deviceId", deviceId.toString());
        obj.put("operationType", operationType);
        obj.put("ip", ip);
        obj.put("time", time);
        return obj;
    }
        
    public UUID getOperationId() {
        return operationId;
    }

    public void setOperationId(UUID operationId) {
        this.operationId = operationId;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
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

    @Override
    public String toString() {
        return "DeviceOperationDTO{" + "operationId=" + operationId + ", deviceId=" + deviceId + ", operationType=" + operationType + ", ip=" + ip + ", time=" + time + '}';
    }
    
    
    
}
