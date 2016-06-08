package com.manhattan.ipe.memory;

import com.manhattan.ipe.dto.DeviceClientConnectedDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lucasschiochet
 */
public class DeviceClientConnectedMemory {
    private static DeviceClientConnectedMemory deviceConnectConnectedMemory;

    private List<DeviceClientConnectedDTO> deviceClientConnectedList;
    
    public DeviceClientConnectedMemory() {
        deviceClientConnectedList = new ArrayList<>();
    }
     
    public static DeviceClientConnectedMemory getInstance(){
        if(deviceConnectConnectedMemory == null){
            deviceConnectConnectedMemory = new DeviceClientConnectedMemory();
        }
        return deviceConnectConnectedMemory;
    }

    public void clear(){
        deviceClientConnectedList = new ArrayList<>();
    }
    
    public DeviceClientConnectedDTO findById(UUID id){
        DeviceClientConnectedDTO ret = null;
        for(DeviceClientConnectedDTO dto:deviceClientConnectedList){
            if(dto.getConnectId().equals(id)){
                ret = dto;
                break;
            }
        }
        return ret;
    }
    
    public List<DeviceClientConnectedDTO> findAll(){
        return deviceClientConnectedList;
    }
    
    public void add(DeviceClientConnectedDTO dto){
        if(dto!=null){
            deviceClientConnectedList.add(dto);
        }
    }

    public void remove(UUID id) {
        if (id != null) {
            DeviceClientConnectedDTO ret = null;
            for (DeviceClientConnectedDTO d : deviceClientConnectedList) {
                if (id.equals(d.getConnectId())) {
                    ret = d;
                    break;
                }
            }
            if(ret!=null){
                deviceClientConnectedList.remove(ret);
            }
        }
    }
    
    public void update(DeviceClientConnectedDTO dto){
        if(dto!=null){
            remove(dto.getConnectId());
            add(dto);
        }
    }
    
}
