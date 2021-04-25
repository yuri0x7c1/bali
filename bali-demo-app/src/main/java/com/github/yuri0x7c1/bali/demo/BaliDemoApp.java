package com.github.yuri0x7c1.bali.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.vaadin.artur.helpers.LaunchUtil;

import com.github.yuri0x7c1.bali.demo.domain.Bar;
import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.BarService;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootApplication(scanBasePackages={"com.github.yuri0x7c1.bali"})
@Theme(value = "balidemoapp")
public class BaliDemoApp extends SpringBootServletInitializer implements CommandLineRunner, AppShellConfigurator {

    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(BaliDemoApp.class, args));
    }

	@Autowired
	FooService fooService;

	@Autowired
	BarService barService;

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
		// createDummyData();
	}
}
