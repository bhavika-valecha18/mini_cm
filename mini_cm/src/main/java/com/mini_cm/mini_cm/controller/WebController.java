package com.mini_cm.mini_cm.controller;


import com.mini_cm.mini_cm.model.CommonRequestDTO;
import com.mini_cm.mini_cm.model.Data;
import com.mini_cm.mini_cm.service.LogDataService;
import com.mini_cm.mini_cm.service.PubKeywordsAttributesStitchingService;
import com.mini_cm.mini_cm.service.ResponseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    private PubKeywordsAttributesStitchingService pubKeywordsAttributesStitchingService;

    final String URL="rurl";
    final String SESSION_ID="cookie";
    final String KEYWORD_SELECTED="keywordSelected";
    final String LISTING_ADS="listingAds";

    CommonRequestDTO commonRequestDTO =null;





    //on keyword click
    @RequestMapping(method=RequestMethod.GET,value ="/keywordsClicked{cookie}{keyword}{country}{adTagId}{browser}{cid}{rurl}")
    public String get(@RequestParam(value="cookie",required = false) String cookie, @RequestParam("keyword") String keyword, @RequestParam(value="country",required = false) String country, @RequestParam(value="adTagId",required = false) String adTagId, @RequestParam(value="browser",required = false) String browser, @RequestParam(value="cid",required = false) String cid, @RequestParam(value = "rurl",required=false) String rurl) {
       //todo async call
        logDataService.logKeywordClick(cookie,browser,country,cid,adTagId,rurl,keyword);
        return "redirect:/ads?cookie="+cookie+"&keyword="+keyword+"&rurl="+rurl;
    }



    @GetMapping(value = "/ads{cookie}{keyword}{rurl}")
    public String advertisements(@RequestParam(value="cookie",required = false) String cookie,@RequestParam(value = "keyword",required = false) String keyword,@RequestParam(value = "rurl",required=false) String rurl,Model model) {

        model.addAttribute(URL,rurl);
        model.addAttribute(SESSION_ID,cookie);
        model.addAttribute(KEYWORD_SELECTED,keyword);
        try
        {
            model.addAttribute(LISTING_ADS, responseDataService.getSerpData());
        }catch (Exception e){
            System.out.println(e);
        }
        return "ads";

    }


    //request to kbb
    @PostMapping(value = "/data", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public  String  responseKeyword(@RequestBody Data data){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/html; charset=UTF-8");

        return pubKeywordsAttributesStitchingService.finalResponseLogging(data, commonRequestDTO);

    }

//    @PostMapping(value = "/data")
//    @ResponseBody
//    public String responseKeyword(@RequestBody Data data){
//
//        return "<h1>hello world</h1>";
//
//    }


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



    //todo add model attribute
    //@GetMapping(value="/log?auditKey={auditkey}&uuid={uuid}&publisher_id={publisher_id}&publiser_url={publisher_url}&adtag_view={adtag_view}&adtag_loaded={adtag_loaded}&kwd_allwd={kwrd_allwd}&browser={browser}&country={browser}&timestamp={timestamp}")
    @GetMapping(value="/log{auditkey}{uuid}{cid}{publisher_url}{keyword_title}{keyword_timestamp}{adtag_view}{browser}{country}{timestamp}{ad_name}{ad_clicked}{adTagId}{ads}{keywords}{device}")
    public @ResponseBody void logging(@RequestParam(name="auditKey",required = false) String key,@RequestParam(name="uuid",required = false) String id,@RequestParam(name="cid",required = false) String cid,@RequestParam(name="publisher_url",required = false) String publisher_url,@RequestParam(name="adtag_view",required = false) Integer  view,@RequestParam(name="browser",required = false) String browser,@RequestParam(name="country",required = false) String country,@RequestParam(name="timestamp",required = false) String time,@RequestParam(name="keyword_title",required = false) String keyword_title,@RequestParam(name="ad_name",required = false) String ad_name,@RequestParam(name="ad_loaded",required = false) Integer ad_loaded,@RequestParam(name="adTagId",required = false) String adTagId,@RequestParam(value = "ads",required = false) String[] ads,@RequestParam(value = "keywords",required = false) String keywords,@RequestParam(value = "device",required = false) String device)
    {

       switch (Integer.parseInt(key)){
           case 0:
                  logDataService.logPageView(id,browser,country,time,cid,adTagId,publisher_url,view,keywords);
                  commonRequestDTO =new CommonRequestDTO(cid,browser,country,id,adTagId,device);
                  break;

           case 1:
                logDataService.logAdsDisplay(id,browser,country,time,cid,adTagId,publisher_url,ads,keyword_title);
                break;

       }

    }

    @GetMapping(value="/adClickData{uuid}{keyword}{adUrl}{time}{country}{browser}{title}{cid}{adTagId}{publisher_url}")
    @ResponseBody
    public String redirectAd( @RequestParam(value = "uuid",required = false) String uuid, @RequestParam(value = "keyword",required = false) String keywordSelected, @RequestParam(value = "adUrl",required = false) String adurl, @RequestParam(value = "time",required = false) String time, @RequestParam(value = "country",required = false) String country, @RequestParam(value = "browser",required = false) String browser, @RequestParam(value = "title",required = false) String adTitle,@RequestParam(value = "cid",required = false) String cid,@RequestParam(value = "adTagId",required = false) String adTagId,@RequestParam("publisher_url")String url){
        logDataService.logAdClick(uuid,browser,country,time,cid,adTagId,url,adTitle,keywordSelected);
       return adurl;

    }




}
