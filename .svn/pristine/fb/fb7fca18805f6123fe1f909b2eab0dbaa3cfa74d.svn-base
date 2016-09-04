package cn.nj.www.my_module.tools.uuid;

/**
 * ID生成器工具类
 * @author Jinfm
 *
 */
public class IdUtil {

	private static IdGenerator idGenerator = new SnowflakeIdGenerator(1);
	
	public static synchronized long getId() {
		return idGenerator.nextId();
	}

//	public static void main(String[] args) {
//		System.out.println(getId());
//	}
	
}
