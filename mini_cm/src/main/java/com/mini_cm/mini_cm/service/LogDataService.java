package com.mini_cm.mini_cm.service;

import com.mini_cm.mini_cm.dao.LogDataRepository;
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
    private LogDataRepository logDataRepository;

//    @Autowired
//    private LogData logData;

    public void logPageView(String uuid,String browser, String country,String timestamp,String cid,String adTagId,String publisher_url,int viewability,String keywords){
        logDataRepository.savePublisher(new LogData(Long.parseLong(uuid),browser,country,timestamp,cid,adTagId,publisher_url),viewability,keywords);
    }

    public void logKeywordClick(String uuid,String browser, String country,String cid,String adTagId,String publisher_url,String keyword){
        String keywordTitle=keyword.replace("-"," ");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = sdf.format(date);
        logDataRepository.saveKeyword(new LogData(Long.parseLong(uuid),browser,country,formattedDate,cid,adTagId,publisher_url),keywordTitle);
    }

    public void logKeywordsLoadFailure(CommonRequestDTO commonRequestDTO, String publisher_url){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = sdf.format(date);
        logDataRepository.saveKeywordNotLoad(new LogData(Long.parseLong(commonRequestDTO.getUuid()), commonRequestDTO.getBrowser(), commonRequestDTO.getCountry(),formattedDate, commonRequestDTO.getCustomerId(), commonRequestDTO.getAdTagId(),publisher_url));
    }

    public void logAdsDisplay(String uuid,String browser, String country,String timestamp,String cid,String adTagId,String publisher_url,String[] ads,String keyword_title){
        String keywordTitle=keyword_title.replace("-"," ");
        for(int i=0;i<ads.length;i++){
            logDataRepository.saveAds(new LogData(Long.parseLong(uuid),browser,country,timestamp,cid,adTagId,publisher_url),ads[i],keywordTitle);
        }
    }

    public void logAdClick(String uuid,String browser, String country,String timestamp,String cid,String adTagId,String publisher_url,String adTitle,String keywordSelected){
        String keywordTitle=keywordSelected.replace("-"," ");
        logDataRepository.saveAdClick(new LogData( Long.parseLong(uuid),browser,country,timestamp,cid,adTagId,publisher_url),adTitle,keywordTitle);
    }

    public void logActionSet(CommonRequestDTO commonRequestDTO, String url, HashMap<Enum ,String> finalSet, int section_id){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = sdf.format(date);
        logDataRepository.saveAttributes(new LogData(Long.parseLong(commonRequestDTO.getUuid()), commonRequestDTO.getBrowser(), commonRequestDTO.getCountry(),formattedDate, commonRequestDTO.getCustomerId(), commonRequestDTO.getAdTagId(),url),finalSet,section_id);

    }

}
