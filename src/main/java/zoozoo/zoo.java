package zoozoo;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;  
import org.apache.zookeeper.Watcher;  
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class zoo {
	private static String connectString="127.0.0.1:2181";  
	private static int sessionTimeout=999999;
	private static final int SESSION_TIMEOUT=30000;
	
	ZooKeeper zk;
	
	Watcher wc = new Watcher(){
		public void process(org.apache.zookeeper.WatchedEvent event){
			System.out.println(event.toString());
		}
	};
	
	private void createZKInstance() throws IOException{
		zk = new ZooKeeper(connectString, SESSION_TIMEOUT, wc);
	}
	
	private void ZKOperations() throws IOException,InterruptedException,KeeperException{
		System.out.println("/n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
		
		zk.create("/zoo2","myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		System.out.println("/n2. 查看是否创建成功： ");
		System.out.println(new String(zk.getData("/zoo2",false,null)));
		
		System.out.println("/n3. 修改节点数据 ");
		zk.setData("/zoo2", "myTestData".getBytes(), -1);
		
		System.out.println("/n2. 查看是否修改成功： ");
		System.out.println(new String(zk.getData("/zoo2",false,null)));
		
//		System.out.println("/n5. 删除节点 ");
//		zk.delete("/zoo2", -1);
		
		System.out.println("/n2. 查看节点是否被删除： ");
		System.out.println("节点状态：["+ zk.exists("/zoo2",false)+"]");
	}
	
	private void ZKClose() throws  InterruptedException{
		zk.close();
	}
	
	public static void main(String[] args) throws Exception{
		zoo z = new zoo();
		z.createZKInstance();
		z.ZKOperations();
		z.clone();
	}
}
