package com.mini_cm.mini_cm.service;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class XmlParsingService
{
    List<String> title=new ArrayList<>();
    List<String> description=new ArrayList<>();
    List<String> adLink=new ArrayList<>();
    private final String path="C:\\Users\\bhavika.v\\Downloads\\mini_cm\\mini_cm\\src\\main\\resources\\AdsApi.xml";

    public void xmlData()
    {

        try
        {

            SAXReader reader = new SAXReader();
            Document document = (Document) reader.read(path);
            List<Node> nodes = document.selectNodes("/Results/ResultSet/Listing");

            for (Node node : nodes)
            {
                title.add(node.valueOf("@title"));
                description.add(node.valueOf("@description"));
                adLink.add(node.selectSingleNode("ClickUrl").getText());
            }

        } catch (DocumentException e)
        {
            e.printStackTrace();
        }
    }
    public List<String> getTitle(){
        return title;
    }
    public List<String> getDescription(){
        return description;
    }
    public List<String> getUrl(){
        return adLink;
    }


}
