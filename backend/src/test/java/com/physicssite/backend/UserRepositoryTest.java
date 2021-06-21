package com.physicssite.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.physicssite.backend.user.User;
import com.physicssite.backend.user.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
@Transactional
public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("email_address");
		user.setPasswordHash("pass");
		user.setUsername("test");
		
		User saved = userRepository.save(user);
		User existing = entityManager.find(User.class, saved.getId());
		
		assertEquals(user.getUsername(), existing.getUsername());
	}
	
}
