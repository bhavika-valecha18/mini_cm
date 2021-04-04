package com.mini_cm.mini_cm.service;


import com.mini_cm.mini_cm.model.Data;
import com.mini_cm.mini_cm.model.ListingAds;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseDataService
{
    @Autowired
    private XmlParsingService xmlParsingService;
    RestTemplate restTemplate = new RestTemplate();
    public JSONObject getData(Data data, String lid) throws JSONException
    {
        DefaultUriBuilderFactory uriFactory = new DefaultUriBuilderFactory();
        uriFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        restTemplate.setUriTemplateHandler(uriFactory);

      String  kbb= "http://c8-kbb-api.srv.media.net:8989/kbb/keyword_api.php?" +
                "d="+data.getDomain()+ "&cc=US&kf=0&pt=60&type=1&uftr=0&kwrd=0&py=1&" +
                 "dtld="+data.getDtld()+"&combineExpired=1&mcat=4067&fpid=800200068&" +
                "maxno=25&actno=20&ykf=1&" +
                "rurl="+data.getRurl() +
                "&pid=8POEPJG4R&lmsc=0&"+"hint="+data.getHint()+"&hs=3&partnerid=7PRFT79UO&stag=mnet_mobile435_search&stags=mnet_mobile435_search%2Cskenzo_search11%2C365657&" +
                "pstag=skenzo_search11&mtags=hypecmdm%2Cperform&crid=147984832&" +
                "pmp=us&https=1&accrev=1&csid=8CU2T3HV4&ugd=4&rand=1526919391&" +
                "tsize=800x200&calling_source=cm&fm_mo=10&fm_bt=1&bkt_block=344&stag_tq_block=1&json=1";
      if(lid!=null){
          kbb+="&lid="+lid;
      }else{
          kbb+="&lid=224";
      }

        ResponseEntity<String> response = restTemplate.getForEntity(kbb,String.class);
       String user=response.getBody();
        JSONObject kbb_response = new JSONObject(user);

        System.out.println("hii:"+kbb_response);
            return kbb_response;

    }

    public List<ListingAds> getSerpData(){
        List<ListingAds> lists = new ArrayList<ListingAds>();
        xmlParsingService.xmlData();
        List<String> titles= xmlParsingService.getTitle();
        List<String> descriptions= xmlParsingService.getDescription();
        List<String> urls= xmlParsingService.getUrl();

        for(int i=0;i<titles.size();i++){
            lists.add(new ListingAds(titles.get(i),descriptions.get(i),urls.get(i)));

        }
        return lists;
    }




}
