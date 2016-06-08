<%-- 
    Document   : report
    Created on : Mar 26, 2016, 12:35:54 AM
    Author     : lucasschiochet
--%>

<%@page import="com.manhattan.ipe.dto.DeviceClientConnectedDTO"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.manhattan.ipe.dto.DeviceConnectDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.manhattan.ipe.service.DeviceClientConnectedService"%>
<%@page import="com.manhattan.ipe.service.DeviceConnectService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ipe</title>
    </head>
    <body>
        <h1>Ipe usage Report</h1>
        <%
            DeviceConnectService connectService = new DeviceConnectService();
            DeviceClientConnectedService clientConnectedService = new DeviceClientConnectedService();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
            
            List<DeviceConnectDTO> deviceList = connectService.findAll();
            if(deviceList!=null && deviceList.size()>0){
                for(DeviceConnectDTO connectDTO:deviceList){
                   
                    Date attachDate = new Date(connectDTO.getTime());
                    out.print("<br>");
                    out.print("<table>");
                    out.println("<tr><td>deviceId</td><td>imei</td><td>ip</td><td>time</td></tr>");
                 
                    out.println("<tr>");
                    out.println("<td>"+connectDTO.getDeviceId().toString()+"</td>");
                    out.println("<td>"+connectDTO.getImei()+"</td>");
                    out.println("<td>"+connectDTO.getIp()+"</td>");
                    out.println("<td>"+dateFormatter.format(attachDate)+"</td>");
                    out.println("</tr></table>"); 
                    
                    out.println("<br>");
                            
                    List<DeviceClientConnectedDTO> clientList = clientConnectedService.findAllByDevice(connectDTO.getDeviceId());
                    if(clientList!=null && clientList.size()>0){
                          out.print("<table>");
                          out.println("<tr><td>deviceId</td><td>macAddress</td><td>ip</td><td>time</td></tr>");
                          
                          for(DeviceClientConnectedDTO clientDTO:clientList){
                              Date clientAttachDate = new Date(clientDTO.getTime());
                              out.println("<tr>");
                              out.println("<td>"+clientDTO.getDeviceId().toString()+"</td>");
                              out.println("<td>"+clientDTO.getMacAddress()+"</td>");
                              out.println("<td>"+clientDTO.getIp()+"</td>");
                              out.println("<td>"+dateFormatter.format(clientAttachDate)+"</td>");
                              out.println("</tr>"); 
                          }
                          
                          out.println("</table>"); 
                    }else{
                        out.print("Ninguem conectado");
                    }
                    
                    out.println("<br>------------------------------------------------<br>"); 


                }
            }else{
                out.print("<br><h2>Nenhum dados</h2>");
            }
            
        %>
    </body>
</html>
