/**
 * 初始化地图及地图相关控件
 */

//基础接口对象
var base;
//自定义图层
var customLayers = new Array();

//绑定事件对象
var handlerArray = {};

var moniteOver = null;

var moniteOut = null;

//初始化地图
function init(config) {
	base = new evecomgis.igis.Base(config);
	if(long==""||lat==""){
		$("#mapDiv").attr("style","width:100%;height:5%;");
		$("#mapDiv").html("暂无地理信息");
		return;
	}
	setTimeout(function() {
		
		var hasD=long.toString().indexOf("D");
		if(hasD>0){
			
			
			long=long.toString().substr(0,long.length-1);
			
			// 凤 城镇(73728，-24576)
			var zxdlonxs = 81920;
			var zxdlatxs = 16384;
			
			// 中心经度
			var zxdlond = 119;
			var zxdlonf = 31;
			var zxdlonm = 23.91;
			
			// 中心纬度
			var zxdlatd = 26;
			var zxdlatf = 12;
			var zxdlatm = 24.88;
			
			
			
			// (106496,-16384)_shift   浦口镇
			if(long >= 106496 && long<= 122880 && lat <= 16384 &&  lat >= 4096 ) {
				//alert("浦口镇");
				zxdlonxs = 114688;
				zxdlatxs = 8192;
				
				zxdlond = 119;
				zxdlonf = 37;
				zxdlonm = 10.99;
				
				zxdlatd = 26;
				zxdlatf = 14;
				zxdlatm = 36.49; 
			}
			
			// (106496,-4096)_Shift (114688,-4096)_Shift   晓澳镇
			if(long >= 106496 && long<= 122880  && lat <= 4096 && lat >= -4096 ) {
				//alert("晓澳镇");
				zxdlonxs = 114688;
				zxdlatxs = 0;
				
				zxdlond = 119;
				zxdlonf = 38;
				zxdlonm = 44.12;
				
				zxdlatd = 26;
				zxdlatf = 13;
				zxdlatm = 8.54; 
			}
			
			// (192512,12288)_shift   黄岐镇
			if(long >= 192512 && long<= 208896  && lat <= -12288 && lat >= -28672 ) {
				//alert("黄岐镇");
				zxdlonxs = 200704;
				zxdlatxs = -20480;
				
				zxdlond = 119;
				zxdlonf = 53;
				zxdlonm = 42.80;
				
				zxdlatd = 26;
				zxdlatf = 19;
				zxdlatm = 2.56; 
			}
			
			
			// (69632,-4096)_shift   琯头镇
			if(long >= 69632 && long<= 77842  && lat <= 4096 && lat >= -4096 ) {
				//alert("琯头镇");
				zxdlonxs = 73737;
				zxdlatxs = 0;
				
				zxdlond = 119;
				zxdlonf = 33;
				zxdlonm = 28.24;
				
				zxdlatd = 26;
				zxdlatf = 8;
				zxdlatm = 37.25; 
			}
			
			// (90112.-24576)_shift   凤 城镇
			if(long >= 90112 && long<= 98304  && lat <= 24576 && lat >= 8192 ) {
				//alert("凤 城镇2");
				zxdlonxs = 94208;
				zxdlatxs = 16384;
				
				zxdlond = 119;
				zxdlonf = 32;
				zxdlonm = 58.83;
				
				zxdlatd = 26;
				zxdlatf = 13;
				zxdlatm = 46.64; 
			}
			
			// (98304,-16384)_shift   凤 城镇
			if(long >= 98304 && long<= 106496  && lat <= 16384 && lat >= 0 ) {
				//alert("凤 城镇3");
				zxdlonxs = 102400;
				zxdlatxs = 8192;
				
				zxdlond = 119;
				zxdlonf = 35;
				zxdlonm = 36.69;
				
				zxdlatd = 26;
				zxdlatf = 13;
				zxdlatm = 14.15; 
			}
			
			// (98304,-49152)_shift   丹阳镇
			if(long >= 98304 && long<= 114688  && lat <= 49152 && lat >= 32768 ) {
				//alert("丹阳镇");
				zxdlonxs = 106496;
				zxdlatxs = 8192;
				
				zxdlond = 119;
				zxdlonf = 29;
				zxdlonm = 55.05;
				
				zxdlatd = 26;
				zxdlatf = 19;
				zxdlatm = 37.04; 
			}
			
			
			
			//alert("lon***"+long);
			//alert("lat***"+lat);

			//目标点与中心点距离(m)
			var xsdlon = (parseInt(long)-zxdlonxs)*0.364;
			var tempxsdlat = parseInt(lat)-zxdlatxs;
			var xsdlat = tempxsdlat*0.364;

			//alert(xsdlat);
			//转45后的像素距离(m)
			
			var az = Math.PI * (45) / 180;
			
			//alert(xsdlon);
			//alert(xsdlat);
			
			var x=Math.sin(az)*xsdlon-xsdlat;
			var y=Math.sin(az)*xsdlon+xsdlat;
			
			x = 0.9*x;
			y = 0.9*y;
						
			//alert("x***"+x);
			//alert("y***"+y);
						
			//经度
			//距离换成度
			var tempjdu;
			if(x >= 1111330 ) {
				tempjdu = zxdlond + parseInt(x / 1111330);
			} else {
				tempjdu = zxdlond;
			}
			
						
			//alert(tempjdu);
			
			var tempjFen;
			if(x >= 1852 ) {
				tempjFen = zxdlonf + parseInt(x / 1852);
			} else {
				tempjFen = zxdlonf;
			}
			
			//换成度剩下的距离换成分
			//if(parseInt(x / 1852) < 31){
					//var tempjFen = zxdlonf + parseInt(x / 1852);
							
			//}else{
					//tempjdu = tempjdu - 1; 
					//var tempjFen = 91 + parseInt(x / 1852);
			//}
						
			//alert(tempjFen);

			//换成分剩下的距离换成秒
			var tempX = parseInt(x / 1852);
			var tempjMiaoM = (x - tempX*1852)/30.87;
			var tempjMiao = zxdlonm + tempjMiaoM;
						
			//alert(tempjMiao);
						
			//将度分秒的形式换成小数点的形式
			long = tempjdu + tempjFen/60 + tempjMiao/3600 ; 
						
			//alert(long);



			var cccs = Math.PI * (26.08) / 180

			//纬度
			//距离换成度
			var tempwdu;
			if(y<1113190) {
				tempwdu = zxdlatd;
			} else {
				tempwdu = zxdlatd + parseInt(y / (1113190 ) );
			}
			
			//换成度剩下的距离换成分
			//if(xsdlat % 1852 < 12){
					//var tempwFen = zxdlatf + parseInt(y / (1855) );
					
			
			var tempwFen;
			if(y >= 1852 ) {
				tempwFen = zxdlatf + parseInt(y / 1855);
			} else {
				tempwFen = zxdlatf;
			}
			//}else{
					//tempwdu = tempwdu - 1; 
					//var tempwFen = 74 + parseInt(y / 1852);
			//}
						
			//换成分剩下的距离换成秒
			var tempY = parseInt(y / (1855 ) );
			var tempwMiaoM = (y - tempY* (1855 ) )/ (30.92);
			var tempwMiao = zxdlatm + tempwMiaoM;
						
			//将度分秒的形式换成小数点的形式
			lat = tempwdu + tempwFen/60 + tempwMiao/3600 ; 
						
			//alert(lat);

			//var point = base.locationObject.getPoint(long, lat);
		}else{
			//var point = base.locationObject.getPoint(long, lat);
		}
		base.map.centerAndZoom(new esri.geometry.Point(long, lat),5);
	},800);
	locationPoint("1",long,lat);
}


