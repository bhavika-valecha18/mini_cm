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



    public void logPageView(String uuid,String browser, String country,String timestamp,String cid,String adTagId,String publisher_url,int viewability,String keywords){
        logDataRepositoryInterface.savePublisher(new LogData(Long.parseLong(uuid),browser,country,timestamp,cid,adTagId,publisher_url),viewability,keywords);
    }

    public void logKeywordClick(String uuid,String browser, String country,String cid,String adTagId,String publisher_url,String keyword){
        String keywordTitle=keyword.replace("-"," ");
        logDataRepositoryInterface.saveKeyword(new LogData(Long.parseLong(uuid),browser,country,getTimeStamp(),cid,adTagId,publisher_url),keywordTitle);
    }

    public void logKeywordsLoadFailure(CommonRequestDTO commonRequestDTO, String publisher_url){
        logDataRepositoryInterface.saveKeywordNotLoad(new LogData(Long.parseLong(commonRequestDTO.getUuid()), commonRequestDTO.getBrowser(), commonRequestDTO.getCountry(),getTimeStamp(), commonRequestDTO.getCustomerId(), commonRequestDTO.getAdTagId(),publisher_url));
    }

    public void logAdsDisplay(String uuid,String browser, String country,String timestamp,String cid,String adTagId,String publisher_url,String[] ads,String keyword_title){
        String keywordTitle=keyword_title.replace("-"," ");
        for(int i=0;i<ads.length;i++){
            logDataRepositoryInterface.saveAds(new LogData(Long.parseLong(uuid),browser,country,timestamp,cid,adTagId,publisher_url),ads[i],keywordTitle);
        }
    }

    public void logAdClick(String uuid,String browser, String country,String timestamp,String cid,String adTagId,String publisher_url,String adTitle,String keywordSelected){
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

}
