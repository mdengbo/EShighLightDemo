package code;

import code.entity.User;
import code.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testMapper() {
		User user = new User();
		user.setPhone("123");
		user.setPassword("123111");
		user.setUserName("chenrui");
		userMapper.insert(user);
	}


}
