package com.example.easyhome;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class TheMessage
{
    private String msg;
    private static final String YAP_SERVER_URL =
	"http://mmu-foe-capstone.appspot.com/control?group=17&msg=";
    
    public TheMessage(String msg) {
	this.msg = msg;
    }

    public String getStringUrl() {
	String url = null;
	try{
		url = YAP_SERVER_URL + URLEncoder.encode(msg, "utf-8");
		
	}catch (UnsupportedEncodingException e){
    }
	return url;
    }
}
