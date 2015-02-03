package ua.com.goit.gojava.kickstarter;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class KickstarterTest {

	@Test // теперь самое интересное. Как протестировать кикстартер без ее зависимости от ConsoleIO.
	// представим, что у нас вообще нет класcа ConsoleIO, толоько интерфейс...\
	// я даже удалил :) чтобы небыло соблазна заюзать ConsoleIO. Что делать? Ну работаем как обычно
	public void stub_and_dummy() {
	    // проинитим их чем можем
		Categories categories = new Categories();
		Projects projects = new Projects();
		IO io = new IO() { // это так называемый анонимный класс. КОгда мы прямо в теле метода реализовываем интерфейс и его методы
			
			@Override
			public int read() {
				return 0;
			}
			
			@Override
			public void print(String message) {
				// do nothing
			}
		};
		Kickstarter kickstarter = new Kickstarter(categories, projects, io, new StubQuoteGenerator()); // создадим локальные переменные
		// то, что мы сейчас сделали с кикстартером - мы забили в него DummyObjects - что нибудь лишь бы заработал сам кикстартер
		// если бы мы могли то забили бы туда null - и это тоже можно было бы считать DummyObject.
		// DummyObjects используется тогда, когда нам пофиг, что передать - лишь бы пройти внутреннюю валидацию в методе (конструктроре)
		
		kickstarter.run();
		// тест завис, что не удивительно, поскольку у нас программа с бесконечным циклом. 
		// а сделаем ка в списке категорий возможность выйти из программы, чтобы было как выходить из кода
		// готово, теперь тест должен будет завершиться потому что наш IO всегда возвращает 0, ведет себя как Stub - реализация с 
		// захардкодженными значениями внутри
	}

	class FakeIO implements IO {

		private List<String> messages = new LinkedList<String>(); // теперь это осталось показать клиентам после работы с фейком
		private List<Integer> input = new LinkedList<Integer>(); // а это надо проинитить перед стартом работы - через конструктор

		public FakeIO(Integer... input) {
			this.input = new LinkedList<Integer>(Arrays.asList(input)); // мы жеж помним, что Arrays.asList( возвращает 
			// немодифицируемый список и потом мы с него не сожем удалить ничего? Потому мы перекладываем в реальный LinkedList
		}
		
		@Override
		public int read() {
			return input.remove(0); // теперь надо читать сообщения, а откуда?
			// тут за каждым вызовом кикстартера метода рид, значение будет браться первое из input (удаляясь)
		}

		@Override
		public void print(String message) {
			messages.add(message); // Все сообщения сохраняются
		}

		public List<String> getMessages() {
			return messages;
		}
		
	}
	
	class StubQuoteGenerator extends QuoteGenerator {

		public StubQuoteGenerator() {
			super(new Random());
		}
		
		@Override
		public String nextQuote() {
			return "quote"; // вот теперь это стаб, а то сделал фейк полноценный в прошлый раз
		}
		
	}
	
	@Test 
	public void fake() {
	    // given
		Categories categories = new Categories();
		categories.add(new Category("category1"));
		categories.add(new Category("category2"));
		Projects projects = new Projects();
		FakeIO io = new FakeIO(1, 0, 0); // проинитим fake - выбрали категорию 1, вышли изсписка категорий, вышли из программы 
		Kickstarter kickstarter = new Kickstarter(categories, projects, io, new StubQuoteGenerator()); // создадим локальные переменные
		
		// when
		kickstarter.run();

		// then
		// и теперь самое интересное. посмотрим, что фейк насохранял
		// штука первая - у нас нет категорий - готово!
		// штука вторая - У нас рендомный генератор цитат мешает - выделим QoteGenerator за пределы класса кикстартер
		assertEquals(
			"[quote\n" + 
			", Выберите категорию (или 0 для выхода):\n" +
			", [1 - category1, 2 - category2]\n" +
			", Вы выбрали категорию: category1\n" +
			", --------------------------------------\n" +
			", Проектов в категории нет. Нажмите 0 - для выхода.\n" +
			", Выберите категорию (или 0 для выхода):\n" +
			", [1 - category1, 2 - category2]\n" +
			", Спасибо за использование нашей программы!\n" +
			"]", io.getMessages().toString());
	}
}
