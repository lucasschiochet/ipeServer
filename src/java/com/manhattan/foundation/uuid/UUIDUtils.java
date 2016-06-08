/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.manhattan.foundation.uuid;

import java.util.UUID;

/**
 *
 * @author lucasschiochet
 */
public class UUIDUtils {
    public static UUID convertFromString(String s){
        s = s.replaceAll("(\\r|\\n)", "");
        s = s.trim();
        return UUID.fromString(s);
    }
}
