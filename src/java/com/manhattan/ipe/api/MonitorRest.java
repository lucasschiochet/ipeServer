/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manhattan.ipe.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


@Path("/monitor")
public class MonitorRest {
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
}