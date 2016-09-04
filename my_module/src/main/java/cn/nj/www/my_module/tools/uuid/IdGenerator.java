package cn.nj.www.my_module.tools.uuid;

/**
 * id生成器接口
 * @author Jinfm
 *
 */
interface IdGenerator {

	/**
	 * 获取ID
	 * @return id值
	 */
	public long nextId();
}
