/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.manhattan.ipe.memory;

import com.manhattan.ipe.dto.DeviceOperationDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lucasschiochet
 */
public class DeviceOperationMemory {
    private static DeviceOperationMemory deviceOperationMemory;

    private List<DeviceOperationDTO> deviceOperationList;
    
    public DeviceOperationMemory() {
        deviceOperationList = new ArrayList<>();
    }
     
    public static DeviceOperationMemory getInstance(){
        if(deviceOperationMemory == null){
            deviceOperationMemory = new DeviceOperationMemory();
        }
        return deviceOperationMemory;
    }
    
     public void clear(){
        deviceOperationList = new ArrayList<>();
    }
    
    public DeviceOperationDTO findById(UUID id){
        DeviceOperationDTO ret = null;
        for(DeviceOperationDTO dto:deviceOperationList){
            if(dto.getOperationId().equals(id)){
                ret = dto;
                break;
            }
        }
        return ret;
    }
    
    public List<DeviceOperationDTO> findAll(){
        return deviceOperationList;
    }
    
    public void add(DeviceOperationDTO dto){
        if(dto!=null){
            deviceOperationList.add(dto);
        }
    }

    public void remove(UUID id) {
        if (id != null) {
            DeviceOperationDTO ret = null;
            for (DeviceOperationDTO d : deviceOperationList) {
                if (id.equals(d.getOperationId())) {
                    ret = d;
                    break;
                }
            }
            if(ret!=null){
                deviceOperationList.remove(ret);
            }
        }
    }
    
    public void update(DeviceOperationDTO dto){
        if(dto!=null){
            remove(dto.getOperationId());
            add(dto);
        }
    }
}
