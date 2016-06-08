/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manhattan.ipe.analytics;

import java.util.Map;
import com.mixpanel.mixpanelapi.ClientDelivery;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import org.json.JSONObject;
/**
 *
 * @author lucasschiochet
 */
public class MixpanelSender {

    public static void sendEvent(String event, Map<String, Object> properties) throws IOException {
        MessageBuilder messageBuilder
                = new MessageBuilder(MixpanelToken.token);
        JSONObject props = new JSONObject();
        
        String sTime = "";
        String ip = "";
        for(String key:properties.keySet()){
            props.put(key, properties.get(key).toString());
            if(key.equalsIgnoreCase("time")){
                sTime = properties.get(key).toString();
            }
            
            if(key.equalsIgnoreCase("ip")){
                ip = properties.get(key).toString();
            }
            
        }
        
        Long cTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
        String aTime = dateFormat.format(new Date(System.currentTimeMillis()));
        props.put("FormattedTimeAction", aTime);
        if(sTime!=null && !sTime.isEmpty()){
            try{
                Long t = Long.parseLong(sTime);
                String s = dateFormat.format(new Date(t));
                props.put("FormattedTimeStarted", s);
                Long diff = cTime - t;
                diff = diff/1000;
                String sDiff = convertSecondsToDescr(diff);
                props.put("FormattedTimeDuration", sDiff);
            }catch(Exception ers){
                ers.printStackTrace();
            }
        }
        
        if(ip!=null && !ip.isEmpty()){
             props.put("FormattedIp", ip);
        }
        
        JSONObject sentEvent
                = messageBuilder.event(UUID.randomUUID().toString(), event, props);
        ClientDelivery delivery = new ClientDelivery();
        delivery.addMessage(sentEvent);

        MixpanelAPI mixpanel = new MixpanelAPI();
        mixpanel.deliver(delivery);
    }
    
    public static String convertSecondsToDescr(Long time){
        String ret = "";
        if(time>3600){
            ret += (time/3600) + "h ";
            time = time%3600;
        }
        
        if(time>60){
            ret += (time/60) + "m ";
            time = time%60;
        }
        if(time>0){
            ret += time + "s ";
        }
        
        return ret;
    }
        
    public static void main(String...args)  throws IOException{
        HashMap<String, Object> t = new HashMap<>();
        t.put("a", "1");
        t.put("b", "2");
        new MixpanelSender().sendEvent("teste", t);
    }
    

}
