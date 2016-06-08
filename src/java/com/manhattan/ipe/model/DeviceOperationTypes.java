/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.manhattan.ipe.model;

/**
 *
 * @author lucasschiochet
 */
public enum DeviceOperationTypes {
    ASK_ACCESS(1), ALLOW_ACCESS(2), BLOCK_ACCESS(3), AVAILABILITY_CHECK(4),REDIRECT_TO_ADS(5);
    
    private int type;

    private DeviceOperationTypes(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
    
}
