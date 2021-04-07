package com.mini_cm.mini_cm.service;

import com.mini_cm.mini_cm.dao.LogDataRepositoryInterface;
import com.mini_cm.mini_cm.model.CommonRequestDTO;
import com.mini_cm.mini_cm.model.LogData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
public class LogDataService
{
    @Autowired
    private LogDataRepositoryInterface logDataRepositoryInterface;

    private String browser="";
    private String device="";


    public void logPageView(String uuid, String country,String timestamp,String cid,String adTagId,String publisher_url,int viewability,String keywords,String userAgent){
        browser=getBrowser(userAgent);
        device=getDevice(userAgent);
       // commonRequestDTO =new CommonRequestDTO(cid,browser,country,uuid,adTagId,device);
        logDataRepositoryInterface.savePublisher(new LogData(Long.parseLong(uuid),browser,country,timestamp,cid,adTagId,publisher_url),viewability,keywords);
    }

    public void logKeywordClick(String uuid, String country,String cid,String adTagId,String publisher_url,String keyword){
        String keywordTitle=keyword.replace("-"," ");
        logDataRepositoryInterface.saveKeyword(new LogData(Long.parseLong(uuid),browser,country,getTimeStamp(),cid,adTagId,publisher_url),keywordTitle);
    }

    public void logKeywordsLoadFailure(CommonRequestDTO commonRequestDTO, String publisher_url){
        logDataRepositoryInterface.saveKeywordNotLoad(new LogData(Long.parseLong(commonRequestDTO.getUuid()), commonRequestDTO.getBrowser(), commonRequestDTO.getCountry(),getTimeStamp(), commonRequestDTO.getCustomerId(), commonRequestDTO.getAdTagId(),publisher_url));
    }

    public void logAdsDisplay(String uuid, String country,String timestamp,String cid,String adTagId,String publisher_url,String[] ads,String keyword_title){
        String keywordTitle=keyword_title.replace("-"," ");
        for(int i=0;i<ads.length;i++){

            logDataRepositoryInterface.saveAds(new LogData(Long.parseLong(uuid),browser,country,timestamp,cid,adTagId,publisher_url),ads[i],keywordTitle);
        }
    }

    public void logAdClick(String uuid, String country,String timestamp,String cid,String adTagId,String publisher_url,String adTitle,String keywordSelected){
        String keywordTitle=keywordSelected.replace("-"," ");
        logDataRepositoryInterface.saveAdClick(new LogData( Long.parseLong(uuid),browser,country,timestamp,cid,adTagId,publisher_url),adTitle,keywordTitle);
    }

    public void logActionSet(CommonRequestDTO commonRequestDTO, String url, HashMap<Enum ,String> finalSet, int section_id){

        logDataRepositoryInterface.saveAttributes(new LogData(Long.parseLong(commonRequestDTO.getUuid()), commonRequestDTO.getBrowser(), commonRequestDTO.getCountry(),getTimeStamp(), commonRequestDTO.getCustomerId(), commonRequestDTO.getAdTagId(),url),finalSet,section_id);

    }

    private String getTimeStamp(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String getDevice(String userAgent){


        //=================OS=======================
        if (userAgent.toLowerCase().indexOf("windows") >= 0 )
        {
            device = "Windows";
        } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
        {
            device = "Mac";
        } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
        {
            device = "Unix";
        } else if(userAgent.toLowerCase().indexOf("android") >= 0)
        {
            device = "Android";
        } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
        {
            device = "IPhone";
        }else{
            device = "UnKnown, More-Info: "+userAgent;
        }


        return device;
    }

    public String getBrowser(String userAgent){

        String  user  = userAgent.toLowerCase();
        //===============Browser===========================
        if (user.contains("msie"))
        {
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera"))
        {
            if(user.contains("opera"))
                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            else if(user.contains("opr"))
                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
        } else if (user.contains("chrome"))
        {

            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-").substring(0,6);
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
        {
            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
            browser = "Netscape-?";

        } else if (user.contains("firefox"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-").substring(0,7);
        } else if(user.contains("rv"))
        {
            browser="IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
        } else
        {
            browser = "UnKnown, More-Info: "+userAgent;
        }



        return browser;
    }



}
