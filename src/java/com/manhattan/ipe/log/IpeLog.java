/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.manhattan.ipe.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lucasschiochet
 */
public class IpeLog {
    private static final Boolean enable = true;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
    
    public static void log(String content){
        if(enable){
            Long cTime = System.currentTimeMillis();
            Date cDate = new Date(cTime);
            System.out.println(dateFormatter.format(cDate) + " - " + content);
        }
    }
}
