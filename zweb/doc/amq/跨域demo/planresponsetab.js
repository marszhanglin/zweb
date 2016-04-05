/**
 * 预案处置->预案响应
 * 
 * @author Mars zhang
 * @created 2015年5月29日 下午14:57:00
 */

// 创建包
evecom.ns('evecom.ecapp.incident.planresponsetab');

/**
 * 预案响应模块
 * 
 * @author Mars zhang
 * @created 2015年5月29日 下午14:57:00
 */
evecom.ecapp.incident.planresponsetab.planresponsetabController = evecom.define(
		evecom.ecapp.incident.map.RightSiderSecondController, function(superclass) {
			return {  
				ids : null,
				planresponsetab1_content : null,
				planresponsetab2_content : null,
				planresponsetab3_content : null,
				/**右侧高度*/
				rSiderHeight_global : null,
				/**当前响应*/
				current_eventresponseid : null,
				/** 当前处置项目id */
				current_disprojectid : null,
				/** 是否是更新预案 */
				ifchangeplan : null,
				/**
				 * 处置项目对象-存数据使用
				 */
				projectDatas : null, 
				/**
				 * 组织机构缓存对象
				 */
				orgcache : null,
				/**
				 * 定义全局数组,用来存放气泡描点对象
				 * @created 2015年5月29日 下午15:02:00
				 */
				fieldPopupArr : null,
				/**
				 * 任务缓存
				 */
				taskResponses : null,
				tree : null,
				selected : null,
				points : null,
				/**
				 * 初始化
				 * 
				 * @author Mars zhang
				 * @created 2015年5月29日 下午15:02:00
				 */
				init : function() {
					superclass.init.call(this); 
					this.ids=[]; 
					this.projectDatas={}; 
					this.taskResponses = []; 
					this.ifchangeplan=false;
					this.orgcache={};
					this.init1();   
					this.points = new Object();
					this.selected='';
					//定义数组,用来存放气泡描点对象
					this.fieldPopupArr = [];
					$this=this;
					//第一二三tab自适应高度
                    this._windowResizeHandler = function() {
						// 设置页面右边高度 
	                    var rSiderHeight = $('.rSider .bd').height(); 
	                    //预案列表
	                    var doHeight1 = rSiderHeight - 65;
	                    var div1=$('#planresponsetab_tab1_scoll_div',this.planresponsetab1_content);
	                    if(div1)div1.height(doHeight1);
	                    //计算tab1 task栏的高度
	                    $this.counttaskheight();
	                    //所有反馈
	                    var doHeight2 = rSiderHeight - 380;
	                    var div2=$('#linkagedisposal_tab1_scoll_div',this.planresponsetab2_content);
	                    if(div2)div2.height(doHeight2);
	                    //某个处置反馈
	                    var doHeight3 = rSiderHeight - 260 ;
	                    var div3=$('#linkagedisposal_tab2_scoll_div',this.planresponsetab3_content);
	                    if(div3)div3.height(doHeight3); 
	                    
					}; 
					$(window).bind('resize', this._windowResizeHandler); 
					//注册监听事件
					this.regist();
				},
				init1 :function(){ 
					$.ajax({
						url : rootPath+'/evecom/ecapp/incident/planresponsetab/html/planresponsetab_tab1.html',
						context : this,
						type : 'get',
						dataType : 'html',
						success : function(r) {   
	                        this.appendTitle(this.functionCode+'_planresponsetab_title','预案响应');
	                        this.appendContent(this.functionCode+'_planresponsetab_content',r,true); 
	                        this.ids.push(this.functionCode+"_planresponsetab_title");
	                        this.ids.push(this.functionCode+"_planresponsetab_content");
	                        this.planresponsetab1_content= $('#'+this.functionCode+'_planresponsetab_content');
	                        this.init2();
		                     
						}
					});  
				},
				init2 :function(){ 
					$.ajax({
						url : rootPath+'/evecom/ecapp/incident/planresponsetab/html/planresponsetab_tab2.html',
						context : this,
						type : 'get',
						dataType : 'html',
						success : function(r) {   
	                        this.appendTitle(this.functionCode+'_planresponsetab2_title','任务追踪');
	                        this.appendContent(this.functionCode+'_planresponsetab2_content',r,true); 
	                        this.ids.push(this.functionCode+"_planresponsetab2_title");
	                        this.ids.push(this.functionCode+"_planresponsetab2_content");
	                        this.planresponsetab2_content= $('#'+this.functionCode+'_planresponsetab2_content');
	                        this.init3();
		                    
						}
					});  
				}, 
				init3 :function(){ 
					$.ajax({
						url : rootPath+'/evecom/ecapp/incident/planresponsetab/html/planresponsetab_tab3.html',
						context : this,
						type : 'get',
						dataType : 'html',
						success : function(r) {   
	                        this.appendTitle(this.functionCode+'_planresponsetab3_title','任务过程');
	                        this.appendContent(this.functionCode+'_planresponsetab3_content',r,true); 
	                        this.ids.push(this.functionCode+"_planresponsetab3_title");
	                        this.ids.push(this.functionCode+"_planresponsetab3_content");
	                        this.planresponsetab3_content= $('#'+this.functionCode+'_planresponsetab3_content');
	                        this.selectTitle(this.functionCode+"_planresponsetab_title");
	                        this.showSider();    
	                        this._windowResizeHandler();
	                        
	                        
	                        
	                        //更新第一个tab
	                        this.initfirstTab(); 
	                        
	                        //第二个标签点击
	                        var $planresponsetab2_content=this.planresponsetab2_content;
	                        $("#"+this.functionCode+"_planresponsetab2_title").one('mouseover',function(evt){
	                        	setTimeout(function(){
	                        		jQuery(".topMarq",$planresponsetab2_content).slide({mainCell:".topMar",autoPlay:true,effect:"topMarquee",vis:1,interTime:50});
	                        	},500);  
	                        });
	                        //第三个tab 的tab
							$(".rSid_box_slide1", this.planresponsetab3_content).slide({titCell: ".box_hd li",mainCell: ".box_bd",trigger:"click"});
							$(".sc",this.planresponsetab3_content).perfectScrollbar();
							
							$("#tab_1", this.planresponsetab3_content).click($.proxy(function(){
								$("#plan_response_disproject_add_a_id",this.planresponsetab3_content).show();
								$("#plan_response_disproject_del_a_id",this.planresponsetab3_content).show();
								
								$("#plan_response_scenperson_add_a_id",this.planresponsetab3_content).hide();
								$("#plan_response_scenperson_del_a_id",this.planresponsetab3_content).hide();
								this.clean();
							},this));
							$("#tab_2", this.planresponsetab3_content).click($.proxy(function(){
								$("#plan_response_disproject_add_a_id",this.planresponsetab3_content).hide();
								$("#plan_response_disproject_del_a_id",this.planresponsetab3_content).hide();
								
								$("#plan_response_scenperson_add_a_id",this.planresponsetab3_content).show();
								$("#plan_response_scenperson_del_a_id",this.planresponsetab3_content).show();
								// 描点现场人员
								//var data=this.projectDatas[this.current_disprojectid];
								//if(data == null){return;}
//								this.initPoint(data.mobileScenpersons);
								this.clean();
								this.fieldPersonPointer();
							},this));
							
							//处置项目新增点击
	                    	$("#plan_response_disproject_add_a_id",this.planresponsetab3_content).unbind('click').click($.proxy(this.taskAddDialog, this));
							
	                    	$("#plan_response_disproject_del_a_id",this.planresponsetab3_content).unbind('click').click($.proxy(function(evt){
	                    		
	                    		if($('.selectTask_hd').length < 1){
	                    			$.easyui.messager.show("请选择要删除的任务！");
	                    			return;
	                    		}
	                    		
	                    		var id = $('.selectTask_hd').attr('id');
	                    		$.messager.confirm('确认','您确认想要删除该任务吗？',$.proxy(function(r){    
	        						if (r){    
	        							$.ajax({
	        								url : evecom.v.contextPath + '/jf/planemergency/eventtask/planeventtaskctr/del',
	        								context : this,
	        								dataType : 'json',
	        								data : {id : id},
	        								success : function(r) {
	        									if (r.success) {
	        										$.easyui.messager.show("删除成功！");
	        										$('.selectTask_hd').remove();
	        										
	        	                                	// 往缓存中更新数据
	        	                                	var currentContainer = this.projectDatas[this.current_disprojectid].planEventTasks;
	        	                                	//console.log("长度："+currentContainer.length);
	        	                                	for(var i=0;i<currentContainer.length;i++){
	        	                                		if(currentContainer[i]["id"] == id){
	        	                                			currentContainer.splice(i,1);
	        	                                			//console.log("删除后："+currentContainer.length);
	        	                                			break ;
	        	                                		}
	        	                                	} 
	        	                                	
	        									}
	        								}
	        							});
	        						}    
	        					},this));
	                    		
	                    	}, this));
	                    	
							//现场人员新增点击
	                    	$("#plan_response_scenperson_add_a_id",this.planresponsetab3_content).unbind('click').click($.proxy(function(){
	                    		$.easyui.messager.show("请点击地图选择人员位置！");
	                    		this.clickAddPoint();
	                    	}, this));
							
	                    	this.fieldPerson();
	                    	// 现场人员删除
	                    	$("#plan_response_scenperson_del_a_id",this.planresponsetab3_content).unbind('click').click($.proxy(function(evt){
	                    		
	                    		if($('.selectPerson_hd').length < 1){
	                    			$.easyui.messager.show("请选择要删除的现场人员！");
	                    			return;
	                    		}
	                    		var id = $('.selectPerson_hd').attr('id');
	                    		$.messager.confirm('确认','您确认想要删除该现场人员吗？',$.proxy(function(r){    
	        						if (r){    
	        							$.ajax({
	        								url : evecom.v.contextPath + '/jf/ecapp/plan/eventresponse/taskResponseCtr/delFieldPerson',
	        								context : this,
	        								dataType : 'json',
	        								data : {id : id},
	        								success : function(r) {
	        									if (r.success) {
	        										$.easyui.messager.show(r.msg);
	        										// 刷新面板内容
//	        										var data=this.projectDatas[this.current_disprojectid];
	        										// 描点现场人员
	        										this.clean();
	        										/*for(var i=0;i<this.projectDatas[this.current_disprojectid]['mobileScenpersons'].length;i++){
	        											if(this.projectDatas[this.current_disprojectid]['mobileScenpersons'][i]['id'] == id){
	        												
	        											}
	        										}*/
	        										this.fieldPersonPointer();
//	        										this.initPoint(data.mobileScenpersons);
	        										$('.selectPerson_hd').remove();
	        									}
	        								}
	        							});
	        						}    
	        					},this));  
	                    	}, this));
						}
					});  
				}, 
				
				//点击输入坐标
				clickAddPoint : function(){
					
					if(this.myaddPointHandler!=undefined){
						return ;
					} 
					//设置描点事件监听  传人回调函数
					this.myaddPointHandler=base.locationObject.addPointEvent($.proxy(this.myAddPoint, this ));
				},
				
								/**'
				 *  单个图标描点跟气泡回调(可手动输入内容)   点击后回调
				 */
				myAddPoint : function(evt) {
					var point = evt.mapPoint;
					//alert(point.x+"  "+point.y);
					this.scenpersonAddDialog(point);
					//释放描点
					dojo.disconnect(this.myaddPointHandler); 
					this.myaddPointHandler=undefined;
					//返回坐标对象
					return point;
				} , 
				
				// 添加现场人员
				scenpersonAddDialog : function(point){
					var dialog = top.eve.modalDialog({
						title : '新增现场人员',
						width : 600,
						height : 310,
						url : evecom.v.contextPath+ '/evecom/ecapp/incident/planresponsetab/fieldperson_add.jsp?eventId='+ this.eventId,
						buttons : [{
							text : '提交',
							iconCls : 'icon-save',
							handler : $.proxy(function() {
								dialog.point = point;
								dialog.find('iframe').get(0).contentWindow.submitForm(dialog,parent.$,this);
							}, this)
						}, {
							text : '取 消',
							iconCls : 'icon-cancel',
							handler : function() {
								dialog.dialog('destroy');
							}
						}]
					});
				},
				
				// 刷新现场人员
				refreshScenperson : function(arr){
					if(arr == null || arr == 'undefined'){return;}
//					this.projectDatas[this.current_disprojectid]['mobileScenpersons'].push(arr[0]);
//					var data=this.projectDatas[this.current_disprojectid];
					
					// 描点现场人员
					this.clean();
//					this.initPoint(data.mobileScenpersons);
					this.fieldPersonPointer();
					
					//现场人员填充
					var mobileScenpersons= arr;//data.mobileScenpersons;
					var $planresponsetab3_content=this.planresponsetab3_content;
					var disposal_progress_scenperson_table= $("#disposal_progress_scenperson_table_id",$planresponsetab3_content);
					//disposal_progress_scenperson_table.empty(); 
					var mobileScenpersons_html=""
					for(var i=0;i<mobileScenpersons.length;i++){
						var json = {
							phone : mobileScenpersons[i].personphone,
							name : mobileScenpersons[i].personname
						}
						var jsonData = $.toJSON(json).replaceAll('"', '\'');
						
						var json2 = {
							phone : mobileScenpersons[i].personphone,
							name : mobileScenpersons[i].personname,
							id : mobileScenpersons[i].id,
							gisx : mobileScenpersons[i].gisx,
							gisy : mobileScenpersons[i].gisy
						}
						var jsonData2 = $.toJSON(json2).replaceAll('"', '\'');
						mobileScenpersons_html+='<tr id="'+mobileScenpersons[i].id+'" data="'+jsonData2+'"  class="person_tr" style="cursor:pointer;"><td>'+mobileScenpersons[i].personname+'</td>'
											+'<td style="width: 40%;">'+mobileScenpersons[i].personphone+'</td>'
											+'<td style="width: 20%;"><a data="'+jsonData+'" href="javascript:void(0);" class="rSid_tab_call personPhoneCall"></a></td></tr>';
					}
					disposal_progress_scenperson_table.prepend(mobileScenpersons_html);
					
					// 打电话
					$('.personPhoneCall',$planresponsetab3_content).unbind().click($.proxy(this.callPhone,this))
					
					$('.person_tr',$planresponsetab3_content).unbind().click($.proxy(function(evt){
						$('.person_tr td',$planresponsetab3_content).css('color','')
						$('.person_tr',$planresponsetab3_content).removeClass('selectPerson_hd');
						
						var $this = $(evt.currentTarget);
						$this.addClass('selectPerson_hd').children('td').css('color','yellow');
						
						this.pointFocus(evt);
					},this))
					
				},
				
				/**
				 * 列表选中效果
				 * @param source
				 * @returns
				 */
				pointFocus : function(source){
					//添加选中样式
					base.map.infoWindow.hide();
					//$(".pointFocusTr td").removeClass("active");
					//$(source.currentTarget.parentNode.parentNode.children).addClass("active");
					
					var item = $.parseJSON($(source.currentTarget).attr('data').replaceAll('\'', '"'));

					var id = source.currentTarget.id;
					
					//把高亮气泡的颜色变成初始颜色
					$(".green" ).removeClass('green').addClass('blue');
					$(".esriPopup").css('z-index','1');
					
					var $popup = $("#"+id+" .popuptab ");
	                $popup.removeClass('blue');
	                $popup.addClass('green');
	                $('#'+id).css('z-index','100');
	                
	                if(base.map.getLayer('planresponse_field_communication') != null){
	                	var graphics = base.map.getLayer("planresponse_field_communication").graphics;
	                	// 还原上一次定位点的图标
	                	var defaultImage = eve.contextPath + '/style/gis/images/police.gif';
	                	if(this.selected != null){
	                		for(var k =0; k<graphics.length; k++){
	                			if(graphics[k].attributes.data.id == this.selected){
	                				graphics[k].setSymbol( new esri.symbol.PictureMarkerSymbol(defaultImage, 32, 32)) ;
	                				break;
	                			}
	                		}
	                	}
	                	var activeImage = eve.contextPath + '/evecom/ecapp/incident/images/dw.gif';
	                	// 刷新本次地位点的图标，并将一次定位的ID和图标设置
	                	for(var k =0; k<graphics.length; k++){
	                        if(graphics[k].attributes.data.id == id){
	                        	// 上一次定位点的ID
	                        	this.selected = id;
	                        	graphics[k].setSymbol( new esri.symbol.PictureMarkerSymbol(activeImage, 32, 32)) ;
	                        	break;
	                        }
	                    }
	                }
		             
					//经纬度为空，则不把资源位置设置为中心点
					if(item.gisx == null || item.gisy == null ){
						return ;
					}
					// 将该资源作为中心点
					var point =  new esri.geometry.Point(Number(item.gisx), Number(item.gisy));
					base.map.centerAndZoom(point, 6);
					
				 },
				 
				/**
				 * 事件注册
				 */
				regist : function(){
					//组织机构新增注册回调
					evecom.v.events.bind('orgination_add_save_topic', $.proxy(function (event) { 
	            		var planEventOrganization = event.data; 
	            		//往机构缓存中压数据
                    	this.orgcache[this.current_eventresponseid].push(planEventOrganization);
                    	this.reloadtab1org(this.orgcache[this.current_eventresponseid]);
                    	
	            	},this)); 
					//组织机构调整点击
					evecom.v.events.bind('orgination_add_edit_topic', $.proxy(function (event) { 
	            		var planEventOrganization = event.data; 
	            		var thisid=planEventOrganization.id;
                    	//往机构缓存中更新数据
                    	var current_orgs = this.orgcache[this.current_eventresponseid]; 
                    	for(var i=0;i<current_orgs.length;i++){
                    		if(current_orgs[i]["id"]==thisid){
                    			current_orgs.splice(i,1);
                    			current_orgs.push(planEventOrganization);
                    			break ;
                    		}
                    	} 
                    	this.orgcache[this.current_eventresponseid]=current_orgs;
                    	this.reloadtab1org(this.orgcache[this.current_eventresponseid]); 
	            	},this)); 
					//任务新增点击
					evecom.v.events.bind('task_add_edit_save_topic', $.proxy(function (event) { 
	            		var planEventTask = event.data;
	            		this.projectDatas[planEventTask.projecttopid].planEventTasks.push(planEventTask);
                    	this.disposalProjectonclick(planEventTask.projecttopid);
	            	},this));
					
					
				},
				/**
				 * 计算tab1 task栏目的高度
				 */
				counttaskheight : function(){
					$this=this;
					this.rSiderHeight_global = $('.rSider .bd').height(); 
					var countheight=0;
					$(".top3class.on").each(function(evt){ 
						countheight+=parseInt($(this).attr("data"));
					}); 
					var lastheight=this.rSiderHeight_global -countheight -210
					$("#tab1_task_tab_div_id",this.planresponsetab1_content).height(lastheight);
				},
				initfirstTab : function(){  
					if(eventId){//有案件
						$.ajax({
		                    url : rootPath + '/jf/planemergency/eventresponse/planeventresponsectr/ifeventhasPlan',
		                    context : this,
		                    dataType : 'json',
		                    data : {eventid : eventId},
		                    async: false,
		                    success : $.proxy(function(r) {
								if(r.success&&r.hasplan){//有预案   
									//更新响应界面
									this.changetoresponsehtml(r.planEventResponse.id);
								} 
		                    },this)
		                }); 
					}  
					//填充预案列表
					this.appendInitplanlist();  
				},
				/**
				 * 加载预案列表 z
				 */
				appendInitplanlist : function(){ 
					var $content = this.planresponsetab1_content;  
					var planlist_sc_table=$("#planlist_sc_table_id",$content); 
					$.ajax({
					url : evecom.v.contextPath + '/jf/textplan/getallplan',
					context : this,
					dataType : 'json',
					async: false,
					success : $.proxy(function(r) {
						var html='';
						for(var i=0;i<r.planInfos.length;i++){
							var plan=r.planInfos[i];
							html+='<tr '+((i%2==1)?'class="even"':'')+' ><td style="white-space: normal;word-break:break-all;">'+plan.planname+'</td><td style="border-right: 0;">'
								+'<a href="#" id="'+plan.id+'" class="rSid_tab_search showplanDetail" title="详细"></a>'
								+'<a href="#" class="rSid_tab_play start_plan" id="'+plan.id+'" title="启动"></a></td></tr>';
						} 
						planlist_sc_table.html(html);
						  
					    //滚动条
						$(".sc",this.planresponsetab1_content).perfectScrollbar();
						
						//启动点击
						$('.start_plan',planlist_sc_table).click($.proxy(function(evt){
							if(eventId==null){
								$.messager.alert('<font color=black>提示</font>',
										"暂无事件", 'error' );
								return ;
							} 
							this.openLevelChoseMsgPopupBox($.proxy(function(re,responseLevel){ 
								if(re){
									var url="";
									var pram_data={};
									if(this.ifchangeplan){//更新预案
										url=evecom.v.contextPath + '/jf/planemergency/eventresponse/planeventresponsectr/updatePlanEventResponse';
										pram_data={planid : evt.target.id ,
												   eventid : eventId,
												   level:responseLevel,
												   planeventresponseid:this.current_eventresponseid};
									}else{//启动根预案响应
										url=evecom.v.contextPath + '/jf/planemergency/eventresponse/planeventresponsectr/eventStartPlan';
										pram_data={planid : evt.target.id ,
												   eventid : eventId,
												   level : responseLevel };
									}
									$.ajax({
										url : url,
										context : this,
										dataType : 'json',
										data : pram_data,
										async: false,
										success : $.proxy(function(r) {
											if(r.success){//成功加载响应页面 
												this.changetoresponsehtml(r.planeventresponseid);
											}
										},this)
										});
								}
							},this)); 
						},this));
						
						//预案详细点击 
						$(".showplanDetail").click($.proxy(this.clickplanDetail, this));
						
						
						},this)
					});
				} ,
				
				/**
	             * 预案详情
	             *  
	             * @author Mars zhang
	             * @created 2015年7月9日 下午14:02:00
	             */
	            clickplanDetail : function(evt){
					var dialog = top.eve.modalDialog({
						title : '预案详细',
						width : 760,
						height : 500,
						url : evecom.v.contextPath+ '/jf/textplan/getplanview?planId='+evt.target.id,
						buttons : [
							{
								text : '关 闭',
								iconCls : 'icon-cancel',
								handler : function() {
									dialog.dialog('destroy');
								}
							} ]
					}); 
	            }, 

	            /**
	             * 将tab加载为预案响应页面
	             * 
	             * @author Mars zhang
	             * @created 2015年7月9日 下午14:02:00
	             */
	            changetoresponsehtml : function(planEventResponseId){
	            	//当前事件响应
	            	this.current_eventresponseid=planEventResponseId;
	            	
                	var $content = this.planresponsetab1_content;
                	//隐藏列表
                	this.changeplanlistshow(false);   
                	//显示响应
                	this.changeeventresponseshow(true);
                	
                	$(".sc",this.planresponsetab1_content).perfectScrollbar();
                	var $this=this;
				    //tab1 收缩
                	$(".rSid_slide_title_span").unbind('click').click(function() {
				    	$(this).parent().toggleClass("on").next(".rSid_box_cont").slideToggle();
				    	//计算任务安排高度
				    	$this.counttaskheight();
				    	});
				    $(".schedule_title" ,$content).unbind('click').click(function() {
				    	$(this).toggleClass("on").next(".schedule_tabDiv").slideToggle();
				    }); 
                	//加载预案树型
                	this.loadPlanTree(planEventResponseId);
                	
                	//根据planEventResponseId更新数据
                	this.refreshEventresponseByEventresponseid(planEventResponseId);
                	
                	//变更响应预案点击
                	$("#plan_response_change_plan_a_id",$content).unbind('click').click($.proxy(this.changePlan, this));
                	
                	//创建预案分支点击
                	$("#title_list_drop_btn1",$content).unbind('click').click($.proxy(this.createPlanBranch, this));
                	
                	//响应级别参考点击
                	$("#planeventresponse_level_a_id",$content).unbind('click').click($.proxy(this.openLevelMsgPopupBox, this));
                	
                	//组织机构新增点击
                	$("#plan_response_org_add_a_id",$content).unbind('click').click($.proxy(this.orgaddDialog, this));
                	
                	
                	//更新第二个tab
                    this.updatemessagehtml();
                 
	            },
	            /**
	             * 创建预案分支
	             *  
	             * @author Mars zhang
	             * @created 2015年7月9日 下午14:02:00
	             */
	            createPlanBranch : function(evt){
					var dialog = parent.eve.modalDialog({
						title : '创建预案分支',
						width : 580,
						height : 335,
						url : evecom.v.contextPath+ '/jf/planemergency/eventresponse/planeventresponsectr/createPlanBranchJSP?eventid='+eventId,
						buttons : [
							{
								text : '确定',
								iconCls : 'icon-save',
								handler : $.proxy(function() { 
									dialog.find("iframe").get(0).contentWindow.submitForm(
        	                                dialog,window,$.proxy(function(){  
        	                                	//异步刷新预案树
        	                                	this.loadPlanTree(this.current_eventresponseid);
        	                                },this));
								},this)
							},{
								text : '取消',
								iconCls : 'icon-cancel',
								handler : function() {
									dialog.dialog('destroy');
								}
							} ]
					}); 
	            }, 
	            /**
	             * 变更响应预案
	             */
	            changePlan : function(){ 
	            	this.ifchangeplan=true;
	            	//列表
                	this.changeplanlistshow(true);   
                	//响应
                	this.changeeventresponseshow(false);
                	//显示返回键
                	var $plan_list_back_span=$("#plan_list_back_span_id",this.planresponsetab1_content);
                	$plan_list_back_span.show();
                	//返回键点击注册
                	$plan_list_back_span.unbind('click').click($.proxy(function(){
                		//列表
                    	this.changeplanlistshow(false);   
                    	//响应
                    	this.changeeventresponseshow(true);
                	}, this));
                	
	            },
	            /**
	             * 打开级别参考框
	             */
	            openLevelChoseMsgPopupBox : function(fun){ 
	            	$.ajax({
	                    url : evecom.v.contextPath + '/evecom/ecapp/incident/pushbox/html/push_box_two_btn.html',
	                    context : this,
	                    dataType : 'html',
	                    async: false,
	                    success : $.proxy(function(r) {
	                    	$("body").append(r);
	                    	var $div=$("#push_box_top_div");
	                    	$("#push_box_close_a_id").click($.proxy(function(evt){
	                    		if($div)$div.remove();
	                    	},this));  
	                    	var $push_box_title_div=$("#push_box_title_div_id").html("请选择预案响应级别");
	                    	//确定
	                    	$("#chosed",$div).click(function(){
	                    		var responseLevel = $("input:checked",$div).map(function () {
	                                return $(this).val();
	                            }).get().join('#'); 
	                    		if(!responseLevel){//没有选择级别时标红  并返回
	                    			$push_box_title_div[0].style.color="#f00";
	                    			return;
	                    		}
	                    		fun(true,responseLevel);
	                    		if($div)$div.remove();
	                    	});
	                    	//取消
	                    	$("#cancle",$div).click(function(){
	                    		//fun(false);
	                    		if($div)$div.remove();
	                    	});
	                    	//单选  
	                    	$(".checkbox_class",$div).click(function(th){
	                    		var checkboxs=$(".checkbox_class",$div);
	                        	for(var i=0;i<checkboxs.length;i++){ 
	                        			checkboxs[i].checked=false; 
	                        	} 
	                    		th.currentTarget.checked=true; 
	                    	}); 
	                    	
	                    	})
	                    });
	            }, 
	            /**
	             * 打开级别参考框
	             */
	            openLevelMsgPopupBox : function(evt){
	            	
	            	$.ajax({
	                    url : evecom.v.contextPath + '/evecom/ecapp/incident/pushbox/html/push_box_no_btn.html',
	                    context : this,
	                    dataType : 'html',
	                    async: false,
	                    success : $.proxy(function(r) {
	                    	$("body").append(r);
	                    	var $div=$("#push_box_top_div");
	                    	$("#push_box_close_a_id").click($.proxy(function(evt){
	                    		if($div)$div.remove();
	                    	},this));  
	                    	$("#push_box_title_div_id").html("预案响应指标");
	                    	
	                    	})
	                    });
	            }, 
	            /**
	             * 组织机构新增点击
	             */
	            orgaddDialog : function(){
	            	var dialog = top.eve.modalDialog({
						title : '新增组织机构',
						width : 580,
						height : 395,
						url : evecom.v.contextPath+ '/jf/planemergency/eventresponse/planeventorganizationctr/getAddJsp?planresponseid='+this.current_eventresponseid,
						buttons : [
							{
								text : '确定',
								iconCls : 'icon-save',
								handler : $.proxy(function() { 
									dialog.find("iframe").get(0).contentWindow.submitForm(
        	                                dialog,$.proxy(function(planEventOrganization){  
        	                                	//往机构缓存中压数据
        	                                	this.orgcache[this.current_eventresponseid].push(planEventOrganization);
        	                                	this.reloadtab1org(this.orgcache[this.current_eventresponseid]);
        	                                },this));
								},this)
							},{
								text : '取消',
								iconCls : 'icon-cancel',
								handler : function() {
									dialog.dialog('destroy');
								}
							} ]
					});
	            	
	            	
	            },
	            /**
	             * 组织机构调整点击
	             */
	            orgeditDialog : function(orgid){
	            	var dialog = top.eve.modalDialog({
						title : '编辑组织机构',
						width : 580,
						height : 395,
						url : evecom.v.contextPath+ '/jf/planemergency/eventresponse/planeventorganizationctr/getEditJsp?orgid='+orgid,
						buttons : [
							{
								text : '确定',
								iconCls : 'icon-save',
								handler : $.proxy(function() { 
									dialog.find("iframe").get(0).contentWindow.submitForm(
        	                                dialog,$.proxy(function(planEventOrganization){
        	                                	var thisid=planEventOrganization.id;
        	                                	//往机构缓存中更新数据
        	                                	var current_orgs = this.orgcache[this.current_eventresponseid]; 
        	                                	for(var i=0;i<current_orgs.length;i++){
        	                                		if(current_orgs[i]["id"]==thisid){
        	                                			current_orgs.splice(i,1);
        	                                			current_orgs.push(planEventOrganization);
        	                                			break ;
        	                                		}
        	                                	} 
        	                                	this.orgcache[this.current_eventresponseid]=current_orgs;
        	                                	this.reloadtab1org(this.orgcache[this.current_eventresponseid]);
        	                                	
        	                                },this));
								},this)
							},{
								text : '取消',
								iconCls : 'icon-cancel',
								handler : function() {
									dialog.dialog('destroy');
								}
							} ]
					}); 
	            	
	            	
	            },
	            /**
	             * 删除组织机构
	             */
	            orgdeleteDialog :  function(orgid){ 
	            	$.messager.confirm('提示','您确认想要该组织吗？',$.proxy(function(r){    
	            	    if (r){    
	            	    	$.ajax({
	    						url : evecom.v.contextPath + '/jf/planemergency/eventresponse/planeventorganizationctr/delete',
	    						context : this,
	    						dataType : 'json',
	    						data : {orgid : orgid },
	    						async: false,
	    						success : $.proxy(function(r) { 
	    							if(r.success){
	    								var thisid=r.orgid;
	                                	//删除机构缓存中的数据
	                                	var current_orgs = this.orgcache[this.current_eventresponseid]; 
	                                	for(var i=0;i<current_orgs.length;i++){
	                                		if(current_orgs[i]["id"]==thisid){
	                                			current_orgs.splice(i,1); 
	                                			break ;
	                                		}
	                                	} 
	                                	this.orgcache[this.current_eventresponseid]=current_orgs;
	                                	this.reloadtab1org(this.orgcache[this.current_eventresponseid]);
	    							}
	    						},this)});
	            	    }    
	            	},this)); 
	            },
	            /**
	             * 根据planEventResponseId创建树结构
	             */
	            loadPlanTree : function(planEventResponseId){   
	            	var $content = this.planresponsetab_content;  
	            	this.tree=$('#plan_tree_ul_id',$content).tree({
	                    url : evecom.v.contextPath+'/jf/planemergency/eventresponse/planeventresponsectr/combotree?root=alltree&planeventresponseid='+planEventResponseId,
	                    parentField : 'pid',
	                    lines : false,
	                    animate:true,
	                    dataPlain : true,//线树转换
	                    onClick: $.proxy(function(node){
	                    	//排除重复点击
	                    	if(this.current_eventresponseid==node.id)return;
	                	    //当前事件响应
	    	            	this.current_eventresponseid=node.id;
	    	            	//置空当前处置项目
	    	            	this.current_disprojectid=undefined;
	    	            	//根据planEventResponseId更新数据
	                    	this.refreshEventresponseByEventresponseid(node.id);
	                    	//更新第二个tab
	                        this.updatemessagehtml();
	                    },this),formatter: $.proxy(function(node){
	                    	var title = "";
	                    	title+=this.ifnull(node.attributes.areaname," ",10)+":&nbsp" 
	                    	title+=this.ifnull(node.text," ",14) ;
	                        var s = '<span id="'+node.id+'" class="sstreenodeclickclass" title="'+ node.text +'" >'
	                        + title + '</span>'; 
	                        return s;
	                    },this),
	                    onLoadSuccess : $.proxy(function(){ 
	                    },this)
	                }); 
	            }, 
	            
	            /**
	             * 1、初始化时调用 2、点击树时调用
	             * 刷新响应界面
	             */
	            refreshEventresponseByEventresponseid : function(planEventResponseId){
	            	$.ajax({
						url : evecom.v.contextPath + '/jf/planemergency/eventresponse/planeventresponsectr/getEventResponseInfosByResponseId',
						context : this,
						dataType : 'json',
						data : {planeventresponseid : planEventResponseId },
						async: false,
						success : $.proxy(function(r) { 
							var $planname_span=$("#planname_span_id",this.planresponsetab1_content); 
							//填充预案名称
							$planname_span[0].value=this.ifnull(r.planInfo.planname,"",10);
							$planname_span[0].textContent=this.ifnull(r.planInfo.planname," ",10);
							$planname_span[0].title=r.planInfo.planname; 
							var jb={1:'一级',2:'二级',3:'三级',4:'四级'};
							//填充响应级别
							var $level_span=$("#plan_response_response_level_span_id",this.planresponsetab1_content); 
							$level_span.html(jb[parseInt(r.planEventResponse.reslevel)]);
							//填充事件指标 
							var inms=r.eventInidmonitors;
							var $eventinidmonitors_table=$("#planresponse_eventinidmonitors_table_id",this.planresponsetab1_content); 
							var inms_html=[];
							for(var i=0;i<inms.length;i++){
								inms_html.push('<tr><th style="width: 80px;border-right: inherit;">');
								inms_html.push(this.ifnull(inms[i]['indiname']));
								inms_html.push('</th><td style="white-space: normal;padding-left: 2px;text-align: left;border-right: inherit;">');
								inms_html.push(this.ifnull(inms[i]['indivalue']));
								inms_html.push('</td></tr>');
							}  
							$eventinidmonitors_table.html(inms_html.join(''));
//							
							//组织机构填充
							var orgs=r.eventOrganizations;
							this.orgcache[this.current_eventresponseid]=orgs;
				        	this.reloadtab1org(this.orgcache[this.current_eventresponseid]);
				        	
							//填充处置项目
							var project_html=[];
							for(var i=0;i<r.eventDisprojects.length;i++){
								var eventDisproject= r.eventDisprojects[i];
								var even= (i%2==1)?'':'even';
								project_html.push('<tr id="'+eventDisproject.id+'" style="" class="'+even+' eventDisprojects">');
								project_html.push('<td style="width: 30px;border: 1px solid #474b54;" class="upCase">');
								project_html.push('<span class="plan_disproject_state_span_class_'+(eventDisproject.state-1)+'"></span>');
								project_html.push('</td><td style="width: 150px;cursor: pointer;border: 1px solid #474b54;">');
								project_html.push(this.ifnull(eventDisproject.pname));
								project_html.push('</td></tr>');
							} 
							var $plan_disproject_list_table=$("#plan_disproject_list_table");
							$plan_disproject_list_table.html(project_html.join(''));
							$(".schedule_title" ,this.planresponsetab1_content).unbind('click').click(function() {
						    	$(this).toggleClass("on").next(".schedule_tabDiv").slideToggle();
						    });  
							//处置项目点击
							$(".eventDisprojects" ,$plan_disproject_list_table).unbind('click').click($.proxy(function(evt) {
								this.disposalProjectonclick(evt.currentTarget.id);
							},this));
							
							
							
							
						},this)
						});
	            },
	            /**
	             * 重新加载 tab1的组织机构
	             */
	            reloadtab1org : function(orgs){
	            	
	            	//给组织机构按分组类型分组
	            	var temporgs={};
	            	for(var i=0;i<orgs.length;i++){
	            		if(temporgs[orgs[i]["orgtypeid"]]){
	            			temporgs[orgs[i]["orgtypeid"]].push(orgs[i]);
	            		}else{
	            			temporgs[orgs[i]["orgtypeid"]]=[];
	            			temporgs[orgs[i]["orgtypeid"]].push(orgs[i]);
	            		}  
	            	} 
	            	orgs=[];
	            	//再把几个组合并成一个数组  遍历对象属性  并连接数组
	            	for(var s in temporgs ){
	            		orgs=orgs.concat(temporgs[s]);
	            	}  
	            	
					var $org_div=$("#plan_response_org_div_id",this.planresponsetab1_content); 
					var org_html=[]; 
					var orgid_temp="";
					if(orgs.length>0){//先加载头部
						org_html.push('<div class="schedule"><a id="190167" href="javascript:void(0);" class="schedule_title bdname on">');
						org_html.push(this.ifnull(orgs[0]["groupname"]));
						org_html.push('</a><div class="schedule_tabDiv" style="display: block;">');
						org_html.push('<table id="bd_table_190167" class="schedule_tab" cellpadding="0" cellspacing="0">');
					}
					for(var i=0;i<orgs.length;i++){
						if(orgs[i]["orgtypeid"]!=orgid_temp&&i!=0){//类型不同且不是第一个时加载尾部头部
							//尾部
							org_html.push('</table></div></div>');
							//头部
							org_html.push('<div class="schedule"><a id="190167" href="javascript:void(0);" class="schedule_title bdname on">');
							org_html.push(this.ifnull(orgs[i]["groupname"]));
							org_html.push('</a><div class="schedule_tabDiv" style="display: block;">');
							org_html.push('<table id="bd_table_190167" class="schedule_tab" cellpadding="0" cellspacing="0">');
						}  
						orgid_temp=orgs[i]["orgtypeid"];
						//item
						org_html.push('<tr><th></th><td><a style="color:#fff" id="248951" class="clickFocus">');
						org_html.push(this.ifnull(orgs[i]["head"])+'('+this.ifnull(orgs[i]["role"])+')');
						org_html.push('</a></td><td style="width:12px;"><a href="javascript:void(0);" id="');
						org_html.push(orgs[i].id);
						org_html.push('" class="schedule_call_btn"></a></td>');
						org_html.push('<td style="width:20px;"><span   id="');
						org_html.push(orgs[i].id);
						org_html.push('" class="plan_event_org_item_btn_class plan_event_org_item_edit_class"></span>'); 
						org_html.push('</td><td style="width:20px;"><span   id="');
						org_html.push(orgs[i].id);
						org_html.push('" class="plan_event_org_item_btn_class plan_event_org_item_delete_class"></span></td></tr>'); 
						
						
						
					}
					if(orgs.length>0){//最后加载尾部
						org_html.push('</table></div></div>');
					}
					$org_div.html(org_html.join(''));
					
					//组织机构调整调整点击
					$(".plan_event_org_item_edit_class" ,this.planresponsetab1_content).unbind('click').click($.proxy(function(evt) {
						this.orgeditDialog(evt.currentTarget.id);
					},this));
					//组织机构删除点击
					$(".plan_event_org_item_delete_class" ,this.planresponsetab1_content).unbind('click').click($.proxy(function(evt) {
						this.orgdeleteDialog(evt.currentTarget.id);
					},this));
					//收缩点击
					$(".schedule_title" ,this.planresponsetab1_content).unbind('click').click(function() {
				    	$(this).toggleClass("on").next(".schedule_tabDiv").slideToggle();
				    }); 
					
	            },
	            /**
	             * 改变列表显示状态
	             */
	            changeplanlistshow : function(ifshow){
	            	var $plan_list_top_div=$("#plan_list_top_div_id",this.planresponsetab1_content);
	            	if(ifshow){//显示列表 
	                	if($plan_list_top_div)$plan_list_top_div.show();
	            	}else{//隐藏列表
	            		if($plan_list_top_div)$plan_list_top_div.hide();
	            	}
	            },
	            /**
	             * 改变事件响应显示状态
	             */
	            changeeventresponseshow : function(ifshow){
	            	var $plan_response_top_div=$("#plan_response_top_div_id",this.planresponsetab1_content);
	            	if(ifshow){//显示列表 
	                	if($plan_response_top_div)$plan_response_top_div.show();
	            	}else{//隐藏列表
	            		if($plan_response_top_div)$plan_response_top_div.hide();
	            	}
	            },
	            
	            
/*****************************tab2******************************/      
	            /**
				 * 更新处置信息界面
				 * 
				 * @author Mars zhang
				 * @created 2015年5月29日 下午15:02:00
				 */
				updatemessagehtml:function(){ 
					//更新上部处置项目
					var disposal_1_project_ul_id=$("#disposal_1_project_ul_id",this.planresponsetab2_content);
					$.ajax({
				        type: "POST",
				        context : this,
				        url: rootPath + '/jf/planemergency/eventresponse/planeventdisprojectctr/getAllProjectByEventResponseId',
				        data: {planeventresponseid : this.current_eventresponseid },
				        dataType: "json",
				        success: $.proxy(function(data){
				        	//tab2处置项目填充
				        	disposal_1_project_ul_id.empty();
				        	var planEventDisprojects=data.planEventDisprojects;
				        	if(!planEventDisprojects){
				        		return ;
				        	} 
				        	var color=['#F96F2E','#DB42D3','#16BB0F','#F96F2E','#EE4A4A','#9BFEFF'];
				        	for(var i=0;i<planEventDisprojects.length;i++){
				        		var disposal_1_project_html="<li id='"+planEventDisprojects[i].id+"'><h4>" //src='../images/alert"+planEventDisprojects[i].projectcode+".png'
				        			+"<span style='width: 30px;height: 30px;display: inline-block;"
				        			+"vertical-align: middle;' class='"+planEventDisprojects[i].iconclass+"' alt=''></span><span style='color:"+color[i%7]+"'>"+planEventDisprojects[i].pname
				        			+"</span><span class='rSid_box_t_btns'>"
				        			+"<a  href='#' class='rSid_tab_copy'></a>"
				        			+"</span></h4></li>"
				        		disposal_1_project_ul_id.append(disposal_1_project_html);	
				        		this.appendmainexplan(planEventDisprojects[i]);
				        	} 
				        	//滚动条
							$(".sc",this.planresponsetab2_content).perfectScrollbar();
				        	//处置项目点击
				        	$('.rSid_tab_copy', disposal_1_project_ul_id).click($.proxy(function(th){
								var a=$(th.target).parent().parent().parent();
								var projectid=a.attr("id"); 
				        		this.disposalProjectonclick(projectid);
				        	}, this));  
				        	
				        	//更新下部任务反馈  
				        	var  disposal_task_responses_ul=$("#disposal_task_responses_ul_id",this.planresponsetab2_content)
				        		disposal_task_responses_ul.empty();
				        		$.ajax({
							        type: "POST",
							        context : this,
							        url: rootPath + '/jf/ecapp/plan/eventresponse/taskResponseCtr/getAllTaskResponseByEventResponseId',
							        data: {planeventresponseid : this.current_eventresponseid },
							        dataType: "json",
							        success: function(data){  
							        	var taskResponses_html=""
							        	this.taskResponses=data.taskResponses;
							        	if(this.taskResponses&&this.taskResponses.length>0){
								        	for(var i=0;i<this.taskResponses.length;i++){
								        		
								        		//图片 
								        		var imghtml=""
								            	var imgids=new Array();
								            	if(this.taskResponses[i].detailattach){ 
								            		imgids=this.taskResponses[i].detailattach.split(',');
								            		for(var j=0;j<imgids.length;j++){
								            			imghtml+="<li><a  id='"+imgids[j]+"'  class='FloatImage_a' href='#'><img src='../images/event_pic.png' alt=''></a></li>";
								            		} 
								            	}  
								        		taskResponses_html+='<li class="level-blue" style=""><div class="clearfloat tab_title">'
								        			+'<label style="width:150px;">'+this.taskResponses[i].createtime+'</label></div>'
								        			+'<table style="width:100%;margin-left: 0;" class="aj_nob_tab2" cellpadding="0" cellspacing="0"><tr>'
								        			+'<td style="width:30px;"></td><td ><span style="color:#fffc04;">'+this.taskResponses[i].deptname+'</span>:'
								        			+''+ this.taskResponses[i].responsecon+'</td></tr>'
								        			+'<tr><td ></td><td ><ul class="events_pics clearfloat">'
								        			+imghtml+"</ul></td></tr></table></li>";
								        	}
								        }
							        	taskResponses_html+="<li></li>"
							        	disposal_task_responses_ul.html(taskResponses_html);
							        	
							        	//图片浮动
							        	$(".FloatImage_a").powerFloat({
							        	    targetMode: "ajax",
							        	    target: function() {
							        	        return rootPath+"/jf/ecapp/plan/eventresponse/taskResponseCtr/getfilebyid?fileid="+$(this).attr("id");	
							        	    },
							        	    hoverHold: false,
							        	    position: "5-7"
							        	});
							        	
							        	$('.FloatImage_a', disposal_task_responses_ul).click($.proxy(this.imageViewClick, this)); 
							        	
							        	//从后台获取的方式刷新
							        	//this.refreshresponse();
							        	
							        	//amq方式监听消息队列
							        	var topics=[];//主题
							        	var second=10;//几秒刷新
							        	topics.push({topic:'ecssp.planeventtaskresponse',
					        					selector:" eventid = "+"'"+eventId+"'"});
							        	//某个模块dom对象   几秒刷新   amq主题    开始刷新回调
							        	this.initAmqMsg($('#'+this.functionCode+'_planresponsetab2_content'),second,topics,function(self){ 
							        		//events主题    收到消息回调
							        		evecom.v.events.bind('ecssp.planeventtaskresponse', function (event) { 
							        				console.log(event.data);
							            		//self.planeventtaskresponsehandler(event.data);
							            	});
							        	});
							        	
							        	
							        }});
				        	
				        	//详细点击注册事件
				        	//$('#disposalInfo_1a_id').click($.proxy(this.disposalInfo, this)); 
				        	
				        } ,this)
				    });  
				},
				/**
				 * 初始化amq方式监听消息队列   该函数不用动直接考走  
				 */
				initAmqMsg : function(dom,second,topics,onloadcallback){
					$.ajax({
				        type: "POST",
				        url: rootPath + '/jfs/ecssp/amq/getAmqUrl',
				        data: {},
				        dataType: "json",
				        context : this,
				        success: function(data){    
				        	//创建amqiframe
				        	//exec_iframe(data.amqurl,topics,'http://'+window.location.host+'<%=contextPath %>'+data.amqproxyurl,second||10);
				        	var url = data.amqurl; 
				        	var proxyurl = 'http://'+window.location.host+rootPath+data.amqproxyurl;
				        	var maoobj = {second:second,
						  				proxyurl:proxyurl,
						  				topics:topics};
				        	var exec_iframe_obj;
				        	exec_iframe_obj = document.createElement('iframe');  
				        	exec_iframe_obj.name = 'exec_iframe_obj';  
				        	exec_iframe_obj.src = url+'#' + JSON.stringify(maoobj);  
				        	exec_iframe_obj.style.display = 'none';  
				        	dom.append(exec_iframe_obj);
				        	onloadcallback(this);
				            //document.body.appendChild(exec_iframe_obj); 
				        	
				        }});
				}, 
				/**
				 * 更新处置信息界面-->重要指标
				 * 
				 * @author Mars zhang
				 * @created 2015年5月29日 下午15:02:00
				 */
				appendmainexplan:function(planEventDisproject){
					var planEventDisproject_li=$("#"+planEventDisproject.id,this.planresponsetab2_content);
					
					 $.ajax({
				        type: "POST",
				        context : this,
				        url: rootPath + '/jf/ecapp/plan/eventresponse/taskResponseCtr/getTaskAndResponse',
				        data: {eventId : planEventDisproject.eventid,
				        	functionCode:planEventDisproject.projectcode,
				        	projectId:planEventDisproject.id },
				        dataType: "json",
				        success: function(data){
				        	var key=planEventDisproject.id;
				        	this.projectDatas[key]=data;
				        	
				        	planEventTasks=data.planEventTasks;
				        	//填充处置项目下存在任务中的关键指标
				        	for(var i=0;i<planEventTasks.length;i++){
				        		if(planEventTasks[i].keyword){
				        			planEventDisproject_li.append("<p>"+planEventTasks[i].keyword+"</p>");
				        		}
				        	} 
				        }});  
				}, 
				disposalInfo:function(th){ 
					createWindow("/ecssp/jf/ecapp/plan/eventresponse/taskResponseCtr/renderdisposalInfoJsp?eventId="+eventId,"应急处置")
				},
/*****************************tab2******************************/	            
/*****************************tab3******************************/	
				/**
				 * 处置项目点击
				 * @param th
				 */
				disposalProjectonclick  : function(projectid){
					this.current_disprojectid = projectid;
					var $planresponsetab3_content=this.planresponsetab3_content;
					var data=this.projectDatas[projectid];
//					$("#F2_3_disposalprocess_title").trigger("mouseover");
					//如果就传值就不显示  否则就显示      处置项目点击时显示   第一次加载时
					this.selectTitle(this.functionCode+"_planresponsetab3_title");
					
					
					//修改处置项目名称
					var planEventDisproject=data.planEventDisproject;
					var disposal_progress_projectname_span=$("#disposal_progress_projectname_span_id",$planresponsetab3_content);
					disposal_progress_projectname_span.empty();
					disposal_progress_projectname_span.html(planEventDisproject.pname);
					disposal_progress_projectname_span.attr("data",planEventDisproject.id);
					$("#disposal_progress_projectname_image_id",$planresponsetab3_content).attr("class",planEventDisproject.iconclass);
					
					//任务安排填充
					var planEventTasks =data.planEventTasks;
					var disposal_progress_taskarrange_table=$("#disposal_progress_taskarrange_table_id",$planresponsetab3_content);
					disposal_progress_taskarrange_table.empty();
					var planEventTasks_html="";
					for(var i=0;i<planEventTasks.length;i++){
						planEventTasks_html+='<tr style="cursor:pointer;" id="'+planEventTasks[i].id+'" class="task_tr_click"><td style="width: 110px;">'+this.ifnull(planEventTasks[i].taskdept)+'</td>'
						+'<td>'+planEventTasks[i].taskname+'</td></tr>';
					}
					disposal_progress_taskarrange_table.html(planEventTasks_html);
					
					// 任务安排行点击事件
					$('.task_tr_click', $planresponsetab3_content).click($.proxy(function(evt){
						
						$('.task_tr_click td',$planresponsetab3_content).css('color','')
						$('.task_tr_click',$planresponsetab3_content).removeClass('selectTask_hd');
						
						var $this = $(evt.currentTarget);
						$this.addClass('selectTask_hd').children('td').css('color','yellow');
						
					},this))
					
					//现场情况
					/*var disposal_progress_scensituation_table=$("#disposal_progress_scensituation_table_id");
					disposal_progress_scensituation_table.empty();
					var planEventTaskskeyword_html="";
					for(var i=0;i<planEventTasks.length;i++){
		        		if(planEventTasks[i].keyword){
		        			planEventTaskskeyword_html+="<tr><th style=''>"+planEventTasks[i].keyword+"</th></tr>";
		        		}
		        	}
					disposal_progress_scensituation_table.html(planEventTaskskeyword_html);*/
					
					
					//现场人员填充
					//var mobileScenpersons=data.mobileScenpersons;
					
					//this.clean();
					//this.initPoint(mobileScenpersons);
					
					
					/*var disposal_progress_scenperson_table=$("#disposal_progress_scenperson_table_id",$planresponsetab3_content);
					disposal_progress_scenperson_table.empty(); 
					var mobileScenpersons_html=""
					for(var i=0;i<mobileScenpersons.length;i++){
						var json = {
							phone : mobileScenpersons[i].personphone,
							name : mobileScenpersons[i].personname
						}
						var jsonData = $.toJSON(json).replaceAll('"', '\'');
						mobileScenpersons_html+='<tr id="'+mobileScenpersons[i].id+'" style="cursor:pointer;" class="person_tr"><td>'+mobileScenpersons[i].personname+'</td>'
						+'<td style="width: 40%;">'+mobileScenpersons[i].personphone+'</td>'
						+'<td style="width: 20%;"><a data="'+jsonData+'" href="javascript:void(0);" class="rSid_tab_call personPhoneCall"></a></td></tr>';
					}
					disposal_progress_scenperson_table.html(mobileScenpersons_html);*/
					// 打电话
					/*$('.personPhoneCall',$planresponsetab3_content).unbind().click($.proxy(this.callPhone,this))
					
					$('.person_tr',$planresponsetab3_content).unbind().click($.proxy(function(evt){
						$('.person_tr td',$planresponsetab3_content).css('color','')
						$('.person_tr',$planresponsetab3_content).removeClass('selectPerson_hd');
						
						var $this = $(evt.currentTarget);
						$this.addClass('selectPerson_hd').children('td').css('color','yellow');
						
						this.pointFocus(evt);
					},this))*/
					
					//填充处置项目下的任务反馈列表 
					taskResponses=data.taskResponses;
					var taskresponse_html="";
					
					if(taskResponses){
						for(var i=0;i<taskResponses.length;i++){
							var newresponseimg = '';
							if (taskResponses[i]["isnew"]) {
								newresponseimg = "<img src='"
										+ rootPath
										+ "/evecom/ecapp/plan/planeventresponse/taskresponse/image/new_response.png' "
										+ " alt='新反馈'  style='margin-left: 20px;margin-top: 4px;'>";
							}
							
							//图片 
			        		var imghtml=""
			            	var imgids=new Array();
			            	if(taskResponses[i].detailattach){ 
			            		imgids=taskResponses[i].detailattach.split(',');
			            		for(var j=0;j<imgids.length;j++){
			            			imghtml+="<li><a class='FloatImage_a' id='"+imgids[j]+"' href='#'><img src='../images/event_pic.png' alt=''></a></li>";
			            		} 
			            	}  
			            	taskresponse_html+='<li class=""><label>'+taskResponses[i].createtime+newresponseimg+'</label>'
			        			+ '<table class="aj_nob_tab2" cellpadding="0" cellspacing="0">'
			        			+ '<tr>'
			        			+ '<td ><span style="color:#fffc04;">'+taskResponses[i].deptname+'</span>:'+taskResponses[i].responsecon+'</td></tr>'
			        			+ '<tr><td><ul class="events_pics clearfloat">'
			        			+ imghtml + '</ul></td></tr></table></li>'; 
						}
					}
					taskresponse_html+="<li></li>" 
					var topMar_2 = '<div id="disposal_progress_taskresponse_div_id" class="topMar_2" style="height:100%;">'+
									'<div class="topMar_slide" >'+
								   '<ul id="disposal_progress_taskresponse_ul_id" class="events">'+
								   '</ul></div></div>'; 
					jQuery("#linkagedisposal_tab2_scoll_div",$planresponsetab3_content).html(topMar_2);
					var disposal_progress_taskresponse_ul=$("#disposal_progress_taskresponse_ul_id",$planresponsetab3_content);
					disposal_progress_taskresponse_ul.html(taskresponse_html);
					setTimeout(function(){
						jQuery(".topMar_2",$planresponsetab3_content).slide({mainCell:".topMar_slide",autoPlay:true,effect:"topMarquee",vis:1,interTime:50});
						
						//图片浮动
			        	$(".FloatImage_a",$planresponsetab3_content).powerFloat({
			        	    targetMode: "ajax",
			        	    target: function() {
			        	        return rootPath+"/jf/ecapp/plan/eventresponse/taskResponseCtr/getfilebyid?fileid="+$(this).attr("id");	
			        	    },
			        	    hoverHold: false,
			        	    position: "5-7"
			        	}); 
					}, 1000);
					$('.FloatImage_a', disposal_progress_taskresponse_ul).unbind('click').click($.proxy(this.imageViewClick, this)); 
					
				},
				
				// 加载现场人员
				fieldPerson : function(){
					$.ajax({
	                    url : evecom.v.contextPath + '/jf/ecapp/plan/eventresponse/taskResponseCtr/mobileScenpersons',
	                    context : this,
	                    dataType : 'json',
	                    data : {eventId : eventId},
	                    async: false,
	                    success : function(r) {
	                    	
	                    	var $planresponsetab3_content=this.planresponsetab3_content;
	    					
	    					//现场人员填充
	    					var mobileScenpersons=r.list;
	    					
	    					this.clean();
	    					//this.initPoint(mobileScenpersons);
	    					
	    					var disposal_progress_scenperson_table=$("#disposal_progress_scenperson_table_id",$planresponsetab3_content);
	    					disposal_progress_scenperson_table.empty(); 
	    					var mobileScenpersons_html=""
	    					for(var i=0;i<mobileScenpersons.length;i++){
	    						var json = {
	    							phone : mobileScenpersons[i].personphone,
	    							name : mobileScenpersons[i].personname
	    						}
	    						var jsonData = $.toJSON(json).replaceAll('"', '\'');
	    						
	    						var json2 = {
	    								phone : mobileScenpersons[i].personphone,
	    								name : mobileScenpersons[i].personname,
	    								id : mobileScenpersons[i].id,
	    								gisx : mobileScenpersons[i].gisx,
	    								gisy : mobileScenpersons[i].gisy
	    							}
	    							var jsonData2 = $.toJSON(json2).replaceAll('"', '\'');
	    						
	    						mobileScenpersons_html+='<tr id="'+mobileScenpersons[i].id+'" data="'+jsonData2+'" style="cursor:pointer;" class="person_tr"><td>'+mobileScenpersons[i].personname+'</td>'
	    						+'<td style="width: 40%;">'+mobileScenpersons[i].personphone+'</td>'
	    						+'<td style="width: 20%;"><a data="'+jsonData+'" href="javascript:void(0);" class="rSid_tab_call personPhoneCall"></a></td></tr>';
	    					}
	    					disposal_progress_scenperson_table.html(mobileScenpersons_html);
	    					// 打电话
	    					$('.personPhoneCall',$planresponsetab3_content).unbind().click($.proxy(this.callPhone,this))
	    					
	    					$('.person_tr',$planresponsetab3_content).unbind().click($.proxy(function(evt){
	    						$('.person_tr td',$planresponsetab3_content).css('color','')
	    						$('.person_tr',$planresponsetab3_content).removeClass('selectPerson_hd');
	    						
	    						var $this = $(evt.currentTarget);
	    						$this.addClass('selectPerson_hd').children('td').css('color','yellow');
	    						
	    						this.pointFocus(evt);
	    					},this))
	    					
	                    }
	                }); 
					
				},
				
				
				// 加载现场人员 描点
				fieldPersonPointer : function(){
					$.ajax({
	                    url : evecom.v.contextPath + '/jf/ecapp/plan/eventresponse/taskResponseCtr/mobileScenpersons',
	                    context : this,
	                    dataType : 'json',
	                    data : {eventId : eventId},
	                    async: false,
	                    success : function(r) {
	    					//现场人员填充
	    					var mobileScenpersons=r.list;
	    					this.clean();
	    					this.initPoint(mobileScenpersons);
	                    }
	                }); 
					
				},
				
				// 打电话
				callPhone : function(evt){
					var d = $.parseJSON($(evt.currentTarget).attr('data').replaceAll('\'', '"'));
					var params = {
						"callType" : "callone",
						"callee" : d.phone,
						"member" : d.name
					};
					$.ajax({
						url : evecom.v.contextPath + '/jf/ecapp/incident/communication/CommunicationCtr/call',
						context : this,
						type : "POST",
						dataType : 'json',
						data: params,
						success : function(json) {
							$.easyui.messager.show("呼叫成功!");
						},
						error : function(XMLHttpRequest, textStatus, errorThrown){
							$.easyui.messager.show("呼叫失败!");
						}
					});
						
				},
				
				/**
	             * 任务添加点击
	             */
	            taskAddDialog : function(){
	            	if(!this.current_disprojectid){//当没有选择处置项目时提示并返回
	            		$.messager.show({
	    					title:'提示',
	    					msg:'请点击选择工作组',
	    					timeout:2000,
	    					showType:'slide'
	    				});
	            		return ;
	            	}
	            	
	            	var dialog = top.eve.modalDialog({
						title : '添加任务',
						width : 580,
						height : 395,
						url : evecom.v.contextPath+ '/jf/planemergency/eventtask/planeventtaskctr/getaddJsp?projectid='+this.current_disprojectid,
						buttons : [
							{
								text : '确定',
								iconCls : 'icon-save',
								handler : $.proxy(function() { 
									dialog.find("iframe").get(0).contentWindow.submitForm(
        	                                dialog,$.proxy(function(planEventTask){ 
        	                                	this.projectDatas[planEventTask.projecttopid].planEventTasks.push(planEventTask);
        	                                	this.disposalProjectonclick(planEventTask.projecttopid);
        	                                },this));
								},this)
							},{
								text : '取消',
								iconCls : 'icon-cancel',
								handler : function() {
									dialog.dialog('destroy');
								}
							} ]
					});
	            	
	            	
	            },
				
/*****************************tab3******************************/				
				/**
				 * 图片预览点击
				 * @param th
				 */
				imageViewClick  : function(th){
					var a=$(th.target);
					var id=a.parent().attr('id');
					var urlArr=[rootPath+"/jf/ecapp/plan/eventresponse/taskResponseCtr/getfilebyid?fileid="+id	]
					fnPicShow(urlArr,0);
				},
	            /** 空串替换 **/
	            ifnull : function(str,defaultvalue,maxlength){
	            	if(maxlength){
	            		if(str){
	            			if(str.length>=maxlength){
	            				return str.substr(0,maxlength)+(defaultvalue?defaultvalue:"..");
	            			}else{
	            				return str;
	            			} 
	                	}else{
	                		return  defaultvalue?defaultvalue:"";
	                	}
	            	}else{
	            		if(str){
	                		return str;
	                	}else{
	                		return  defaultvalue?defaultvalue:"";
	                	}
	            	} 
	            }, 
	            
	            // 描点
	            initPoint : function(result){
					for(var i=0;i<result.length;i++){
						result[i].showname = result[i].personname;
					}
					if(result != null){
						var obj = {
							id :　'planresponse_field_communication',
							lonName : 'gisx',
	                        latName : 'gisy',
	                        url : evecom.v.contextPath+"/style/gis/images/police.gif",
	                        width : 32,
	                        height : 32,
	                        typeName : "type",
	                        data : result
						}
					}
					// 资源定位
					//obj.infoCallback = this.detailInfo;
                    locationPoint(obj);
                    for (var i=0; i<result.length; i++) {
						 var o = result[i];
						 var evt = {};
						 // 气泡窗口
						 if(o.gisx||o.gisy){
						     evt = {
						         mapPoint : base.locationObject.getPoint(o.gisx, o.gisy)
						     };
						     this.myOpenPopup(o, evt, (o.personname.length+1)*20, 33, 'mouseover');
					     }
					 }
                    // 打电话
					$('.callPhonePopup').unbind().click($.proxy(this.callPhone,this))
	            },
	            
	            /**
				 * 显示气泡窗口
				 * 
				 * @param o
				 *            事件
				 * @param evt
				 *            事务
				 * @param width
				 *            气泡宽度
				 * @param height
				 *            气泡高度
				 * @param className
				 *            气泡样式
				 * 
				 * @author Leon Lin
				 * @created 2015年5月30日 下午10:43:00
				 */
				myOpenPopup : function(o, evt , width, height, className) {
					dojo.require("esri.dijit.Popup");// 导入ArcGis对应的包
				    dojo.require("dojo.dom-construct");

				    var div = dojo.create("div");// 创建气泡popup对象，创建div
				    div.id = o.id;// 为div增加id
				    var popup = new esri.dijit.Popup({
				        marginLeft : 0,
				        marginTop : 0
				    }, div);// 创建popup对象
				    
				    popup.domNode.onclick = function(){
				    	
				    };
				    popup.domNode.onmouseover = function() {
				    	
				    };
				    popup.domNode.onmouseout = function() {
				    	
				    };
				    // 样式初始化事件 ,dojo绑定事件
				    dojo.connect(popup, "show", function(evt) {
				    	
				    });
				    dojo.addClass(popup.domNode, "esriBar");
				    popup.resize(width+100, height);// 气泡大小
				    popup.anchor='right';// 锚:控制气泡显示在角的右边
				    popup.setMap(base.map); // 气泡所在地图
				    var color='';
				    if(o.color=='1'){
				        color = 'blue';
				    }else if(o.color == '2'){
				        color = 'red';
				    }else if(o.color=='3'){
				        color = 'green';
				    }else if(o.color=='4'){
				        color = 'orange';
				    }
				    var json = {
						phone : o.personphone,
						name : o.personname
					}
					var jsonData = $.toJSON(json).replaceAll('"', '\'');
				    
				    popup.setContent('<div class="popuptab '+color+'"><em style="color:white;">'+o.showname
				    	+'<img class="callPhonePopup" style="vertical-align: middle;margin-left: 5px;" data="'+jsonData+'" src="'+eve.contextPath+'/evecom/ecapp/incident/images/sPhone.png"/>'
				    	+'</em></div>');// 气泡内容
				    //popup.setContent(''+subStrUtil(o.eventname,strSize)+'');// 气泡内容
				    popup.show(evt.mapPoint);// 坐标位置
				    this.fieldPopupArr.push(popup);// 在全局变量数组中添加，统一管理
				},
				
				//清除气泡
				clean : function(){
					//清除气泡描点对象
					if(this.fieldPopupArr.length>0){
				        for ( var i=0;i<this.fieldPopupArr.length;i++) {
				        	this.fieldPopupArr[i].destroy();
				        }
				    }
				    this.fieldPopupArr = [];
					//清除现场人员图层
				    if(base.map.getLayer('planresponse_field_communication') != null){
				       base.map.removeLayer(base.map.getLayer('planresponse_field_communication'));
				    }
				},
				
				/**
				 * 销毁
				 * 
				 * @author Mars zhang
				 * @created 2015年5月29日 下午15:02:00
				 */
				destroy : function() { 
					superclass.destroy.call(this); 
					// 清除现场人员 
					this.clean();
					//清除右侧面板
				    $("#rightPanel").empty();
				    
				    for(var i=0;i<this.ids.length;i++){
				    	$("#"+this.ids[i]).remove();
				    }
				}  
			};
		}); 