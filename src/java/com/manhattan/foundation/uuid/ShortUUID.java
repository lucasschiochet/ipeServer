package com.manhattan.foundation.uuid;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Generate short UUID (13 characters)
 *
 * @return short UUID
 */
public class ShortUUID {

    public static String shortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }
    
    public static void main(String...args){
     
        List<String> list = new ArrayList<>();
        for(int i=0;i<10000;i++){
            
            String sh = ShortUUID.shortUUID();
            System.out.println(sh);
            if(!list.contains(sh)){
                list.add(sh);
            }else{;
                System.out.println("DUPLICATED");
            }
        }
    }
}
