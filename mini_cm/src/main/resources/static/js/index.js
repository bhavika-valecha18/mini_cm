window.scroll(0, document.documentElement.scrollHeight);
let global_cookie="";
let global_check=true;
let keywords_arr=[];
let keywords_allwd=true;
let display_Keywords=false;

window.addEventListener("load",adScript);

function adScript(){



//fetch all data from meta tag

let metaTag=document.getElementsByTagName("meta");
let author=metaTag["author"].getAttribute('content');
let ref=metaTag["referrer"].getAttribute('content');
//let url=window.location.url;
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
//console.log("cid",`[[${creativeId}]]`);
 let timestamp=getTimestamp();let country="india";

checkCookie();
global_cookie=getCookie("uuid");
console.log("gc:",global_cookie);
let content='';
for (i=0;i<metaTag.length;i++){
    if(metaTag[i].getAttribute('name')=='keywords'){
        content+=metaTag[i].getAttribute('content');
    }
}
let keywords=content.split(',');
console.log(keywords.length);
console.log(author);
console.log(ref);
console.log(d);

//fire pixel 1

let cookie=getCookie("uuid");
//let logData="`"+cookie+"!"+"f4545"+"!"+rurl+"!"+0+"!"+0+"!"+1+"!"+browser+"!"+"india"+"!"+timestamp+"`";
//console.log(logData.split("!"));
//console.log(logData);
//var u = new URL("https://localhost:8080/log/1");
//console.log(u);
//let src=u.searchParams.append('logData',logData);
//console.log(src);



var logData={
    uuid:getCookie("uuid"),
    cid:"f4545",
    publisher_url:encodeURIComponent(rurl),
    browser:browser,
    country:"india",
    timestamp:getTimestamp(),
    adTagId:"f1898",
    adtag_view:0,
    keywords:""

};
for(key in logData){
console.log(logData[key]);
console.log(typeof(logData[key]));}
//let src=`http://localhost:8080/log/${1}/${logData}`;

//log user details
firePixel(logData,0);





//call to server
let url='http://localhost:8080/data';
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
fetch(url, {
        method: "POST",
        headers:{'Content-Type': 'application/json;charset=UTF-8'},
        body: JSON.stringify({
            author:author,
            domain:d,
            rurl:rurl,
            referrer:ref,
            dtld:domainTld,
            hint:"recipes",
            customerId:"f4546",
            browser:browser,
            country:"India",
            uuid:cookie,
             adTagId:"f1898",
             device:device

        })
    }).then(response =>{
        // checking the status
        if (response.status === 200) {
            return response.json();
        }


    }).then(data => {

       // console.log(context.data.meta.statusCode);
      // if(data.status==200){
      if(data["keyword_block"]!=1){
      console.log("bhavika did changes");
      console.log(data);
      console.log("see",data["r"][0][0]["bg"]);
        displayKeywords(data);
        }else{

        }
    //}
    }).catch(err => {
        console.log("Error", err);
    });




    function displayKeywords(data){

            let adBox=document.getElementById("ad-box");
            let adElements=document.getElementById("ad-elements");
            let li=adElements.getElementsByTagName("li");
            console.log(adElements);
            console.log(li);
            //ad heading
            let heading=document.createElement("div");
            const value=document.createTextNode("RELATED TOPICS");
            heading.appendChild(value);
            adBox.prepend(heading);


            //ad keywords
            let arr=data["r"][0][0]["bg"];
            for(i=0;i<data["keyword_count"];i++){
                let list=document.createElement("li");
                let a=document.createElement('a');
                //a.setAttribute("_target","blank");
                let key=arr[i]["k"][0]["t"];
                keywords_arr.push(key);
                let keyword=document.createTextNode(key);
                a.appendChild(keyword);
                 let adTagId="f1898";
                 let cid="f4545";
                let keys=key.split(" ");
                let time=getTimestamp();
                let anchorLink=`http://localhost:8080/keywordsClicked?cookie=${global_cookie}&keyword=${keys.join("-")}&country=${country}&adTagId=${adTagId}&browser=${browser}&cid=${cid}&rurl=${rurl}`;
                let aLink=`http://localhost:8080/ads/${global_cookie}/${keys.join("-")}/${country}/${adTagId}/${browser}/${cid}/${rurl}`;
                a.setAttribute("href",anchorLink);
                a.setAttribute("target","_blank");
                list.appendChild(a);
                adElements.appendChild(list);
            }

            display_Keywords=true;
            //apply actions
            adBox.style.backgroundColor =data["background_color"];
            adBox.style.fontStyle=data["font_style"];
            adBox.style.fontFamily=data["font_family"];

        }//end of displayAds





$(window).bind("scroll", function() {
//console.log("in");
         if($(window).scrollTop() >= $("#ad-box").offset().top -700) {
         console.log(keywords_arr.toString());
            console.log("hi");
            if(global_check==true && display_Keywords==true){

            global_check=false;
            display_Keywords=false;
            //data=logData;
            let data={
            uuid:getCookie("uuid"),
            cid:"f4545",
            publisher_url:encodeURIComponent(rurl),
            browser:browser,
            country:"india",
            timestamp:getTimestamp(),
            adTagId:"f1898",
            adtag_view:1,
            keywords:keywords_arr



            };
//            data["adtag_view"]=1;
//            data["adtag_loaded"]=1;
//            let t=getTimestamp();
//            data["adview_timestamp"]=`${t}`;
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
  //console.log(document.cookie);
}

function getCookie(cname) {
  var name = cname + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  //console.log(ca);
  for(var i = 0; i < ca.length; i++) {
    var c = ca[i];
    //console.log(c);
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
     // console.log(c);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

function checkCookie() {
  var user=getCookie("uuid");
  console.log(user);
     if (user == "" ) {
       setCookie();
     }
    //global_cookie=getCookie("uuid");

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
console.log(srcUrl);
new Image().src=srcUrl;
}



