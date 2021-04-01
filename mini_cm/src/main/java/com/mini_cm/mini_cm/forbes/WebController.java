package com.mini_cm.mini_cm.forbes;


import com.mini_cm.mini_cm.service.EntityService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller

public class WebController
{
    @Autowired
    private ResponseData responseData;

    @Autowired
    private LogDataRepository logDataRepository;

    @Autowired
    private LogData logData;

    @Autowired
    private Action action;

    @Autowired
    private EntityService entityService;



    @Autowired
    private XmlParsing xmlParsing;
    Boolean count=false;
    boolean check=true;

    List response=new ArrayList<>();


    //on keyword click
    @RequestMapping(method=RequestMethod.GET,value ="/keywordsClicked{cookie}{keyword}{country}{adTagId}{browser}{cid}{rurl}")
    public String get(@RequestParam(value="cookie",required = false) String cookie, @RequestParam("keyword") String keyword, @RequestParam(value="country",required = false) String country, @RequestParam(value="adTagId",required = false) String adTagId, @RequestParam(value="browser",required = false) String browser, @RequestParam(value="cid",required = false) String cid, @RequestParam(value = "rurl",required=false) String rurl) {
        System.out.println("bhavika"+keyword+cookie);
        String keywordTitle=keyword.replace("-"," ");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = sdf.format(date);
        logDataRepository.saveKeyword(new LogData(Long.parseLong(cookie),browser,country,formattedDate,cid,adTagId,rurl),keywordTitle);
        return "redirect:/ads?cookie="+cookie+"&keyword="+keyword+"&rurl="+rurl;
    }

    //redirect to serp page
    @RequestMapping(method = RequestMethod.GET,path = "/serp/{cookie}/{keyword}/{rurl}")
    public String serp(@RequestParam(value="cookie",required = false) String cookie,@RequestParam(value="keyword",required = false) String keyword,@RequestParam(value = "rurl",required=false) String rurl){
        //System.out.println("bhavika 1"+cookie+keyword+rurl);
        return "redirect:/ads{cookie}{keyword}{rurl}";

    }


    @GetMapping(value = "/ads{cookie}{keyword}{rurl}")
    public String advertisements(@RequestParam(value="cookie",required = false) String cookie,@RequestParam(value = "keyword",required = false) String keyword,@RequestParam(value = "rurl",required=false) String rurl,Model model) {
        System.out.println("inside bhavika"+keyword+"cookie:"+cookie);
        List<ListingAds> lists = new ArrayList<ListingAds>();
        List<ListingAds> list1 = new ArrayList<ListingAds>();
        xmlParsing.xmlData();
        List<String> titles=xmlParsing.getTitle();
        List<String> descriptions=xmlParsing.getDescription();
        List<String> urls=xmlParsing.getUrl();

        for(int i=0;i<titles.size();i++){
            lists.add(new ListingAds(titles.get(i),descriptions.get(i),urls.get(i)));

        }
        model.addAttribute("rurl",rurl);
        model.addAttribute("cookie",cookie);
        model.addAttribute("keywordSelected",keyword);
        model.addAttribute("listingAds",lists);


        return "ads";

    }


    //request to kbb
    @RequestMapping(method= RequestMethod.POST,value = "/data")
    @ResponseBody
    public String responseKeyword(@RequestBody Data data){

        RuleObject ruleObject=new RuleObject(data.getCountry(),data.getBrowser(),data.getDevice(),null);
        entityService.getRuleObject(ruleObject);
        TreeSet<Integer> ts1 = new TreeSet<Integer>();
        ArrayList<Integer> levels=new ArrayList<>();
        for(PriorityLevel i:PriorityLevel.values()){
            levels.add(i.level);
        }
        Collections.sort(levels);

        //fetch and override
        for(int i=0;i<levels.size();i++){
            String type=PriorityLevel.getNameByCode(levels.get(i)).toString().toLowerCase();
            entityService.getActionValuesFromSql(data.getId(type),type);
        }




       // System.out.println("enum"+PriorityLevel.getNameByCode(levels.get(0)));
        String keyword="";
        JSONObject json=null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = sdf.format(date);


        response.add(data);
        try{
//        keyword=responseData.getData(data,finalSet.getLid());
            HashMap<String,String> finalSet=new HashMap<>();
            finalSet=entityService.getFinalSet();
            System.out.println("action set"+finalSet);
            keyword=responseData.getData(data,"224");

            json = new JSONObject(keyword);
            json.put("background_color",finalSet.get("background_color"));
            json.put("keyword_count",finalSet.get("keyword_count"));
            json.put("keyword_block",finalSet.get("keyword_block"));
            json.put("font_name",finalSet.get("font_name"));
            json.put("font_style",finalSet.get("font_style"));
            json.put("lid",finalSet.get("lid"));
            Action actionLog=new Action();
            HashMap<String ,String> attributeLog=new HashMap<>();
            attributeLog=actionLog.getAttribute();
            for(String keys:attributeLog.keySet())
            {
                actionLog.set_value_in_key(keys,finalSet.get(keys));

            }
            entityService.saveAttributes(new LogData(Long.parseLong(data.getUuid()),data.getBrowser(),data.getCountry(),formattedDate,data.getCustomerId(),data.getAdTagId(),data.getRurl()),actionLog, entityService.sectionId());
            return json.toString();
        }
        catch (Exception e){
            System.out.println(e);
            logDataRepository.saveAdLoad(new LogData(Long.parseLong(data.getUuid()),data.getBrowser(),data.getCountry(),formattedDate,data.getCustomerId(),data.getAdTagId(),data.getRurl()));
        }
        System.out.println("out");
       return null;

    }

