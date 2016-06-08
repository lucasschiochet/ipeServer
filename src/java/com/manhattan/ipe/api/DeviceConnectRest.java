/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.manhattan.ipe.api;
import com.manhattan.ipe.dto.DeviceConnectDTO;
import com.manhattan.ipe.log.IpeLog;
import com.manhattan.ipe.service.DeviceConnectService;
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
/**
 *
 * @author lucasschiochet
 */
@Path("/deviceConnect")
public class DeviceConnectRest {
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
    @Path("/connectDevice")
    @Produces(MediaType.TEXT_PLAIN)
    public String connectDevice( @FormParam("imei") String imei,
           @FormParam("ssidName") String ssidName ){
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
 
        String ipAddress = httpRequest.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = httpRequest.getRemoteAddr();
        }
        if(imei==null || imei.isEmpty()){
            return "NOK";
        }
        
        if(ssidName==null || ssidName.isEmpty()){
            ssidName = "";
        }
        
        IpeLog.log("Connect Device - IP:" + ipAddress);
        DeviceConnectService connectService = new DeviceConnectService();
        DeviceConnectDTO connectDTO = connectService.findByImei(imei);
        if(connectDTO!=null){
            if(connectDTO.getIp().equals(ipAddress)){
                IpeLog.log("Connect Device - device was already connected IP:" + ipAddress);
                return connectDTO.getAccessToken().toString();
            }else{
                connectService.remove(connectDTO);
            }
        }
        connectDTO = connectService.insert(imei, ipAddress, ssidName);
        IpeLog.log("Connect Device - sucessiful connected IP:" + ipAddress + " imei:" + imei);
        return connectDTO.getAccessToken().toString();
    }
    
    @POST
    @Path("/disconnectDevice")
    @Produces(MediaType.TEXT_PLAIN)
    public String disconnectDevice( @FormParam("imei") String imei){
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
   
        IpeLog.log("Disconnect Device - imei:" + imei);
        DeviceConnectService connectService = new DeviceConnectService();
        DeviceConnectDTO connectDTO = connectService.findByImei(imei);
        if(connectDTO!=null){
            connectService.remove(connectDTO);

            IpeLog.log("Disconnect Device - success - imei:" + imei);
        }
        
        return "OK";
    }
    
}
