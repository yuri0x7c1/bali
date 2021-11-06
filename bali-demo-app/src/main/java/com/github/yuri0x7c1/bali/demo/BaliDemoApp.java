package com.github.yuri0x7c1.bali.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.yuri0x7c1.bali.demo.domain.Bar;
import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.BarService;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.security.domain.SecurityGroup;
import com.github.yuri0x7c1.bali.security.domain.SecurityRole;
import com.github.yuri0x7c1.bali.security.domain.SecurityUser;
import com.github.yuri0x7c1.bali.security.role.SecurityRoles;
import com.github.yuri0x7c1.bali.security.service.SecurityGroupService;
import com.github.yuri0x7c1.bali.security.service.SecurityRoleService;
import com.github.yuri0x7c1.bali.security.service.SecurityUserService;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAutoConfiguration
@EntityScan("com.github.yuri0x7c1.bali")
@EnableJpaRepositories("com.github.yuri0x7c1.bali")
@SpringBootApplication(scanBasePackages="com.github.yuri0x7c1.bali")
public class BaliDemoApp implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BaliDemoApp.class, args);
	}

	@Autowired
	FooService fooService;

	@Autowired
	BarService barService;

	@Autowired
	SecurityRoleService securityRoleService;

	@Autowired
	SecurityGroupService securityGroupService;

	@Autowired
	SecurityUserService securityUserService;

	public static final String BCRYPT_PASSWORD = "$2a$10$oO3wWvopMOAUvGiO3uoP7uzS6/u/se0BHgsrxhsWc0gtA9AEiCoIu";

	public static final String ADMIN_USER_NAME = "admin";

	public static final String ADMIN_GROUP_NAME = "Administrators";

	public void createAdmin() {
		for (SecurityRoles securityRole : SecurityRoles.values()) {
			log.debug("Security role name: {}", securityRole);
			SecurityRole role = securityRoleService.findByName(securityRole.name());
			if (role == null) {
				role = new SecurityRole();
				role.setName(securityRole.name());
				securityRoleService.save(role);
			}
		}

		SecurityGroup adminGroup = securityGroupService.findByName(ADMIN_GROUP_NAME);
		if (adminGroup == null) {
			adminGroup = new SecurityGroup();
			adminGroup.setName(ADMIN_GROUP_NAME);
		}
		adminGroup.setRoles(securityRoleService.findAll());
		securityGroupService.save(adminGroup);

		SecurityUser adminUser = securityUserService.findByName(ADMIN_USER_NAME);
		if (securityUserService.findByName(ADMIN_USER_NAME) == null) {
			adminUser = new SecurityUser();
			adminUser.setName(ADMIN_USER_NAME);
			adminUser.setPasswordHash(BCRYPT_PASSWORD);
		}
		adminUser.getGroups().add(adminGroup);
		securityUserService.save(adminUser);
	}

	public void createDummyData() {
		if (fooService.count() == 0L) {
			Fairy fairy = Fairy.create();

			for (int i = 0; i < 1000; i++) {
				Person fooPerson = fairy.person();

				Foo foo = new Foo();
				foo.setStringValue(fooPerson.getFullName());
				fooService.save(foo);


				Person barPerson = fairy.person();

				Bar bar = new Bar();
				bar.setValue(barPerson.getFullName());
				barService.save(bar);
			}
		}
	}

	@Override
	public void run(String... args) throws Exception {
		createDummyData();
		createAdmin();
	}
}
