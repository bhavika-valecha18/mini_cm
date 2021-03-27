

 //browser
 navigator.browserSpecs = (function()
{ var ua = navigator.userAgent,
tem,
M = ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
if(/trident/i.test(M[1])){ tem = /\brv[ :]+(\d+)/g.exec(ua) || []; return {name:'IE',version:(tem[1] || '')}; }
if(M[1]=== 'Chrome'){ tem = ua.match(/\b(OPR|Edge)\/(\d+)/); if(tem != null) return {name:tem[1].replace('OPR', 'Opera'),version:tem[2]}; }
M = M[2]? [M[1], M[2]]: [navigator.appName, navigator.appVersion, '-?']; if((tem = ua.match(/version\/(\d+)/i))!= null) M.splice(1, 1, tem[1]); return {name:M[0], version:M[1]}; })();
let browser=navigator.browserSpecs["name"];
let country="india";
let ads=[];
let display=true;

//page loads
window.addEventListener("load",adsScript);

 function adsScript(){


       console.log("in");

           let keyword=document.getElementById("keyword").innerHTML;
           let arr=keyword.split(" ");
           let key=arr[2].split(":")[1];

            let publisher_url=document.getElementById("link_url").innerHTML;

           //firePixel(serpLog,4);


           //log all ad details
         // for(i=0;i<4;i+=1){
         let a=document.getElementById("adData");
         let b=a.getElementsByTagName('div').length;
         let c=a.getElementsByTagName('div');
         console.log(c[0].querySelector('a').innerHTML);
           console.log(document.getElementById("adData"));
           console.log(document.querySelector("#adData >div").length);
           console.log(b);
           let adsArr=[];
           for(i=0;i<b;i++){
                 adsArr.push(c[i].querySelector('a').innerHTML);
           }
           ads=adsArr;
           console.log(adsArr.toString());
            let adsLog={
                      uuid:document.getElementById("cookie").innerHTML,
                       keyword_title:key,
                       browser:browser,
                       country:country,
                      timestamp:getTimestamp(),
                        cid:"f4545",
                        adTagId:"f1898",
                        publisher_url:publisher_url

                      };
                      firePixel(adsLog,1);


 }// end of adsscript
        //}




        function firePixel(obj,audit_key){
        let srcUrl="http://localhost:8080/log?auditKey="+audit_key;
        for(key in obj){
        srcUrl+="&"+key+"="+obj[key];
        }
        for(i=0;i<ads.length;i++){
         srcUrl+="&"+"ads"+"="+ads[i];
        }

        console.log(srcUrl);
        new Image().src=srcUrl;
        }

        function adClick(a){
         let title=a.innerHTML;
         let t=`${title}`;
           console.log(`${title}`);
           let keyword=document.getElementById("keyword").innerHTML;
          let arr=keyword.split(" ");
          let key=arr[2].split(":")[1];
          console.log(key);
         let adUrl=document.getElementById("url").innerHTML;
         let uuid=document.getElementById("cookie").innerHTML;
         console.log(adUrl);
         console.log(uuid);
          let time=getTimestamp();
           console.log(time);
            let country="india";
            let cid="f4545";
            let adTagId="f1898";
             let publisher_url=document.getElementById("link_url").innerHTML;

           let url=`http://localhost:8080/adClickData?uuid=${uuid}&keyword=${key}&adUrl=${adUrl}&time=${time}&country=${country}&browser=${browser}&title=${t}&cid=${cid}&adTagId=${adTagId}&publisher_url=${publisher_url}`;


            fetch(url, {mode: 'cors'})
              .then(function(response) {
                return response.text();
              })
              .then(function(text) {
                console.log('Request successful', text);
                window.open(text);
              })
              .catch(function(error) {
                log('Request failed', error)
              });







        }//end of adClick

        function getTimestamp(){
        let date=new Date();
        let p1=date.getFullYear()+"-"+date.getMonth()+"-"+date.getDate();
        let p2=date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
        timestamp=p1+" "+p2;
        return timestamp;
        }
