package com.mini_cm.mini_cm.service;

import com.mini_cm.mini_cm.model.AttributeSet;
import com.mini_cm.mini_cm.model.CommonRequestDTO;
import com.mini_cm.mini_cm.model.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class PubKeywordsAttributesStitchingService
{
    @Autowired
    private ResponseDataService responseDataService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private LogDataService logDataService;

    public String finalResponseLogging(Data data, CommonRequestDTO commonRequestDTO){

        //override

        entityService.callEntities(commonRequestDTO, data.getAuthor());
        try{
            JSONObject kbbResponse=null;
            HashMap<Enum,String> finalSet=entityService.getFinalSet();
            kbbResponse= responseDataService.getData(data,finalSet.get(AttributeSet.LID));

            //list of keywords
            ArrayList<String> keywords=new ArrayList<>();
            int keyword_count=Integer.parseInt(finalSet.get(AttributeSet.KEYWORD_COUNT));
            JSONArray arr=kbbResponse.getJSONArray("r");
            JSONArray keyword=arr.getJSONArray(0);
            JSONObject lists=keyword.getJSONObject(0);
            JSONArray keyword_arr=lists.getJSONArray("bg");

            for(int i=0;i<keyword_count;i++){

                JSONObject bg=keyword_arr.getJSONObject(i);
                JSONArray k=bg.getJSONArray("k");
                JSONObject finalKeyword=k.getJSONObject(0);
                String keyword_title=finalKeyword.getString("t");
                keywords.add(keyword_title);


            }
           String result= HTMLResponse(keywords,commonRequestDTO,data.getRurl(),finalSet.get(AttributeSet.BACKGROUND_COLOR),finalSet.get(AttributeSet.FONT_NAME),finalSet.get(AttributeSet.FONT_STYLE));
            logDataService.logActionSet(commonRequestDTO,data.getRurl(),finalSet,entityService.sectionId());
            if(Integer.parseInt(finalSet.get(AttributeSet.KEYWORD_BLOCK))!=1)
            {
                return result;
            }else{
                return "";
            }
        }
        catch (Exception e){

            logDataService.logKeywordsLoadFailure(commonRequestDTO,data.getRurl());
        }
        return null;


    }

    private String HTMLResponse(ArrayList<String> keywords,CommonRequestDTO commonRequestDTO,String rurl,String color,String font_name,String font_style) throws JSONException
    {

        String style="style="+'"'+"background-color:"+color+";"+"font-style:"+font_style+";"+"font-family:"+font_name+";"+'"';
        String box="<div "+style+" id="+'"'+"keyword-box"+'"'+" >\n";
        String heading="<div> RELATED TOPICS </div>\n";
        String ul="<ul "+"id="+'"'+"ad-elements"+'"'+" >\n";
        String htmlResponse=box+heading+ul;
        for(String keyword:keywords){

            String requestUrl="http://localhost:8080//keywordsClicked?uuid="+commonRequestDTO.getUuid()+"&keyword="+keyword.replace(" ","-")+"&country="+commonRequestDTO.getCountry()+"&adTagId="+commonRequestDTO.getAdTagId()+"&browser="+commonRequestDTO.getBrowser()+"&cid="+commonRequestDTO.getCustomerId()+"&publisher_url="+rurl;
            htmlResponse+="<li>"+"<a "+"href="+'"'+requestUrl+'"'+" target="+'"'+"_blank"+'"'+" >"+keyword+"</a></li>\n";
        }
        htmlResponse+=" </ul>\n</div>\n";


        return htmlResponse;
    }
}
