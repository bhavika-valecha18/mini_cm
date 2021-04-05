//global variables
let keywordBoxViewability = true;
let keywordsList = [];
let keywordsDisplay = false;
let custId = "";
let adtagid = "";
let country = "india";
let metaInfo = {};
//onload
window.addEventListener("load", adScript);


function adScript() {

    //add custom tag for adtagid

    createMedianetAdTag();
    //fetch adtag id
    fetchAdtagId();

    //fetch all data from meta tag

    fetchMetaInfo();

    //set cookie for user
    checkCookie();




    var logData = {
        uuid: getCookie("uuid"),
        cid: custId,
        publisher_url: encodeURIComponent(metaInfo["rurl"]),
        country: country,
        timestamp: getTimestamp(),
        adTagId: adtagid || "f1898",
        viewability: 0,
        keyword: "",
        userAgent: navigator.userAgent
    };

    //log user details,fire pixel 1
    firePixel(logData, 0);

    //call to server
    requestToKbb();


    $(window).bind("scroll", function () {

        if ($(window).scrollTop() >= $("#ad-box").offset().top - 700) {
            if (keywordBoxViewability == true && keywordsDisplay == true) {

                keywordBoxViewability = false;
                logData["viewability"] = 1;
                logData["keyword"] = keywordsList.toString();
                logData["timestamp"] = getTimestamp();

                //fire pixel on keywords view
                firePixel(logData, 0);
            }
        }
    });
}//end of adScript


//create media-net adtag
function createMedianetAdTag() {
    class Medianet extends HTMLElement {
        constructor() {
            super();
        }
    }
    customElements.define('media-net', Medianet);
    custId = document.querySelector('media-net').getAttribute('cid');
}

//fetch adtagid's from custom tag
function fetchAdtagId() {
    fetch('http://localhost:8080/adTagId?cid=' + custId, { mode: 'cors' })
        .then(function (response) {
            return response.text();
        })
        .then(function (text) {
            document.querySelector('media-net').setAttribute('adtagid', text);
            adtagid = text;
        })
        .catch(function (error) {
            console.log(error);
        });
}


//fetch info from meta tag
function fetchMetaInfo() {
    let metaTag = document.getElementsByTagName("meta");
    let author = metaTag["author"].getAttribute('content') || "";
    let referrer = metaTag["referrer"].getAttribute('content') || "";
    //let url=window.location.url;

    let publisherUrl = document.querySelector("meta[property='og:url']").getAttribute("content");
    let key = publisherUrl.split('/');
    let domain = key[2].split('.');
    let domainTld = domain[2];
    let domainName = domain[1] + "." + domain[2];
    metaInfo["author"] = author;
    metaInfo["ref"] = referrer;
    metaInfo["rurl"] = publisherUrl;
    metaInfo["domain"] = domainName;
    metaInfo["domainTld"] = domainTld;
}


//call to kbb
function requestToKbb() {
    let url = 'http://localhost:8080/kbbRequest';
    fetch(url, {
        method: "POST",
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        body: JSON.stringify({
            author: metaInfo["author"],
            domain: metaInfo["domain"],
            rurl: metaInfo["rurl"],
            referrer: metaInfo["ref"],
            dtld: metaInfo["domainTld"],
            hint: ""
        })
    }).then(response => {
        // checking the status
        if (response.status === 200) {
            return response.text();
        }
    }).then(kbbData => {
        displayKeywords(kbbData);

    }).catch(err => {
        console.log("Error", err);
    });
}

//display keyword box
function displayKeywords(kbbData) {

    let adBox = document.getElementById("ad-box");
    adBox.innerHTML = kbbData;
    let ulElement = document.getElementById("ad-elements");
    let listLength = ulElement.getElementsByTagName('li').length;
    let keywordContent = ulElement.getElementsByTagName('li');
    for (i = 0; i < listLength; i++) {
        keywordsList.push(keywordContent[i].querySelector('a').innerHTML);
    }
    keywordsDisplay = true;
}//end of displayKeywords


//cookie generation
function setCookie() {
    var d = new Date();
    // d.setTime(d.getTime() + (*24*60*60*1000));
    //var expires = "expires=" + d.toGMTString();
    document.cookie = "uuid" + "=" + crypto.getRandomValues(new Uint32Array(1)) + ";";

}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
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
    var user = getCookie("uuid");

    if (user == "") {
        setCookie();

    }


}

function getTimestamp() {
    let date = new Date();
    let dateString = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate();
    let timeString = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    timestamp = dateString + " " + timeString;
    return timestamp;
}

function firePixel(obj, audit_key) {
    let srcUrl = "http://localhost:8080/log?auditKey=" + audit_key;
    for (key in obj) {
        srcUrl += "&" + key + "=" + obj[key];
    }
    new Image().src = srcUrl;
}



