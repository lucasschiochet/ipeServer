/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.manhattan.ipe.api;

import com.manhattan.foundation.uuid.UUIDUtils;
import com.manhattan.ipe.dto.DeviceConnectDTO;
import com.manhattan.ipe.dto.DeviceOperationDTO;
import com.manhattan.ipe.log.IpeLog;
import com.manhattan.ipe.service.DeviceConnectService;
import com.manhattan.ipe.service.DeviceOperationService;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import org.json.JSONArray;

@Path("/deviceOperation")
public class DeviceOperationRest {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Context
    private HttpServletResponse httpResponse;
    @Context
    private HttpServletRequest httpRequest;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "OK";
    }
    
    @POST
    @Path("/findDeviceOperation")
    @Produces(MediaType.TEXT_PLAIN)
    public String findDeviceOperation( @FormParam("accessToken") String accessToken){
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
        
        IpeLog.log("findDeviceOperation starting - token = " + accessToken);
        
        UUID uAccessToken = null;
        try{
            uAccessToken = UUIDUtils.convertFromString(accessToken);
        }catch(Exception ers){}
        
        if(uAccessToken==null){
            return "NOK";
        }
        DeviceConnectService connectService = new DeviceConnectService();
        DeviceConnectDTO connectDTO = connectService.findByAccessToken(uAccessToken);
        if (connectDTO != null) {
            DeviceOperationService operationService = new DeviceOperationService();
            List<DeviceOperationDTO> list = operationService.findDeviceOperation(connectDTO.getDeviceId());
            JSONArray ar = new JSONArray();
            for (DeviceOperationDTO l : list) {
                ar.put(l.toJsonObject());
            }
            String ret = ar.toString();
            IpeLog.log("findDeviceOperation returning - token = " + accessToken + " ret:" + ret);
            return ret;
        } else {
            return "NOK";
        }
    }
    
    @POST
    @Path("/connectClientDevice")
    @Produces(MediaType.TEXT_PLAIN)
    public String connectClientDevice( @FormParam("accessToken") String accessToken,
            @FormParam("vLanIp") String vlanIp,
            @FormParam("macAddress") String macAddress){
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
        
        IpeLog.log("connectClientDevice starting - token = " + accessToken + " vlan:" +vlanIp);
        
        UUID uAccessToken = null;
        try{
            uAccessToken = UUIDUtils.convertFromString(accessToken);
        }catch(Exception ers){}
        
        if(uAccessToken==null || vlanIp.isEmpty()){
            return "NOK";
        }
        DeviceConnectService connectService = new DeviceConnectService();
        DeviceConnectDTO connectDTO = connectService.findByAccessToken(uAccessToken);
        if (connectDTO != null) {
           DeviceOperationService operationService = new DeviceOperationService();
           operationService.connectClientDevice(connectDTO.getDeviceId(), vlanIp, macAddress);
           IpeLog.log("connectClientDevice success - token = " + accessToken + " vlan:" +vlanIp);
           return "OK";
        } else {
            return "NOK";
        }
    }
    
    @POST
    @Path("/disconnectClientDevice")
    @Produces(MediaType.TEXT_PLAIN)
    public String disconnectClientDevice( @FormParam("accessToken") String accessToken,
            @FormParam("vLanIp") String vlanIp,
            @FormParam("macAddress") String macAddress){
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
        
        IpeLog.log("redirectAllowed starting - token = " + accessToken + " vlan:" +vlanIp);
        
        UUID uAccessToken = null;
        try{
            uAccessToken = UUIDUtils.convertFromString(accessToken);
        }catch(Exception ers){}
        
        if(uAccessToken==null || vlanIp.isEmpty()){
            return "NOK";
        }
        DeviceConnectService connectService = new DeviceConnectService();
        DeviceConnectDTO connectDTO = connectService.findByAccessToken(uAccessToken);
        if (connectDTO != null) {
           DeviceOperationService operationService = new DeviceOperationService();
           operationService.disconnectClientDevice(connectDTO.getDeviceId(), vlanIp, macAddress);
           IpeLog.log("disconnectClientDevice success - token = " + accessToken + " vlan:" +vlanIp);
           return "OK";
        } else {
            return "NOK";
        }
    }
    
    
    @POST
    @Path("/requestAccess")
    @Produces(MediaType.TEXT_PLAIN)
    public String requestAccess( @FormParam("accessToken") String accessToken,
            @FormParam("vlanIp") String vlanIp){
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
        

        IpeLog.log("requestAccess start - server = " + accessToken + " vlan:" +vlanIp);
        
        UUID uAccessToken = UUID.fromString(accessToken);
        DeviceConnectService connectService = new DeviceConnectService();
        DeviceConnectDTO connectDTO = connectService.findByAccessToken(uAccessToken);
        if(connectDTO!=null){
            DeviceOperationService operationService = new DeviceOperationService();
            operationService.requestAccess(uAccessToken, vlanIp);
        
            for(int i=0;i<120;i++){
                
                boolean isAllowed = operationService.hasRedirectOperation(connectDTO.getDeviceId(), vlanIp);
                if(isAllowed){
                    IpeLog.log("requestAccess allowrd - server = " + accessToken + " vlan:" +vlanIp);
                    return "OK";
                }
                try{
                    Thread.sleep(500);
                }catch(Exception ers){}
            }
        }else{
            return "NOK";
        }
        IpeLog.log("requestAccess timeout - server = " + accessToken + " vlan:" +vlanIp);      
        return "NOK";
    }
}