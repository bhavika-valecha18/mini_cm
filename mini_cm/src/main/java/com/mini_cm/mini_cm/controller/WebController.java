package com.mini_cm.mini_cm.controller;


import com.mini_cm.mini_cm.model.CommonRequestDTO;
import com.mini_cm.mini_cm.model.KbbRequestData;
import com.mini_cm.mini_cm.model.RequestQueryParams;
import com.mini_cm.mini_cm.service.LogDataService;
import com.mini_cm.mini_cm.service.KbbHTMLResponseService;
import com.mini_cm.mini_cm.service.ResponseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    final String URL="rurl";
    final String SESSION_ID="cookie";
    final String KEYWORD_SELECTED="keywordSelected";
    final String LISTING_ADS="listingAds";

    CommonRequestDTO commonRequestDTO =null;





    //on keyword click
    @RequestMapping(method=RequestMethod.GET,value ="/keywordsClicked")
    public String get(@ModelAttribute RequestQueryParams params) {
        logDataService.logKeywordClick(params.getUuid(),params.getBrowser(),params.getCountry(),params.getCid(),params.getAdTagId(),params.getPublisher_url(),params.getKeyword());
        return "redirect:/ads?uuid="+params.getUuid()+"&keyword="+params.getKeyword()+"&publisher_url="+params.getPublisher_url();
    }



    @GetMapping(value = "/ads")
    public String advertisements(@ModelAttribute RequestQueryParams params, Model model) {

        model.addAttribute(URL,params.getPublisher_url());
        model.addAttribute(SESSION_ID,params.getUuid());
        model.addAttribute(KEYWORD_SELECTED,params.getKeyword());
        try
        {
            model.addAttribute(LISTING_ADS, responseDataService.getSerpResponseData());
        }catch (Exception e){
            System.out.println(e);
        }
        return "ads";

    }


    //request to kbb
    @PostMapping(value = "/data", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public  String  responseKeyword(@RequestBody KbbRequestData kbbRequestData){

        return kbbHTMLResponseService.getHTMLResponse(kbbRequestData, commonRequestDTO);

    }



    @GetMapping(value = "/index.js{cid}")
    public String cid(@PathParam("cid") String cid){

        return "static/js/index.js";
    }

    @GetMapping(value = "/adTagId{cid}")
    @ResponseBody
    public String index(@PathParam("cid") String cid){
        //bidding process
        return "f1898";
    }




    @GetMapping(value="/log")
    public @ResponseBody void logging(@ModelAttribute RequestQueryParams params)
    {

       switch (Integer.parseInt(params.getAuditKey())){
           case 0:

                  logDataService.logPageView(params.getUuid(),params.getBrowser(),params.getCountry(),params.getTimestamp(),params.getCid(),params.getAdTagId(),params.getPublisher_url(),params.getViewability(),params.getKeyword());
                 commonRequestDTO =new CommonRequestDTO(params.getCid(),params.getBrowser(),params.getCountry(),params.getUuid(),params.getAdTagId(),params.getDevice());
                  break;

           case 1:
                logDataService.logAdsDisplay(params.getUuid(),params.getBrowser(),params.getCountry(),params.getTimestamp(),params.getCid(),params.getAdTagId(),params.getPublisher_url(),params.getAdsDisplay(),params.getKeyword());
                break;

       }

    }

    @GetMapping(value="/adClickData")
    @ResponseBody
    public String redirectAd(@ModelAttribute RequestQueryParams params){
        logDataService.logAdClick(params.getUuid(),params.getBrowser(),params.getCountry(),params.getTimestamp(),params.getCid(),params.getAdTagId(),params.getPublisher_url(),params.getAdTitle(),params.getKeyword());
       return params.getAdUrl();

    }




}
