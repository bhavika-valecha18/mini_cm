package com.mini_cm.mini_cm.forbes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class LogData
{
    private long uuid;
    private String browser;
    private String country;
    private String timestamp;
    private String cid;
    private String ad_tag_id;
    private String publisher_url;

    @Override
    public String toString()
    {
        return "LogData{" +
                "uuid=" + uuid +
                ", browser='" + browser + '\'' +
                ", country='" + country + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", cid='" + cid + '\'' +
                ", ad_tag_id='" + ad_tag_id + '\'' +
                ", publisher_url='" + publisher_url + '\'' +
                '}';
    }





}
