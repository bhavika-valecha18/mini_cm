
let global_cookie="";
let global_check=true;
let keywords_arr=[];
let display_Keywords=false;
let custId="";
let adtagid="";
let country="india";
window.addEventListener("load",adScript);
let metaInfo={};

  function adScript(){

    //add custom tag for adtagid
    class Medianet extends HTMLElement{
      constructor(){
        super();
      }
    }
    customElements.define('media-net',Medianet);
    custId=document.querySelector('media-net').getAttribute('cid');
   // adtagid=await fetchAdtagId();





    //fetch all data from meta tag

    fetchMetaInfo();
     checkCookie();
      global_cookie=getCookie("uuid");



    var logData={
        uuid:getCookie("uuid"),
        cid:custId,
        publisher_url:encodeURIComponent(metaInfo["rurl"]),
        browser:metaInfo["browser"],
        country:country,
        timestamp:getTimestamp(),
        adTagId:adtagid||"f1898",
        viewability:0,
        keyword:"",
        device:metaInfo["device"]
    };
    console.log(logData);
    //log user details,fire pixel 1
    firePixel(logData,0);

    //call to server
    requestToKbb();


    $(window).bind("scroll", function() {
    console.log(adtagid);
             if($(window).scrollTop() >= $("#ad-box").offset().top -700) {
                if(global_check==true && display_Keywords==true){
                console.log("in");
                console.log(adtagid);
                    global_check=false;
                    display_Keywords=false;

                    let data={
                        uuid:getCookie("uuid"),
                        cid:custId,
                        publisher_url:encodeURIComponent(metaInfo["rurl"]),
                        browser:metaInfo["browser"],
                        country:country,
                        timestamp:getTimestamp(),
                        adTagId:adtagid||"f1898",
                        viewability:1,
                        keyword:keywords_arr.toString(),
                        device:metaInfo["device"]
                       };

                //fire pixel on keywords view
               firePixel(data,0);
             }
         }
         });
    }//end of adScript



//fetch adtagid's from custom tag
function fetchAdtagId(){
   fetch('http://localhost:8080/adTagId?cid='+custId, {mode: 'cors'})
                    .then(function(response) {
                      return response.text();
                    })
                    .then(function(text) {
                      document.querySelector('media-net').setAttribute('adtagid',text);
                      adtagid=text;
                    })
                    .catch(function(error) {
                      console.log(error);
                    });
}


//fetch info from meta tag
function fetchMetaInfo(){
    let metaTag=document.getElementsByTagName("meta");
        let author=metaTag["author"].getAttribute('content')||"";
        let ref=metaTag["referrer"].getAttribute('content')||"";
        //let url=window.location.url;
        /* todo take static url and wrap it in url object */
        let rurl=document.querySelector("meta[property='og:url']").getAttribute("content");
        let key=rurl.split('/');
        let domain=key[2].split('.');
        let domainTld=domain[2];
        let d=domain[1]+"."+domain[2];
         navigator.browserSpecs = (function()
            { var ua = navigator.userAgent,
            tem,
            M = ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
            if(/trident/i.test(M[1])){ tem = /\brv[ :]+(\d+)/g.exec(ua) || []; return {name:'IE',version:(tem[1] || '')}; }
            if(M[1]=== 'Chrome'){ tem = ua.match(/\b(OPR|Edge)\/(\d+)/); if(tem != null) return {name:tem[1].replace('OPR', 'Opera'),version:tem[2]}; }
            M = M[2]? [M[1], M[2]]: [navigator.appName, navigator.appVersion, '-?']; if((tem = ua.match(/version\/(\d+)/i))!= null) M.splice(1, 1, tem[1]); return {name:M[0], version:M[1]}; })();

            let browser=navigator.browserSpecs["name"];
             let timestamp=getTimestamp();

            /*todo useragent*/



            let device='';
            if(navigator.platform.indexOf("Win")!=-1){
            device="windows";
            }
            if(navigator.platform.indexOf("Mac")!=-1){
            device="MacOs";
            }
            if(navigator.platform.indexOf("Android")!=-1){
            device="Android";
            }

        metaInfo["author"]=author;
        metaInfo["ref"]=ref;
        metaInfo["rurl"]=rurl;
        metaInfo["domain"]=d;
        metaInfo["domainTld"]=domainTld;
        metaInfo["device"]=device;
        metaInfo["browser"]=browser;

}


//call to kbb
function requestToKbb(){
 let url='http://localhost:8080/data';
   fetch(url, {
           method: "POST",
           headers:{'Content-Type': 'application/json;charset=UTF-8'},
           body: JSON.stringify({
               author:metaInfo["author"],
               domain:metaInfo["domain"],
               rurl:metaInfo["rurl"],
               referrer:metaInfo["ref"],
               dtld:metaInfo["domainTld"],
               hint:""
           })
       }).then(response =>{
           // checking the status
           if (response.status === 200) {
               return response.text();
           }
         }).then(data => {
           displayKeywords(data);

       }).catch(err => {
           console.log("Error", err);
       });
}

 //display keyword box
 function displayKeywords(data){

        let adBox=document.getElementById("ad-box");
        adBox.innerHTML =data;
        let ul_element=document.getElementById("ad-elements");
        let len=ul_element.getElementsByTagName('li').length;
        let keyword_content=ul_element.getElementsByTagName('li');
        for(i=0;i<len;i++){
        keywords_arr.push(keyword_content[i].querySelector('a').innerHTML);
        }
        display_Keywords=true;
 }//end of displayKeywords


//cookie generation
function setCookie() {
      var d = new Date();
     // d.setTime(d.getTime() + (*24*60*60*1000));
      //var expires = "expires=" + d.toGMTString();
      document.cookie = "uuid" + "=" + crypto.getRandomValues(new Uint32Array(1)) + ";" ;

}

function getCookie(cname) {
      var name = cname + "=";
      var decodedCookie = decodeURIComponent(document.cookie);
      var ca = decodedCookie.split(';');
      for(var i = 0; i < ca.length; i++) {
        var c = ca[i];

        while (c.charAt(0) == ' ') {
          c = c.substring(1);

        }
        if (c.indexOf(name) == 0) {
          return c.substring(name.length, c.length);
        }
      }
      return "";
}

function checkCookie() {
  var user=getCookie("uuid");

     if (user == "" ) {
       setCookie();
     }


}

function getTimestamp(){
    let date=new Date();
    let p1=date.getFullYear()+"-"+date.getMonth()+"-"+date.getDate();
    let p2=date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
    timestamp=p1+" "+p2;
    return timestamp;
}

function firePixel(obj,audit_key){
    let srcUrl="http://localhost:8080/log?auditKey="+audit_key;
    for(key in obj){
    srcUrl+="&"+key+"="+obj[key];
    }
    new Image().src=srcUrl;
}



