package TestCase;

import java.util.List;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.sun.net.httpserver.Filter;
import com.zn.mapper.ItemsMapper;
import com.zn.mapper.SysUserMapper;
import com.zn.pojo.Items;
import com.zn.pojo.SysUser;
import com.zn.pojo.SysUserExample;
import com.zn.pojo.SysUserExample.Criteria;

public class TestCase {
	@SuppressWarnings("resource")
	@Test
	public void test1() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("conf/spring-mybatis.xml");
		System.out.println(ac);
		SysUserMapper bean = ac.getBean("sysUserMapper", SysUserMapper.class);
		//按条件查询
		SysUserExample sue = new SysUserExample();
		//创建条件
		Criteria criteria = sue.createCriteria();
		//设置过滤条件
		criteria.andUsercodeEqualTo("zhangsan");
		List<SysUser> list = bean.selectByExample(sue);
		for (SysUser sysUser : list) {
			System.out.println(sysUser.getPassword());
		}
		
		
	}
	@Test
	public void test2() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("conf/spring-mybatis.xml");
		System.out.println(ac);
		ItemsMapper bean = ac.getBean("itemsMapper", ItemsMapper.class);
		List<Items> list = bean.selectByExample(null);
		for (Items items : list) {
			System.out.println(items.getName());
		}
		Items items = bean.selectByPrimaryKey(1);
		System.out.println(items.getName());
		//DelegatingFilterProxy
		//ShiroFilterFactoryBean
		//DefaultWebSecurityManager
		//ContextLoaderListener
		//AuthorizationAttributeSourceAdvisor
	}
}
