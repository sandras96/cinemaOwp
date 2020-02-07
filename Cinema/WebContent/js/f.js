var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

var formatDate = function formatDate(date) {
	  var hours = date.getHours();
	  var minutes = date.getMinutes();
	  var ampm = hours >= 12 ? 'pm' : 'am';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  var strTime = hours + ':' + minutes + ' ' + ampm;
	  return date.getDate() + "-" + (date.getMonth()+1)  + "-" + date.getFullYear() + "  " + strTime;
}


/*22-2-2020  1:59 am
2020-02-28T23:00:00.000Z 
2018-06-12T19:30
2020-02-29T00:59
2018-06-14T00:00
2020-02-29T001:59
2013-03-10T02:00:00Z
2013-03-10*/
/*
var date = new Date(x);
year = date.getFullYear();
month = date.getMonth()+1;
dt = date.getDate();
if (dt < 10) {
  dt = '0' + dt;
}
if (month < 10) {
  month = '0' + month;
}
//	 hours = x.getHours();

console.log("aaaaaaaaa" + year+'-' + month + '-'+dt);*/

var test2= function test2(date) {
	  var hours = date.getUTCHours();
	  var minutes = date.getMinutes();
	  var ampm = hours >= 12 ? 'pm' : 'am';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  return date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate + "T0" + date.getHours + ":" + minutes;
}


var test = function test(date) {
	  var hours = date.getHours(); // isto i za sate
	  var minutes = date.getMinutes();
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  var month = date.getMonth() + 1; // proveris da li je manji od 10 ako jeste lepis mu nulu ispred
	  var days = date.getDate() // proveriti da li je manji od 10 ako jeste lepis nulu ispred
	  
	  return  date.getFullYear() + "-0" + month  + "-0" + days + 'T0' +  hours + ':' + minutes;
}


var test3 = function test3(depTime){
	var x = new Date(depTime).toISOString();
	return depTime = x.split(":")[0] + ":" + x.split(":")[1];
	//2020-02-29T00:59
}
var test4 = function test4(depTime){
	var x = new Date(depTime).toISOString();
	return depTime = x.split("T")[1];
	//2020-02-29T00:59
}