    @GetMapping(value = "/index.js{cid}{adtagid}")
    public String index(@PathParam("cid") String cid,@RequestParam(value = "adtagid",required = false) String adTagId,Model model){
        System.out.println(adTagId);
        model.addAttribute("creativeId",cid);
        model.addAttribute("adTagId",adTagId);
        return "static/js/index.js";
    }


    //@GetMapping(value="/log?auditKey={auditkey}&uuid={uuid}&publisher_id={publisher_id}&publiser_url={publisher_url}&adtag_view={adtag_view}&adtag_loaded={adtag_loaded}&kwd_allwd={kwrd_allwd}&browser={browser}&country={browser}&timestamp={timestamp}")
    @GetMapping(value="/log{auditkey}{uuid}{cid}{publisher_url}{keyword_title}{keyword_clicked}{keyword_timestamp}{adtag_view}{adtag_loaded}{kwrd_allwd}{browser}{country}{timestamp}{adview_timestamp}{ad_name}{ad_clicked}{ad_clicked_timestamp}{ad_loaded}{adTagId}{ads}{keywords}")
    public @ResponseBody void logging(@RequestParam(name="auditKey",required = false) String key,@RequestParam(name="uuid",required = false) String id,@RequestParam(name="cid",required = false) String cid,@RequestParam(name="publisher_url",required = false) String publisher_url,@RequestParam(name="adtag_view",required = false) Integer  view,@RequestParam(name="adtag_loaded",required = false) Integer load,@RequestParam(name="kwrd_allwd",required = false) Integer keywords_allowed,@RequestParam(name="browser",required = false) String browser,@RequestParam(name="country",required = false) String country,@RequestParam(name="timestamp",required = false) String time,@RequestParam(name="adview_timestamp",required = false) String adview_timestamp,@RequestParam(name="keyword_title",required = false) String keyword_title,@RequestParam(name="keyword_clicked",required = false) Integer keyword_clicked,@RequestParam(name="keyword_timestamp",required = false) String keyword_timestamp,@RequestParam(name="ad_name",required = false) String ad_name,@RequestParam(name="ad_clicked",required = false) Integer ad_clicked,@RequestParam(name="ad_clicked_timestamp",required = false) String ad_clicked_timestamp,@RequestParam(name="ad_loaded",required = false) Integer ad_loaded,@RequestParam(name="adTagId",required = false) String adTagId,@RequestParam(value = "ads",required = false) String[] ads,@RequestParam(value = "keywords",required = false) String keywords)
    {

       switch (Integer.parseInt(key)){
           case 0:
//               System.out.println("in controller publisher");
//               System.out.println(time);
             //  System.out.println(browser+country+time+id+adTagId+publisher_url);
                  logDataRepository.savePublisher(new LogData(Long.parseLong(id),browser,country,time,cid,adTagId,publisher_url),view,keywords);
                  break;

           case 1:
               String keywordTitle=keyword_title.replace("-"," ");
               for(int i=0;i<ads.length;i++){
                   logDataRepository.saveAds(new LogData(Long.parseLong(id),browser,country,time,cid,adTagId,publisher_url),ads[i],keywordTitle);
               }

               break;

       }

    }

    @GetMapping(value="/adClickData{uuid}{keyword}{adUrl}{time}{country}{browser}{title}{cid}{adTagId}{publisher_url}")
    @ResponseBody
    public String redirectAd( @RequestParam(value = "uuid",required = false) String uuid, @RequestParam(value = "keyword",required = false) String keywordSelected, @RequestParam(value = "adUrl",required = false) String adurl, @RequestParam(value = "time",required = false) String time, @RequestParam(value = "country",required = false) String country, @RequestParam(value = "browser",required = false) String browser, @RequestParam(value = "title",required = false) String adTitle,@RequestParam(value = "cid",required = false) String cid,@RequestParam(value = "adTagId",required = false) String adTagId,@RequestParam("publisher_url")String url){

        String keywordTitle=keywordSelected.replace("-"," ");

        logDataRepository.saveAdClick(new LogData( Long.parseLong(uuid),browser,country,time,cid,adTagId,url),adTitle,keywordTitle);

      return adurl;

    }




}
