<script type="text/javascript">
function $getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}

var $_root_path = $getRootPath();
if(typeof($) == "undefined") {
	document.write("<script src=\"/gssms/js/evecomgis/common/jquery-1.9.0.js\" type=\"text/javascript\" charset=\"utf-8\"><\/script>");
}
</script>

<!-- æ ·å¼ -->
<link rel="stylesheet" type="text/css" href="/gssms/js/evecomgis/common/arcgislib/library/3.7/3.7/js/dojo/dijit/themes/tundra/tundra.css" charset="utf-8"/>
<link rel="stylesheet" type="text/css" href="/gssms/js/evecomgis/common/arcgislib/library/3.7/3.7/js/esri/css/esri.css" charset="utf-8" />
<link rel="stylesheet" type="text/css" href="/gssms/js/evecomgis/common/css/base.css" charset="utf-8" />
<link rel="stylesheet" type="text/css" href="/gssms/js/evecomgis/common/css/common.css" charset="utf-8" />
<link rel="stylesheet" type="text/css" href="/gssms/js/evecomgis/common/css/style.css" charset="utf-8" />

<!-- å¬å±å -->
<script type="text/javascript" src="/gssms/js/evecomgis/common/arcgislib/library/3.7/3.7/init.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/common/supermaplib/core/include.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/common/supermaplib/core/SuperMap.Include.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/common/Namespace.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/common/jquery.xml2json.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/jsp/mobile/common/js/gis/readXMLConfig.js" charset="utf-8"></script>

<!-- arcGiså®ç°ç±»  -->
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/arcgis/base/initMap.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/arcgis/base/measureLength.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/arcgis/base/Graphic.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/arcgis/base/roam.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/arcgis/base/printMap.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/common/monitor.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/arcgis/base/location.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/arcgis/base/tools.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/arcgis/base/query.js" charset="utf-8"></script>

<!-- supermapå®ç°ç±» -->
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/supermap/base/tools.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/supermap/base/initMap.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/supermap/base/query.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/supermap/base/location.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/supermap/base/Graphic.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/supermap/base/measureLength.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/supermap/base/roam.js" charset="utf-8"></script>
<script type="text/javascript" src="/gssms/js/evecomgis/gisimpl/supermap/base/printMap.js" charset="utf-8"></script>

<!-- å°å¾æ¥å£ -->
<script type="text/javascript" src="/gssms/js/evecomgis/igis/baseGis.js" charset="utf-8"></script>

