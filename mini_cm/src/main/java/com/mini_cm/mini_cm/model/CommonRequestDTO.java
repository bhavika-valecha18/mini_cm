package com.mini_cm.mini_cm.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommonRequestDTO
{
private String customerId;
    private String browser;
    private String country;
    private String uuid;
    private String adTagId;
    private String device;

    @Override
    public String toString()
    {
        return "CommonRequestDTO{" +
                "customerId='" + customerId + '\'' +
                ", browser='" + browser + '\'' +
                ", country='" + country + '\'' +
                ", uuid='" + uuid + '\'' +
                ", adTagId='" + adTagId + '\'' +
                ", device='" + device + '\'' +
                '}';
    }
//   private HashMap<Enum,String> requestDTO=new HashMap<>();
//
//    public CommonRequestDTO(){
//        for(CommonRequestDataSet params:CommonRequestDataSet.values())   {
//            requestDTO.put(params,null);
//        }
//    }
//
//    public void getRequestData(){
//        HashMap<Enum,String> resultSet=new HashMap<>();
//        resultSet.putAll(this.requestDTO);
//    }
//
//    public  void set_key_in_value(Enum key,String value){
//        requestDTO.put(key,value);
//    }

}
