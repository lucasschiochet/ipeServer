package com.manhattan.ipe.memory;

import com.manhattan.ipe.dto.DeviceConnectDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lucasschiochet
 */
public class DeviceConnectMemory {
    private static DeviceConnectMemory deviceConnectMemory;

    private List<DeviceConnectDTO> deviceConnectList;
    
    public DeviceConnectMemory() {
        deviceConnectList = new ArrayList<>();
    }
     
    public static DeviceConnectMemory getInstance(){
        if(deviceConnectMemory == null){
            deviceConnectMemory = new DeviceConnectMemory();
        }
        return deviceConnectMemory;
    }

    public void clear(){
        deviceConnectList = new ArrayList<>();
    }
    
    public DeviceConnectDTO findById(UUID id){
        DeviceConnectDTO ret = null;
        for(DeviceConnectDTO dto:deviceConnectList){
            if(dto.getDeviceId().equals(id)){
                ret = dto;
                break;
            }
        }
        return ret;
    }
    
    public List<DeviceConnectDTO> findAll(){
        return deviceConnectList;
    }
    
    public void add(DeviceConnectDTO dto){
        if(dto!=null){
            deviceConnectList.add(dto);
        }
    }

    public void remove(UUID id) {
        if (id != null) {
            DeviceConnectDTO ret = null;
            for (DeviceConnectDTO d : deviceConnectList) {
                if (id.equals(d.getDeviceId())) {
                    ret = d;
                    break;
                }
            }
            if(ret!=null){
                deviceConnectList.remove(ret);
            }
        }
    }
    
    public void update(DeviceConnectDTO dto){
        if(dto!=null){
            remove(dto.getDeviceId());
            add(dto);
        }
    }
    
}
