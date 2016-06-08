/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.manhattan.ipe.service;

import com.manhattan.ipe.analytics.DeviceClientConnectedAnalytics;
import com.manhattan.ipe.dto.DeviceClientConnectedDTO;
import com.manhattan.ipe.memory.DeviceClientConnectedMemory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lucasschiochet
 */
public class DeviceClientConnectedService {
    
    public List<DeviceClientConnectedDTO> findAllByDevice(UUID deviceId){
        DeviceClientConnectedMemory clientMemory = DeviceClientConnectedMemory.getInstance();
        List<DeviceClientConnectedDTO> allOperation = clientMemory.findAll();
        List<DeviceClientConnectedDTO> deviceClient = new ArrayList<>();
        for(DeviceClientConnectedDTO dto:allOperation){
            if(dto.getDeviceId().equals(deviceId)){
                deviceClient.add(dto);
            }
        }
        return deviceClient;
    }
    
    public void disconnectDevice(UUID deviceId){
        DeviceClientConnectedMemory clientMemory = DeviceClientConnectedMemory.getInstance();
        List<DeviceClientConnectedDTO> allOperation = clientMemory.findAll();
        List<DeviceClientConnectedDTO> deviceClient = new ArrayList<>();
        
        for(DeviceClientConnectedDTO dto:allOperation){
            if(dto.getDeviceId().equals(deviceId)){
                deviceClient.add(dto);
            }
        }
         
        for(DeviceClientConnectedDTO dto:deviceClient){
            clientMemory.remove(dto.getConnectId());
        }
    }
    
    public void addClientToDevice(UUID deviceId, String vlanIp, String macAddress){
        DeviceClientConnectedDTO clientDTO = new DeviceClientConnectedDTO();
        clientDTO.setConnectId(UUID.randomUUID());
        clientDTO.setDeviceId(deviceId);
        clientDTO.setMacAddress(macAddress);
        clientDTO.setIp(vlanIp);
        clientDTO.setTime(System.currentTimeMillis());
        
        DeviceClientConnectedMemory clientMemory = DeviceClientConnectedMemory.getInstance();
        clientMemory.add(clientDTO);
        
        DeviceClientConnectedAnalytics.onAddClientToDevice(clientDTO);
    }
    
    public void removeClientToDevice(UUID deviceId, String vlanIp, String macAddress){
        DeviceClientConnectedMemory clientMemory = DeviceClientConnectedMemory.getInstance();
        List<DeviceClientConnectedDTO> allOperation = clientMemory.findAll();
        
        DeviceClientConnectedDTO clientDTO = null;
        for(DeviceClientConnectedDTO dto:allOperation){
           if(dto.getDeviceId().equals(deviceId)){
               if(dto.getIp().equals(vlanIp)){
                   clientDTO = dto;
                   break;
               }
           }
        }
        
        if(clientDTO!=null){
            clientDTO.setMacAddress(macAddress);
            clientMemory.remove(clientDTO.getConnectId());
            DeviceClientConnectedAnalytics.onRemoveClientToDevice(clientDTO);
        }
    }
}
