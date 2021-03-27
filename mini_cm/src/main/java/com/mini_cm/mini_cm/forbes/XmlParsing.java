package com.mini_cm.mini_cm.forbes;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class XmlParsing
{
    List<String> title=new ArrayList<>();
    List<String> description=new ArrayList<>();
    List<String> adLink=new ArrayList<>();


    public void xmlData()
    {

        try
        {
            // File inputFile = new File("C:\\Users\\bhavika.v\\Downloads\\mini_cm\\mini_cm\\src\\main\\java\\com\\mini_cm\\mini_cm\\forbes\\AdsApiData.xml");
            SAXReader reader = new SAXReader();

            Document document = (Document) reader.read("C:\\Users\\bhavika.v\\Downloads\\mini_cm\\mini_cm\\src\\main\\java\\com\\mini_cm\\mini_cm\\forbes\\AdsApiData.xml");
            //System.out.println("Root element:" + ((org.dom4j.Document) document).getRootElement().getName());

            List<Node> nodes = document.selectNodes("/Results/ResultSet/Listing");

            for (Node node : nodes)
            {
                //listingAds=new ListingAds(node.valueOf("@title"),node.valueOf("@description"),node.selectSingleNode("ClickUrl").getText());
                title.add(node.valueOf("@title"));
                description.add(node.valueOf("@description"));
                adLink.add(node.selectSingleNode("ClickUrl").getText());
            }
            //System.out.println("1");


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
