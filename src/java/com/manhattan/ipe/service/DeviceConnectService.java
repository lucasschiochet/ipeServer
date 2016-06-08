package com.manhattan.ipe.service;

import com.manhattan.ipe.analytics.DeviceConnectAnalytics;
import com.manhattan.ipe.dto.DeviceConnectDTO;
import com.manhattan.ipe.memory.DeviceConnectMemory;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lucasschiochet
 */
public class DeviceConnectService {
    public boolean hasAccess(UUID accessToken){
        DeviceConnectDTO dto = findByAccessToken(accessToken);
        if(dto!=null){
            return true;
        }else{
            return false;
        }
    }
    public DeviceConnectDTO findByAccessToken(UUID token){
        DeviceConnectMemory connectMemory = DeviceConnectMemory.getInstance();
        List<DeviceConnectDTO> list = connectMemory.findAll();
        DeviceConnectDTO dto = null;
        for(DeviceConnectDTO d:list){
            if(d.getAccessToken().equals(token)){
                dto = d;
                break;
            }
        }
        return dto;
    }
    
    public DeviceConnectDTO findByImei(String imei){
        DeviceConnectMemory connectMemory = DeviceConnectMemory.getInstance();
        List<DeviceConnectDTO> list = connectMemory.findAll();
        DeviceConnectDTO dto = null;
        for(DeviceConnectDTO d:list){
            if(d.getImei().equals(imei)){
                dto = d;
                break;
            }
        }
        return dto;
    }
    
    public DeviceConnectDTO findByIp(String ip){
        DeviceConnectMemory connectMemory = DeviceConnectMemory.getInstance();
        List<DeviceConnectDTO> list = connectMemory.findAll();
        DeviceConnectDTO dto = null;
        for(DeviceConnectDTO d:list){
            if(d.getIp().equals(ip)){
                dto = d;
                break;
            }
        }
        return dto;
    }
    
    public DeviceConnectDTO insert(String imei, String ip, String ssidName){
        
        DeviceConnectDTO dto = findByImei(imei);
        if(dto!=null){
            return dto;
        }else{
           dto = new DeviceConnectDTO();
           dto.setDeviceId(UUID.randomUUID());
           dto.setAccessToken(UUID.randomUUID());
           dto.setImei(imei);
           dto.setIp(ip);
           dto.setTime(System.currentTimeMillis());
           dto.setSsidName(ssidName);
           DeviceConnectMemory connectMemory = DeviceConnectMemory.getInstance();
           connectMemory.add(dto);
           DeviceConnectAnalytics.onDeviceConnect(dto);
           return dto;
        }
    }
    
    public void removeByAccessToken(UUID accessToken){
        DeviceConnectDTO dto = findByAccessToken(accessToken);
        if(dto!=null){
            remove(dto);
        }
    }
    
    public void remove(DeviceConnectDTO dto){
        DeviceConnectMemory connectMemory = DeviceConnectMemory.getInstance();
        connectMemory.remove(dto.getDeviceId());
        
        DeviceOperationService operationService = new DeviceOperationService();
        operationService.removeAllFromDevice(dto.getDeviceId());
        
        DeviceClientConnectedService clientConnectedService = new DeviceClientConnectedService();
        clientConnectedService.disconnectDevice(dto.getDeviceId());
        
        DeviceConnectAnalytics.onDeviceDisconnect(dto);
    }
    
    public List<DeviceConnectDTO> findAll(){
        DeviceConnectMemory connectMemory = DeviceConnectMemory.getInstance();
        List<DeviceConnectDTO> list = connectMemory.findAll();
        return list;
    }
    public void print(){
        DeviceConnectMemory connectMemory = DeviceConnectMemory.getInstance();
        List<DeviceConnectDTO> list = connectMemory.findAll();
        for(DeviceConnectDTO dto:list){
            System.out.println(dto.toString());
        }
    }
    
}
