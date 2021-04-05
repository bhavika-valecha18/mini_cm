package com.mini_cm.mini_cm.controller;


import com.mini_cm.mini_cm.model.CommonRequestDTO;
import com.mini_cm.mini_cm.model.KbbRequestData;
import com.mini_cm.mini_cm.model.RequestQueryParams;
import com.mini_cm.mini_cm.model.SerpLogAndDisplay;
import com.mini_cm.mini_cm.service.KbbHTMLResponseService;
import com.mini_cm.mini_cm.service.LogDataService;
import com.mini_cm.mini_cm.service.ResponseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
public class WebController
{
    @Autowired
    private ResponseDataService responseDataService;

    @Autowired
    private LogDataService logDataService;

    @Autowired
    private KbbHTMLResponseService kbbHTMLResponseService;



   public CommonRequestDTO commonRequestDTO =null;


    @GetMapping(value = "/index.js{cid}")
    public String fetchJsScript(@PathParam("cid") String cid){
        return "static/js/index.js";
    }

    @GetMapping(value = "/adTagId{cid}")
    @ResponseBody
    public String getAdtagId(@PathParam("cid") String cid){
        //bidding process
        return "f1898";
    }

    @GetMapping(value="/log")
    public @ResponseBody void logData(@ModelAttribute RequestQueryParams params,HttpServletRequest request)
    {


        switch (params.getAuditKey()){
            case 0:

                logDataService.logPageView(params.getUuid(),params.getCountry(),params.getTimestamp(),params.getCid(),params.getAdTagId(),params.getPublisher_url(),params.getViewability(),params.getKeyword(),params.getUserAgent());
                commonRequestDTO =new CommonRequestDTO(params.getCid(),logDataService.getBrowser(params.getUserAgent()),params.getCountry(),params.getUuid(),params.getAdTagId(), logDataService.getDevice(params.getUserAgent()));
                break;

            case 1:
                logDataService.logAdsDisplay(params.getUuid(),params.getCountry(),params.getTimestamp(),params.getCid(),params.getAdTagId(),params.getPublisher_url(),params.getAdsDisplay(),params.getKeyword());
                break;

        }

    }

    //request to kbb
    @PostMapping(value = "/kbbRequest", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public  String getKbbResponse(@RequestBody KbbRequestData kbbRequestData){
        return kbbHTMLResponseService.getHTMLResponse(kbbRequestData, commonRequestDTO);
    }

    //on keyword click
    @GetMapping(value ="/serpPageRedirect")
    public String serpPageRedirect(@ModelAttribute RequestQueryParams params) {
        logDataService.logKeywordClick(params.getUuid(),params.getCountry(),params.getCid(),params.getAdTagId(),params.getPublisher_url(),params.getKeyword());
        return "redirect:/ads?uuid="+params.getUuid()+"&keyword="+params.getKeyword()+"&publisher_url="+params.getPublisher_url();
    }



    @GetMapping(value = "/ads")
    public String serpPage(@ModelAttribute RequestQueryParams params, Model model) {

        model.addAttribute(SerpLogAndDisplay.URL.value,params.getPublisher_url());
        model.addAttribute(SerpLogAndDisplay.SESSION_ID.value,params.getUuid());
        model.addAttribute(SerpLogAndDisplay.KEYWORD_SELECTED.value,params.getKeyword());
        try
        {
            model.addAttribute(SerpLogAndDisplay.LISTING_ADS.value, responseDataService.getSerpResponseData());
        }catch (Exception e){
            System.out.println(e);
        }
        return "serpPage";

    }

    @GetMapping(value="/adClickData")
    @ResponseBody
    public String redirectAd(@ModelAttribute RequestQueryParams params){
        logDataService.logAdClick(params.getUuid(),params.getCountry(),params.getTimestamp(),params.getCid(),params.getAdTagId(),params.getPublisher_url(),params.getAdTitle(),params.getKeyword());
       return params.getAdUrl();

    }

}
