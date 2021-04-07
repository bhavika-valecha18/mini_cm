package com.mini_cm.mini_cm.service;

import com.mini_cm.mini_cm.model.AttributeSet;
import com.mini_cm.mini_cm.model.CommonRequestDTO;
import com.mini_cm.mini_cm.model.KbbRequestData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class KbbResponseService
{
    @Autowired
    private ResponseDataService responseDataService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private LogDataService logDataService;

    public String getHTMLResponse(KbbRequestData kbbRequestData, CommonRequestDTO commonRequestDTO){

        //final Attribute set can be cached with rule set as its key

        //override
        entityService.getActionValuesOnEntityLevel(commonRequestDTO, kbbRequestData.getAuthor());
        try{
            JSONObject kbbResponse=null;
            HashMap<Enum,String> attributeResultSet=entityService.getFinalAttributeSet();
            kbbResponse= responseDataService.getKbbResponseData(kbbRequestData,attributeResultSet.get(AttributeSet.LID));

            //list of keywords

           ArrayList<String> keywordList=getKeywordList(kbbResponse,attributeResultSet);

           String result= HTMLResponse(keywordList,commonRequestDTO, kbbRequestData.getRurl(),attributeResultSet.get(AttributeSet.BACKGROUND_COLOR),attributeResultSet.get(AttributeSet.FONT_NAME),attributeResultSet.get(AttributeSet.FONT_STYLE));

           //log attribute set
           logDataService.logActionSet(commonRequestDTO, kbbRequestData.getRurl(),attributeResultSet,entityService.sectionId());

           if(Integer.parseInt(attributeResultSet.get(AttributeSet.KEYWORD_BLOCK))!=1)
            {
                return result;
            }else{
                return "";
            }
        }
        catch (Exception e){
            //log kbb response failure
            logDataService.logKeywordsLoadFailure(commonRequestDTO, kbbRequestData.getRurl());
        }
        return null;


    }

    private ArrayList<String> getKeywordList(JSONObject kbbResponse,HashMap<Enum,String> attributeResultSet) throws JSONException
    {
        ArrayList<String> keywordList=new ArrayList<>();
        int keywordCount=Integer.parseInt(attributeResultSet.get(AttributeSet.KEYWORD_COUNT));
        JSONArray jsonResultArray1=kbbResponse.getJSONArray("r");
        JSONArray jsonResultArray2=jsonResultArray1.getJSONArray(0);
        JSONObject jsonResultObject1=jsonResultArray2.getJSONObject(0);
        JSONArray jsonResultArray3=jsonResultObject1.getJSONArray("bg");

        for(int i=0;i<keywordCount;i++){

            JSONObject bg=jsonResultArray3.getJSONObject(i);
            JSONArray k=bg.getJSONArray("k");
            JSONObject finalKeyword=k.getJSONObject(0);
            String keywordTitle=finalKeyword.getString("t");
            keywordList.add(keywordTitle);


        }
        return keywordList;
    }

    private String HTMLResponse(ArrayList<String> keywords,CommonRequestDTO commonRequestDTO,String rurl,String color,String font_name,String font_style) throws JSONException
    {

        String style="style="+'"'+"background-color:"+color+";"+"font-style:"+font_style+";"+"font-family:"+font_name+";"+'"';
        String keywordBoxDivElement="<div "+style+" id="+'"'+"keyword-box"+'"'+" >\n";
        String headingElement="<div> RELATED TOPICS </div>\n";
        String listElement="<ul "+"id="+'"'+"ad-elements"+'"'+" >\n";
        String htmlResponse=keywordBoxDivElement+headingElement+listElement;
        for(String keyword:keywords){

            String requestUrl="http://localhost:8080//serpPageRedirect?uuid="+commonRequestDTO.getUuid()+"&keyword="+keyword.replace(" ","-")+"&country="+commonRequestDTO.getCountry()+"&adTagId="+commonRequestDTO.getAdTagId()+"&browser="+commonRequestDTO.getBrowser()+"&cid="+commonRequestDTO.getCustomerId()+"&publisher_url="+rurl;
            htmlResponse+="<li>"+"<a "+"href="+'"'+requestUrl+'"'+" target="+'"'+"_blank"+'"'+" >"+keyword+"</a></li>\n";
        }
        htmlResponse+=" </ul>\n</div>\n";


        return htmlResponse;
    }
}
