/**
 * 查询下拉项
 * 
 * @param select_Obj
 *            下拉对象
 * @param selectVal
 *            默认值
 * @param id
 *            数据字典id
 * @param dictTypeId
 *            数据字典类型
 */
function getSelectType(selectObj, selectVal, id, dictTypeId) {

	$.ajax({
		type : "POST",
		url : "dictController/queryDictByDicttypeId",
		datatype : "json",
		data : {
			ID : id,
			DICTTYPEID : dictTypeId
		},
		// 成功返回之后调用的函数
		success : function(data) {
			var List = data;
			// alert(JSON.stringify(data));
			$(selectObj).empty();
			$(selectObj).append("<option value=''>请选择</option>");
			for ( var index in List) {
				if (List[index].DICTID == selectVal) {
					$(selectObj).append(
							"<option value='" + List[index].DICTID
									+ "' selected='true' >"
									+ List[index].DICTNAME + "</option>");
				} else {
					$(selectObj).append(
							"<option value='" + List[index].DICTID + "'>"
									+ List[index].DICTNAME + "</option>");
				}
			}
			;
		}
	});
}

/**
 * 获取下拉项回填值
 * @param showObj  回显的控件对象 
 * @param key 回显值
 * @param id 
 * @param dictTypeId 字典类型
 */
function getSelectTypeValue(showObj, key, id, dictTypeId) {

	$.ajax({
		type : "POST",
		url : "dictController/queryDictByDicttypeId",
		datatype : "json",
		data : {
			ID : id,
			DICTTYPEID : dictTypeId
		},
		// 成功返回之后调用的函数
		success : function(data) {
			var List = data;
			for ( var index in List) {
				if (List[index].DICTID == key) {
					$(showObj).html(List[index].DICTNAME);
				} 
			}
			;
		}
	});
}

/**
 * 查询网格名
 * 
 * @param areaId
 *            网格id
 * @param inputName
 *            显示网格名的对象
 */
function getAreaName(areaId, inputObj) {
	$.ajax({
		type : "POST",
		url : "baseDataAreaController/areaUserTreeById",
		datatype : "json",
		data : {
			areaId : areaId
		},
		// 成功返回之后调用的函数
		success : function(data) {
			data = $.parseJSON(data);
			$(inputObj).val(data[0].text);
		}
	});
}

/**
 * 选择楼栋
 * @param areaId 网格id
 * @param distDiv 显示在哪
 * @param bdId_dist 楼栋id显示在哪
 * @param bdName_dist 楼栋名字显示在哪
 * @param backDiv 返回div
 * @param formId 搜索表单
 * @param page 第几页
 * @param unitId 楼栋单元下拉控件id
 */
function getBuildings(areaId,distDiv,bdId_dist,bdName_dist,backDiv,formId,page,unitId){
	var pageSize = 5;
	$.ajax({
      type:"POST",
      url:"buildingController/noBoundselectedGrid?page="+page+"&rows="+pageSize+"&areaId="+areaId,
   	  data:$("#"+formId).serialize(),
      datatype: "json",
      //成功返回之后调用的函数             
      success:function(data){
    	  //alert(JSON.stringify(data));
    	  $('#'+distDiv).empty();
          for(var index in data.rows){
             var charger = data.rows[index].CHARGER;
             if(charger==null||charger=="null"){
             charger="";
             }
      		 $('#'+distDiv).append(
  				"<li style='border-bottom-color: #444;'>"+
  				 "<span class='delete-ico' onclick='getSelectBD("+data.rows[index].BDID+",\""+data.rows[index].BDNAME+"\",\"" + bdId_dist +"\",\""+bdName_dist+"\",\""+ backDiv +"\",\""+unitId+"\");'><img src='jsp/mobile/common/images/add.png'></span>"+
  				 "<p>"+
  				 "<label class='list-title1'>楼宇名称:"+ data.rows[index].BDNAME +"</label>"+
  				 "</p>"+
  				 "<p>"+
  				 "<label class='list-content'>负责人:"+ charger +"</label>"+
  				 "</p>"+
  				 "<p>"+
  				 "<label class='list-content'>楼宇类型:" +
  				 "<span id='BDDICTID_"+ index +"' "+
  				 "class='mcdropdown_menu_cache' dict-type-id='BDDICTID_"+ index +"' "+
  				 "dict-type='BASE_BUILDING_TYPE' dict-type-value='"+ data.rows[index].BDDICTID +"' />"+
  				 "</label>"+
  				 "</p>"+
  				 "<p>"+
  				 "<label class='list-content'>建筑结构:"+
  				 "<span id='BDSTRUCTD_"+ index +"' "+
  				 "class='mcdropdown_menu_cache' dict-type-id='BDSTRUCTD_"+ index +"' "+
  				 "dict-type='BASE_BUILDING_STRUCT' dict-type-value='"+ data.rows[index].BDSTRUCTD +"' />"+
  				 "</label>"+
  				 "</p>"+
  				 "<p>"+
  				 "<label class='list-content'>楼宇地址:"+ data.rows[index].BDADDR +"</label>"+
  				 "</p>"+
  				 "</li>"		
      		 );
          }
          $("#bdSplitPage").empty();
          if(page > 1){
        	  $("#bdSplitPage").append("<a href='javascript:void(0);' onclick=searchBD("+(parseInt(page)-1)+") data-role='button'>上一页</a>")
          } 
          if(page<data.total && pageSize*page<data.total){
        	  $("#bdSplitPage").append("<a href='javascript:void(0);' onclick=searchBD("+(parseInt(page)+1)+") data-role='button'>下一页</a>")
          } 
          $("#bdpageInfo").html("<p class='page-info'>第"+page+"页 每页"+pageSize+"条 共"+data.total+"条</p>");
          //移动端回显字典方法
          getMDictType();
          $('#'+distDiv).listview("refresh");
          $("#bdSplitPage").trigger("create")
      }      
   });
}

