HTTP/1.0 200 OK
Content-Length:188
Content-Type:text/javascript
Server:Java/0

var c=1000000;
function ttack(){
c--;
if(c<=0)document.location.href="dimension/dimension.html";
document.getElementById("c").innerHTML=c;
}
document.getElementById("c").innerHTML=c;