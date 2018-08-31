HTTP/1.0 200 OK
Content-Length:477
Content-Type:text/javascript
Server:Java/0

function isMobile(){
var ua = navigator.userAgent;
var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),
isAndroid = ua.match(/(Android)\s+([\d.]+)/),
isMobile = isIphone || isAndroid;
if(isMobile) {
return true;
}else {
return false;
}
}

if(isMobile()){
if(document.location.pathname==
"/mynazo/console.html")document.location.href="blank.html";
else setTimeout("document.location.href=\"blank.html\";",10000);
}