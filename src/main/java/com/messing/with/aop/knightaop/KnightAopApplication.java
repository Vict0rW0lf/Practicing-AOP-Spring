package com.messing.with.aop.knightaop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAspectJAutoProxy
public class KnightAopApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnightAopApplication.class, args);
		
		ApplicationContext context = new AnnotationConfigApplicationContext(KnightAopApplication.class);
		
		BraveKnight knight = context.getBean(BraveKnight.class);
		
		knight.embarkOnQuest();
		
		((AnnotationConfigApplicationContext)context).close();
	}
	
	@Component
	public class BraveKnight {
		
		@Autowired
		@Qualifier("epicQuest")
		private Quest quest;
		
		public void embarkOnQuest() {
			quest.embark();
		}
	}
	
	public interface Quest {
		public void embark();
	}
	
	@Component
	@Qualifier("epicQuest")
	public class EpicQuest implements Quest {

		@Override
		public void embark() {}
		
	}
	
	@Component
	@Qualifier("rescueDamselQuest")
	public class RescueDamselQuest implements Quest {

		@Override
		public void embark() {}
		
	}

	@Aspect
	@Component
	public class Minstrel {
		
		@Before("execution(public void embarkOnQuest())")
		public void singBeforeQuest() {
			System.out.println("Fa la la; The knight is so brave!");
		}
		
		@After("execution(public void embarkOnQuest())")
		public void singAfterQuest() {
			System.out.println("Tee hee he; The brave knight did embark on a quest!");
		}
	}

}
