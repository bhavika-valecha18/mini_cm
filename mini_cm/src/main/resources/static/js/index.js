window.scroll(0, document.documentElement.scrollHeight);
let global_cookie="";
let global_check=true;
let keywords_arr=[];
let keywords_allwd=true;
let display_Keywords=false;
let custId="f4545";
console.log('connected');
window.addEventListener("load",adScript);


function adScript(){


class Medianet extends HTMLElement{
  constructor(){
    super();
  }
}
customElements.define('media-net',Medianet);
console.log(document.querySelector('media-net').getAttribute('cid'));
fetch('http://localhost:8080/adTagId?cid=f4545', {mode: 'cors'})
              .then(function(response) {
                return response.text();
              })
              .then(function(text) {
                let adtagid=text;
                document.querySelector('media-net').setAttribute('adtagid',adtagid)
               console.log( document.querySelector('media-net').getAttribute('adtagid'));

              })
              .catch(function(error) {
                console.log(error);
              });
//fetch all data from meta tag

let metaTag=document.getElementsByTagName("meta");
let author=metaTag["author"].getAttribute('content')||"";
let ref=metaTag["referrer"].getAttribute('content');
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
 let timestamp=getTimestamp();let country="india";
/*todo useragent*/
checkCookie();
global_cookie=getCookie("uuid");
let content='';
for (i=0;i<metaTag.length;i++){
    if(metaTag[i].getAttribute('name')=='keywords'){
        content+=metaTag[i].getAttribute('content');
    }
}
let keywords=content.split(',');
let cookie=getCookie("uuid");

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


var logData={
    uuid:getCookie("uuid"),
    cid:custId,
    publisher_url:encodeURIComponent(rurl),
    browser:browser,
    country:"india",
    timestamp:getTimestamp(),
    adTagId:"f1898",
    adtag_view:0,
    keywords:"",
    device:device
};


//log user details,fire pixel 1
firePixel(logData,0);





//call to server

let url='http://localhost:8080/data';

fetch(url, {
        method: "POST",
        headers:{'Content-Type': 'application/json;charset=UTF-8'},
        body: JSON.stringify({
            author:author,
            domain:d,
            rurl:rurl,
            referrer:ref,
            dtld:domainTld,
            hint:""


        })
    }).then(response =>{
        // checking the status
        if (response.status === 200) {

            return response.json();
        }


    }).then(data => {

    console.log("res",data);
    //todo remove keyword_block
      if(data["keyword_block"]!=1){
       displayKeywords(data);
       }else{

        }

    }).catch(err => {
        console.log("Error", err);
    });




    function displayKeywords(data){

            let adBox=document.getElementById("ad-box");
            adBox.innerHTML =data["response_html"];
//            let adElements=document.getElementById("ad-elements");
//            let li=adElements.getElementsByTagName("li");
//
//            //ad heading
//            let heading=document.createElement("div");
//            const value=document.createTextNode("RELATED TOPICS");
//            heading.appendChild(value);
//            adBox.prepend(heading);
//
//
//            //ad keywords
//           // let arr=data["r"][0][0]["bg"];
//            for(i=0;i<data["list_of_keywords"].length;i++){
//
//                let list=document.createElement("li");
//                let a=document.createElement('a');
//
//                //let key=arr[i]["k"][0]["t"];
//                let arr=data["list_of_keywords"];
//                let key=arr[i];
//
//                keywords_arr.push(key);
//                let keyword=document.createTextNode(key);
//                a.appendChild(keyword);
//                 let adTagId="f1898";
//                 let cid=custId;
//                let keys=key.split(" ");
//                let time=getTimestamp();
//                let anchorLink=`http://localhost:8080/keywordsClicked?cookie=${global_cookie}&keyword=${keys.join("-")}&country=${country}&adTagId=${adTagId}&browser=${browser}&cid=${cid}&rurl=${rurl}`;
//                let aLink=`http://localhost:8080/ads/${global_cookie}/${keys.join("-")}/${country}/${adTagId}/${browser}/${cid}/${rurl}`;
//                a.setAttribute("href",anchorLink);
//                a.setAttribute("target","_blank");
//                console.log(a);
//                list.appendChild(a);
//                adElements.appendChild(list);
//            }
//
//            display_Keywords=true;
//            //apply actions
//            adBox.style.backgroundColor =data["background_color"];
//            adBox.style.fontStyle=data["font_style"];
//            adBox.style.fontFamily=data["font_family"];

        }//end of displayAds





$(window).bind("scroll", function() {

         if($(window).scrollTop() >= $("#ad-box").offset().top -700) {


            if(global_check==true && display_Keywords==true){

            global_check=false;
            display_Keywords=false;
            //data=logData;
            let data={
            uuid:getCookie("uuid"),
            cid:custId,
            publisher_url:encodeURIComponent(rurl),
            browser:browser,
            country:"india",
            timestamp:getTimestamp(),
            adTagId:"f1898",
            adtag_view:1,
            keywords:keywords_arr



            };

            //fire pixel on keywords view
           firePixel(data,0);


         }
     }
     });







}//end of adScript


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



