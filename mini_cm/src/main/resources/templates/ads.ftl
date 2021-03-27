<!DOCTYPE html>
<html>
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="../static/js/serp.js"></script>
</head>


    <body>

    <!--<a href="cities">Hello bhavika</a>-->

        <div id="top">
            <div id="keyword">Sponsered Results for:${keywordSelected}</div>
            <div id="link_url" style="display:none;">${rurl}</div>
            <div id="cookie" style="display:none;">${cookie}</div>
            <div id="adData">
            <#list listingAds as ads>
            <div id="container" >
                <p id="url" style="display:none;">${ads.clickUrl}</p>
                <h2><a  href="#" onclick="adClick(this)" >${ads.title}</a></h2>
                <p>${ads.description}</p>
            </div>

        </#list>
        </div>

        </div>

    </body>

</html>