/**
 * 选择楼栋后跳回页面
 * @param bdId 楼栋id
 * @param bdName 楼栋名称
 * @param bdId_dist 楼栋id显示的组件id
 * @param bdName_dist  楼栋名称显示的组件id
 * @param backDiv 跳回div
 * @param unitId 楼栋单元下拉控件id
 */
function getSelectBD(bdId,bdName,bdId_dist,bdName_dist,backDiv,unitId){
	$("#"+bdId_dist).val(bdId);
	$("#"+bdName_dist).val(bdName);
	$.mobile.changePage("#"+backDiv,{transition:'slide'});
	// 选择楼栋后   楼栋单元下拉变化
	if(unitId !=null && unitId != ""){
		getSSLDDY(bdId, unitId,"");
	}
	
}

//获取所属楼栋单元下拉
function getSSLDDY(bdId, unitId,defaultValue){
	$.ajax({
      type:"POST",
      url:"house/getUnitnameByBdid?BDID="+bdId,
      datatype: "json",
      //成功返回之后调用的函数
      success:function(data){
      	$('#'+unitId).empty();
      	$('#'+unitId).append("<option value=''>请选择</option>");
          for(var index in data){
          	//返回的json第一项未{} 空的
          	if(index > 0){
          		if(data[index].UNITID == defaultValue){
          			$('#'+unitId).append("<option value='"+data[index].UNITID+"' selected='true'>"+data[index].UNITNAME+"</option>");
          		}else{
          			$('#'+unitId).append("<option value='"+data[index].UNITID+"'>"+data[index].UNITNAME+"</option>");
          		}
          	}
          };
      }      
   });
}

//获取所属楼栋回显值
function getSSLDValueForInput(Obj, defaultValue, areaId){
	$.ajax({
      type:"POST",
      url:"house/getBdnameByAreaId?AREAID="+areaId,
      datatype: "json",
      //成功返回之后调用的函数             
      success:function(data){
          for(var index in data){
          	//返回的json第一项为{} 是空的
          	if(index > 0){
          		if(data[index].BDID == defaultValue){
          			$(Obj).val(data[index].BDNAME);
          		}
          	}
          };
      }      
   });
}
//字典转换
function getMDictType(){
	var objArray = $(".mcdropdown_menu_cache");
	var i = 0;
	var path = ctx+'/dictController/queryDict';
	var arrayString = "";
	var dictTypeId = "";
	for(;i<objArray.length;i++) {
		if( $(objArray[i]).attr("dict-type-value") != '' ) {
			arrayString = arrayString  + $(objArray[i]).attr("dict-type") + "," +  $(objArray[i]).attr("dict-type-value") + ",";
			dictTypeId = dictTypeId + $(objArray[i]).attr("dict-type-id") + ",";
		}
	}
	if(dictTypeId.length == 0 ) {
		return;
	}
	var arr = dictTypeId.split(",");
	$.ajax({
		url:path,
		type:'post',
		data:{
			  "arrayString" : arrayString},
		dataType: 'json',
		timeout: 1000,
		error: function(){
			//alert('查询字典失败');
		},
		success: function(result){
			if(result.length > 0 ) {
				
				for(i=0;i<arr.length-1; i++) {
					document.getElementById(arr[i]).innerHTML = result[i].DICTNAME;
				}	
			}
			
		}
	});
}

