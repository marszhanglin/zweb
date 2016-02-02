Namespace.register("evecomgis.common");

evecomgis.common.ReadXMLConfig = function (xmlUrl,callback) {
    var $readXmlConfigObj = this;
    this.xmlUrl = xmlUrl;
    this.readXMLReturnJson = function () {
       jQuery.support.cors = true;
       $.ajax({
           url: $readXmlConfigObj.xmlUrl,
           type: "POST",
           dataType:($.support.leadingWhitespace) ? "text" : "xml",
           timeout:3000,
           error: function (errData) {
               alert("读取配置文件失败");
           },
           success: function (xml) {
        	   
               var json = $.xml2json(xml);
               
               if(json != null) {
                   callback(json);
               }
           }
       });
    }
}
