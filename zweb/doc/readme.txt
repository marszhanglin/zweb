

							《一》情况：
目标：
	1、搭载spring   00
	1.1、请求路径框架搭建  springMVC   00
	2、搭载hibernate结合spring  连接mysql及oracle数据库     00
	3、hibernate自动建表       00
	4、引入androidpn的mina框架       00
	4.1、编解码WebSocket数据        /zweb/src/z/pub/test/mina/factory/MyWebSocketFactory.java
	5、与移动端简单交互     
	6、将项目部署在linux上(后续实时更新)    
	7、引入jqueryMobile 完成用户管理
	8、引入AMQ
	9、单点登录及统一认证     ldap(apacheDS)  cas  http://blog.csdn.net/zhurhyme/article/category/2362091/3
执行情况：
	项目框架初步搭建：
	1、初步搭载spring框架  			  	web.xml	/zweb/WebRoot/WEB-INF/spring/test-config.xml
	2、初步搭载定时器z.pub.quartz        z.pub.test.SpringTest.springWhenBeanRun()
	3、初步搭载日志系统log4j              /zweb/src/log4j.properties
	4、完成springmvc请求搭建及model自动转xml/json    Open Declaration z.pub.test.SpringControllerTest
	5、提交github
	6、初步引入mina     				监听客户端socket连接      对协议及数据的编解码还未了解
	7、移植androidpn的org下xmpp相关的工具类到该工程   
	8、建jdbc.properties及ds-config.xml引入  配置了一个mysql的dataSource
	9、建hibernate.cfg.xml及db-config.xml  配置了dao service
	10、成功引入amq  详见test下的amq
	11、引入jutil4   就一个jutil4包   
	12、完成JNDI连接ApacheDS    z.pub.test.jndi.JNDITest
	13、单点登录cas  
疑惑：
	1、一个<Filter>对应三个<filter-mapping>？       			web.xml SessionFilter
	
	
	
	
	
	
	
	
jar包管理：
     1、mina : mina-core-2.0.0-RC1.jar   slf4j-api-1.5.8.jar  slf4j-log4j12-1.5.8.jar
     		 mina-integration-beans-2.0.0-RC1.jar
     
     
     2、xmpp : xpp3-1.1.4c.jar   
	
	
	 3、Document ：  dom4j-1.6.1.jar
	 
	 4、hibernate ：缓存：ehcache-1.6.1.jar  
	 					 hibernate-annotations-3.4.0.GA.jar 
	 					 hibernate-commons-annotations-3.1.0.GA.jar
	 					 hibernate-core-3.3.1.GA.jar
	 					 hsqldb-1.8.0.10.jar
	 					 ejb3-persistence-1.0.2.GA.jar
						 antlr-2.7.6.jar   干嘛用？  没有此包，hibernate不会执行hql语句
	
	5、mysql ： mysql-connector-java-5.1.6.jar 
	
	6、jstl :  jstl.jar   standard.jar
	
	xmpp：------------
	7、配置Configuration .property  commons-configuration-1.6.jar   ///zweb/src/z/pn/util/ConfigManager.java
	
	8、xmpp   tinder-1.2.1.jar
	          libidn-1.15.jar
	          commons-digester-2.0.jar
	          config.properties
			  config.xml
	xmpp：------------
	
	9、amq  activemq-all-5.10.0.jar
			activemq-broker-5.10.0.jar
			activemq-client-5.10.0.jar
			activemq-console-5.10.0.jar
			activemq-jaas-5.10.0.jar
			activemq-kahadb-store-5.10.0.jar
			activemq-openwire-legacy-5.10.0.jar
			activemq-protobuf-1.1.jar
			activemq-spring-5.10.0.jar
			activemq-web-5.10.0.jar
			
		还需要jetty的支持
			jetty-all-server-7.6.9.v20130131.jar
			jetty-client-8.1.16.v20140903.jar
			jetty-http-8.1.16.v20140903.jar
			jetty-io-8.1.16.v20140903.jar
			jetty-jmx-8.1.16.v20140903.jar
			jetty-server-8.1.16.v20140903.jar
			jetty-servlet-8.1.16.v20140903.jar
			jetty-util-8.1.16.v20140903.jar
			jetty-websocket-7.6.9.v20130131.jar
	