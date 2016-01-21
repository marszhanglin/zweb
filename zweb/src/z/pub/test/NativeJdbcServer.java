package z.pub.test;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository@Scope("prototype")//prototype  singleton  session  request  
public class NativeJdbcServer {
	public NativeJdbcServer(){
		System.out.println("NativeJdbcServer.NativeJdbcServer");
	}
	public void doSomething(){
		System.out.println("NativeJdbcServer.doSomething");
	}
}
