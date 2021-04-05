
let custId = "f4545";
let adtagid = "f1898";
let country = "india";
let adsList = [];


//serp page loads
window.addEventListener("load", adsScript);

function adsScript() {


    let keywordElement = document.getElementById("keyword").innerHTML;
    let keywordArray = keywordElement.split(" ");
    let keyword = keywordArray[2].split(":")[1];
    let publisherUrl = document.getElementById("link_url").innerHTML;
    let adDataElement = document.getElementById("adData");
    let len = adDataElement.getElementsByTagName('div').length;
    let adContent = adDataElement.getElementsByTagName('div');
    if (len > 10) {
        for (i = 10; i < len; i++) {
            adContent[i].innerHTML = "";
        }
    }

    let adsArray = [];//adsArray
    for (i = 0; i < len; i++) {
        adsArray.push(adContent[i].querySelector('a').innerHTML);
    }
    adsList = adsArray;
    let adsLog = {
        uuid: document.getElementById("cookie").innerHTML,
        keyword: keyword,
        country: country,
        timestamp: getTimestamp(),
        cid: custId,
        adTagId: adtagid,
        publisher_url: publisherUrl
    };
    //log ads display
    firePixel(adsLog, 1);
}// end of adsscript

function firePixel(obj, audit_key) {
    let srcUrl = "http://localhost:8080/log?auditKey=" + audit_key;
    for (key in obj) {
        srcUrl += "&" + key + "=" + obj[key];
    }
    for (i = 0; i < adsList.length; i++) {
        srcUrl += "&" + "adsDisplay" + "=" + adsList[i];
    }
    new Image().src = srcUrl;
}

//on ad click
function adClick(a) {

    let title = a.innerHTML;
    let adTitle = `${title}`;
    let keywordElement = document.getElementById("keyword").innerHTML;
    let keywordArray = keywordElement.split(" ");
    let keyword = keywordArray[2].split(":")[1];
    let adUrl = document.getElementById("url").innerHTML;
    let uuid = document.getElementById("cookie").innerHTML;
    let time = getTimestamp();
    let publisherUrl = document.getElementById("link_url").innerHTML;//case change
    let url = `http://localhost:8080/adClickData?uuid=${uuid}&keyword=${keyword}&adUrl=${adUrl}&timestamp=${time}&country=${country}&adTitle=${adTitle}&cid=${custId}&adTagId=${adtagid}&publisher_url=${publisherUrl}`;


    fetch(url, { mode: 'cors' })
        .then(function (response) {
            return response.text();
        })
        .then(function (text) {
            window.open(text);
        })
        .catch(function (error) {
            console.log(error);
        });

}//end of adClick


//get timestamp
function getTimestamp() {
    let date = new Date();
    let dateString = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate();
    let timeString = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    timestamp = dateString + " " + timeString;
    return timestamp;
}
