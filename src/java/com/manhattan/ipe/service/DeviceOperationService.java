package com.manhattan.ipe.service;

import com.manhattan.ipe.analytics.DeviceOperationAnalytics;
import com.manhattan.ipe.dto.DeviceConnectDTO;
import com.manhattan.ipe.dto.DeviceOperationDTO;
import com.manhattan.ipe.memory.DeviceOperationMemory;
import com.manhattan.ipe.model.DeviceOperationTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceOperationService {
    public List<DeviceOperationDTO> findDeviceOperation(UUID deviceId){
        
        DeviceOperationMemory operationMemory = DeviceOperationMemory.getInstance();
        List<DeviceOperationDTO> allOperation = operationMemory.findAll();
        List<DeviceOperationDTO> deviceOperation = new ArrayList<>();
        
        for(DeviceOperationDTO dto:allOperation){
            if(dto.getDeviceId().equals(deviceId)){
                if(!dto.getOperationType().equals(DeviceOperationTypes.REDIRECT_TO_ADS.getType())){
                    deviceOperation.add(dto);
                }
            }
        }
        for(DeviceOperationDTO dto:deviceOperation){
            operationMemory.remove(dto.getOperationId());
        }
        return deviceOperation;
    }
    
    public List<DeviceOperationDTO> findRedirectOperation(UUID deviceId){
        
        DeviceOperationMemory operationMemory = DeviceOperationMemory.getInstance();
        List<DeviceOperationDTO> allOperation = operationMemory.findAll();
        List<DeviceOperationDTO> deviceOperation = new ArrayList<>();
        
        for(DeviceOperationDTO dto:allOperation){
            if(dto.getDeviceId().equals(deviceId)){
                if(dto.getOperationType().equals(DeviceOperationTypes.REDIRECT_TO_ADS.getType())){
                    deviceOperation.add(dto);
                }
            }
        }
        for(DeviceOperationDTO dto:deviceOperation){
            operationMemory.remove(dto.getOperationId());
        }
        return deviceOperation;
    }
    
    public void requestAccess(UUID accessToken, String vlanIp){
        DeviceConnectService connectService = new DeviceConnectService();
        DeviceConnectDTO deviceConnectDTO = connectService.findByAccessToken(accessToken);
        if(deviceConnectDTO!=null){
            DeviceOperationDTO operationDTO = new DeviceOperationDTO();
            operationDTO.setDeviceId(deviceConnectDTO.getDeviceId());
            operationDTO.setIp(vlanIp);
            operationDTO.setOperationId(UUID.randomUUID());
            operationDTO.setOperationType(DeviceOperationTypes.ASK_ACCESS.getType());
            operationDTO.setTime(System.currentTimeMillis());
            DeviceOperationMemory operationMemory = DeviceOperationMemory.getInstance();
            operationMemory.add(operationDTO);   
            DeviceOperationAnalytics.onRequestAccess(operationDTO);
        }
    }
    
    public void connectClientDevice(UUID deviceId, String vlanIp, String macAddress){
        DeviceOperationDTO operationDTO = new DeviceOperationDTO();
        operationDTO.setDeviceId(deviceId);
        operationDTO.setIp(vlanIp);
        operationDTO.setOperationId(UUID.randomUUID());
        operationDTO.setOperationType(DeviceOperationTypes.REDIRECT_TO_ADS.getType());
        operationDTO.setTime(System.currentTimeMillis());
        DeviceOperationMemory operationMemory = DeviceOperationMemory.getInstance();
        operationMemory.add(operationDTO);   
        
        DeviceClientConnectedService clientConnectedService = new DeviceClientConnectedService();
        clientConnectedService.addClientToDevice(deviceId, vlanIp, macAddress);
    }

    public void disconnectClientDevice(UUID deviceId, String vlanIp, String macAddress){
        DeviceOperationMemory operationMemory = DeviceOperationMemory.getInstance();
        List<DeviceOperationDTO> allOperation = operationMemory.findAll();
        List<DeviceOperationDTO> deviceOperation = new ArrayList<>();
        
        for(DeviceOperationDTO dto:allOperation){
            if(dto.getDeviceId().equals(deviceId)){
                if(dto.getIp().equals(vlanIp)){
                    deviceOperation.add(dto);
                }
            }
        }
        for(DeviceOperationDTO dto:deviceOperation){
            operationMemory.remove(dto.getOperationId());
        }

        DeviceClientConnectedService clientConnectedService = new DeviceClientConnectedService();
        clientConnectedService.removeClientToDevice(deviceId, vlanIp, macAddress);
    }
    
    public boolean hasRedirectOperation(UUID deviceId, String vlanIp) {
        DeviceOperationMemory operationMemory = DeviceOperationMemory.getInstance();
        List<DeviceOperationDTO> allOperation = operationMemory.findAll();
        for (DeviceOperationDTO dto : allOperation) {
            if (dto.getDeviceId().equals(deviceId)) {
                if (dto.getOperationType().equals(DeviceOperationTypes.REDIRECT_TO_ADS.getType())) {
                    if (dto.getIp().equals(vlanIp)) {
                        operationMemory.remove(dto.getOperationId());
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void removeAllFromDevice(UUID deviceId){
        DeviceOperationMemory operationMemory = DeviceOperationMemory.getInstance();
        List<DeviceOperationDTO> allOperation = operationMemory.findAll();
        List<DeviceOperationDTO> deviceOperation = new ArrayList<>();
        
        for(DeviceOperationDTO dto:allOperation){
            if(dto.getDeviceId().equals(deviceId)){
                deviceOperation.add(dto);
            }
        }
        
        for(DeviceOperationDTO dto:deviceOperation){
            operationMemory.remove(dto.getOperationId());
        }
    }
    
    public void print(){
        DeviceOperationMemory operationMemory = DeviceOperationMemory.getInstance();
        List<DeviceOperationDTO> allOperation = operationMemory.findAll();
        for(DeviceOperationDTO dto:allOperation){
            System.out.println(dto.toString());
        }
    }
}