//描点
function locationPoint(i,long,lat) {
	var graphicLayer;
	if(base.map.getLayer(i) == null) {
		//alert("base.map.getLayer(i) == null");
		graphicLayer = new esri.layers.GraphicsLayer({id:i});
		base.map.addLayer(graphicLayer);
		customLayers.push(graphicLayer);
		graphicLayer.on("mouse-over", function (evt) {
			base.map.setMapCursor("pointer");
		});
		graphicLayer.on("mouse-out", function (evt) {
			base.map.setMapCursor("default");
		});
	} else {
		//alert("base.map.getLayer(i) != null");
		graphicLayer = base.map.getLayer(i);
	}
	//var changeLat = lat*Math.cos(45) + long*Math.sin(45);
	//var changeLon = Math.cos(45)*(long*Math.cos(45)-lat*Math.sin(45));
	//var point = base.locationObject.getPoint(changeLon, changeLat);
	//alert("截取前："+lon);
	var hasD=long.toString().indexOf("D");
	if(hasD>0){
		
		
		long=long.toString().substr(0,long.length-1);
		
		// 凤 城镇(73728，-24576)
		var zxdlonxs = 81920;
		var zxdlatxs = 16384;
		
		// 中心经度
		var zxdlond = 119;
		var zxdlonf = 31;
		var zxdlonm = 23.91;
		
		// 中心纬度
		var zxdlatd = 26;
		var zxdlatf = 12;
		var zxdlatm = 24.88;
		
		
		
		// (106496,-16384)_shift   浦口镇
		if(long >= 106496 && long<= 122880 && lat <= 16384 &&  lat >= 4096 ) {
			//alert("浦口镇");
			zxdlonxs = 114688;
			zxdlatxs = 8192;
			
			zxdlond = 119;
			zxdlonf = 37;
			zxdlonm = 10.99;
			
			zxdlatd = 26;
			zxdlatf = 14;
			zxdlatm = 36.49; 
		}
		
		// (106496,-4096)_Shift (114688,-4096)_Shift   晓澳镇
		if(long >= 106496 && long<= 122880  && lat <= 4096 && lat >= -4096 ) {
			//alert("晓澳镇");
			zxdlonxs = 114688;
			zxdlatxs = 0;
			
			zxdlond = 119;
			zxdlonf = 38;
			zxdlonm = 44.12;
			
			zxdlatd = 26;
			zxdlatf = 13;
			zxdlatm = 8.54; 
		}
		
		// (192512,12288)_shift   黄岐镇
		if(long >= 192512 && long<= 208896  && lat <= -12288 && lat >= -28672 ) {
			//alert("黄岐镇");
			zxdlonxs = 200704;
			zxdlatxs = -20480;
			
			zxdlond = 119;
			zxdlonf = 53;
			zxdlonm = 42.80;
			
			zxdlatd = 26;
			zxdlatf = 19;
			zxdlatm = 2.56; 
		}
		
		
		// (69632,-4096)_shift   琯头镇
		if(long >= 69632 && long<= 77842  && lat <= 4096 && lat >= -4096 ) {
			//alert("琯头镇");
			zxdlonxs = 73737;
			zxdlatxs = 0;
			
			zxdlond = 119;
			zxdlonf = 33;
			zxdlonm = 28.24;
			
			zxdlatd = 26;
			zxdlatf = 8;
			zxdlatm = 37.25; 
		}
		
		// (90112.-24576)_shift   凤 城镇
		if(long >= 90112 && long<= 98304  && lat <= 24576 && lat >= 8192 ) {
			//alert("凤 城镇2");
			zxdlonxs = 94208;
			zxdlatxs = 16384;
			
			zxdlond = 119;
			zxdlonf = 32;
			zxdlonm = 58.83;
			
			zxdlatd = 26;
			zxdlatf = 13;
			zxdlatm = 46.64; 
		}
		
		// (98304,-16384)_shift   凤 城镇
		if(long >= 98304 && long<= 106496  && lat <= 16384 && lat >= 0 ) {
			//alert("凤 城镇3");
			zxdlonxs = 102400;
			zxdlatxs = 8192;
			
			zxdlond = 119;
			zxdlonf = 35;
			zxdlonm = 36.69;
			
			zxdlatd = 26;
			zxdlatf = 13;
			zxdlatm = 14.15; 
		}
		
		// (98304,-49152)_shift   丹阳镇
		if(long >= 98304 && long<= 114688  && lat <= 49152 && lat >= 32768 ) {
			//alert("丹阳镇");
			zxdlonxs = 106496;
			zxdlatxs = 8192;
			
			zxdlond = 119;
			zxdlonf = 29;
			zxdlonm = 55.05;
			
			zxdlatd = 26;
			zxdlatf = 19;
			zxdlatm = 37.04; 
		}
		
		
		
		//alert("lon***"+long);
		//alert("lat***"+lat);

		//目标点与中心点距离(m)
		var xsdlon = (parseInt(long)-zxdlonxs)*0.364;
		var tempxsdlat = parseInt(lat)-zxdlatxs;
		var xsdlat = tempxsdlat*0.364;

		//alert(xsdlat);
		//转45后的像素距离(m)
		
		var az = Math.PI * (45) / 180;
		
		//alert(xsdlon);
		//alert(xsdlat);
		
		var x=Math.sin(az)*xsdlon-xsdlat;
		var y=Math.sin(az)*xsdlon+xsdlat;
		
		x = 0.9*x;
		y = 0.9*y;
					
		//alert("x***"+x);
		//alert("y***"+y);
					
		//经度
		//距离换成度
		var tempjdu;
		if(x >= 1111330 ) {
			tempjdu = zxdlond + parseInt(x / 1111330);
		} else {
			tempjdu = zxdlond;
		}
		
					
		//alert(tempjdu);
		
		var tempjFen;
		if(x >= 1852 ) {
			tempjFen = zxdlonf + parseInt(x / 1852);
		} else {
			tempjFen = zxdlonf;
		}
		
		//换成度剩下的距离换成分
		//if(parseInt(x / 1852) < 31){
				//var tempjFen = zxdlonf + parseInt(x / 1852);
						
		//}else{
				//tempjdu = tempjdu - 1; 
				//var tempjFen = 91 + parseInt(x / 1852);
		//}
					
		//alert(tempjFen);

		//换成分剩下的距离换成秒
		var tempX = parseInt(x / 1852);
		var tempjMiaoM = (x - tempX*1852)/30.87;
		var tempjMiao = zxdlonm + tempjMiaoM;
					
		//alert(tempjMiao);
					
		//将度分秒的形式换成小数点的形式
		long = tempjdu + tempjFen/60 + tempjMiao/3600 ; 
					
		//alert(long);



		var cccs = Math.PI * (26.08) / 180

		//纬度
		//距离换成度
		var tempwdu;
		if(y<1113190) {
			tempwdu = zxdlatd;
		} else {
			tempwdu = zxdlatd + parseInt(y / (1113190 ) );
		}
		
		//换成度剩下的距离换成分
		//if(xsdlat % 1852 < 12){
				//var tempwFen = zxdlatf + parseInt(y / (1855) );
				
		
		var tempwFen;
		if(y >= 1852 ) {
			tempwFen = zxdlatf + parseInt(y / 1855);
		} else {
			tempwFen = zxdlatf;
		}
		//}else{
				//tempwdu = tempwdu - 1; 
				//var tempwFen = 74 + parseInt(y / 1852);
		//}
					
		//换成分剩下的距离换成秒
		var tempY = parseInt(y / (1855 ) );
		var tempwMiaoM = (y - tempY* (1855 ) )/ (30.92);
		var tempwMiao = zxdlatm + tempwMiaoM;
					
		//将度分秒的形式换成小数点的形式
		lat = tempwdu + tempwFen/60 + tempwMiao/3600 ; 
					
		//alert(lat);

		var point = base.locationObject.getPoint(long, lat);
	}else{
		var point = base.locationObject.getPoint(long, lat);
	}
	var obj = {
				point: point,
				layer: graphicLayer,
	}
	base.locationObject.addGraphic(obj);
	//alert("定位结束");
	
}


$(function() {
	var xmlUrl = "/gssms/js/evecomgis/common/arcgis-config.xml";
	var configReader = new evecomgis.common.ReadXMLConfig(xmlUrl, init);
	configReader.readXMLReturnJson();
});