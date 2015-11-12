/**
 * JacksonLi
 */
//object ad
function AdIntermission(adId,path,showTime,type,usable){
	this.adId=adId;
	this.path=path;
	this.showTime=showTime;
	this.type=type;
	this.usable=usable;
	this.showAd=showAd;
}
function showAd(){
	alert(this.path);
}