/**
 * 查询网格名
 * 
 * @param areaId
 *            网格id
 * @param inputName
 *            显示网格名的对象
 */
function getAreaNameForSpan(areaId, inputObj) {
	$.ajax({
		type : "POST",
		url : "baseDataAreaController/areaUserTreeById",
		datatype : "json",
		data : {
			areaId : areaId
		},
		// 成功返回之后调用的函数
		success : function(data) {
			data = $.parseJSON(data);
			$(inputObj).html(data[0].text);
		}
	});
}

/**
 * 验证身份证号码
 * @param num
 * @returns {Boolean}
 */
function checkIdcard(num) {
	if(num != undefined && num != ""){
		num = num.toUpperCase();
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
		if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
			// alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
			return false;
		}
		// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
		// 下面分别分析出生日期和校验位
		var len, re;
		len = num.length;
		if (len == 15) {
			re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
			var arrSplit = num.match(re);

			// 检查生日日期是否正确
			var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/'
					+ arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2]))
					&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
					&& (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay) {
				// alert('输入的身份证号里出生日期不对！');
				return false;
			} else {
				// 将15位身份证转成18位
				// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
						8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
						'3', '2');
				var nTemp = 0, i;
				num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
				for (i = 0; i < 17; i++) {
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				num += arrCh[nTemp % 11];
				return true;
			}
		}
		if (len == 18) {
			re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
			var arrSplit = num.match(re);

			// 检查生日日期是否正确
			var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/"
					+ arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2]))
					&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
					&& (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay) {
				// alert(dtmBirth.getYear());
				// alert(arrSplit[2]);
				// alert('输入的身份证号里出生日期不对！');
				return false;
			} else {
				// 检验18位身份证的校验码是否正确。
				// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				var valnum;
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
						8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
						'3', '2');
				var nTemp = 0, i;
				for (i = 0; i < 17; i++) {
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				valnum = arrCh[nTemp % 11];
				if (valnum != num.substr(17, 1)) {
					// alert('18位身份证的校验码不正确！应该为：' + valnum);
					return false;
				}
				return true;
			}
		}
		return false;
	}else{
		return true;
	}
}

//验证电话号码
function checkTel(num){
	if(num != undefined && num != ""){
		var tel = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
		// 可以匹配座机号码，形式如0511-12345678-1234，其中区号可以3位或4位或没有，直拨号码可以7位或8位，分机号可以为0至4位
		///^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}(-\d{1,4})?$/
		return tel.test(num);
	}else{
		return true;
	}
}
//验证手机
function checkMobile(num){
	if(num != undefined && num != ""){
		var length = num.length;
		//var mobile = /^1[3|5|7|8][0-9]\d{4,8}$/;
		var mobile = /^0?(13[0-9]|15[0-9]|18[0-9]|14[7]|17[6])[0-9]{8}$/
		return (length == 11 && mobile.test(num));
	}else{
		return true;
	}
}
//验证邮箱格式
function isEmail(str){
    var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    return reg.test(str);
}

/**
 * 获取地理位置
 * @param divId 显示的div
 * @param latitude 纬度
 * @param longitude 经度
 */
function getAddressMap(divId, latitude, longitude){
//	latitude = parseFloat(latitude) + 0.0767;
//	longitude = parseFloat(longitude) - 0.05;
	if(longitude!="" && latitude != ""){
		var map = new BMap.Map("addressMap");
		var point = new BMap.Point(longitude,latitude);
		map.centerAndZoom(point,15);
		var markergps = new BMap.Marker(point);
		map.addOverlay(markergps); 
		/*var icon = new BMap.Icon('jsp/mobile/common/images/pin.png', new BMap.Size(20, 32), {
		    anchor: new BMap.Size(10, 30)
		});
		var mkr = new BMap.Marker(new BMap.Point(longitude, latitude), {
		    icon: icon
		});
		map.addOverlay(mkr);*/
	}else{
		$("#"+divId).removeClass();
		$("#"+divId).html("暂无地理信息！");
	}
}

function cleansearchform(formname)
{
	//$(formname+" > input[type=text]").val("");
	//$(formname+" >select").val("");
	$("#"+formname)[0].reset();
}

