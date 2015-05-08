package kickstarter;

import kickstarter.data_types.Category;
import kickstarter.data_types.Data;
import kickstarter.data_types.Project;
import kickstarter.data_types.Quote;
import kickstarter.interfaces.UserInterface;
import kickstarter.storages.CategoriesStorage;
import kickstarter.storages.ProjectsStorage;
import kickstarter.storages.QuotesStorage;

public class Kickstarter {

	private UserInterface userInterface;
	private QuotesStorage quotes;
	private CategoriesStorage categories;
	private ProjectsStorage projects;

	public Kickstarter(UserInterface userInterface, QuotesStorage quotes, CategoriesStorage categories, ProjectsStorage projects) {
		this.userInterface = userInterface;
		this.quotes = quotes;
		this.categories = categories;
		this.projects = projects;
	}

	public void run() {
		showQuote();
		choiceCategory();
		userInterface.showTheEndPage();
	}

	private void showQuote() {
		if (quotes.empty()) {
			return;
		}
		Quote randomQuote = quotes.getRandom();
		userInterface.showQuotePage(randomQuote);
	}

	private void choiceCategory() {
		while (true) {
			Data item = userInterface.choiceCategory(categories);
			if (item == Data.Default.EXIT) {
				return;
			}
			Category category = (Category) item;
			choiceProject(category);
		}
	}

	private void choiceProject(Category category) {
		while (true) {
			Data item = userInterface.choiceProject(projects.getProjectsInCategory(category));
			if (item == Data.Default.EXIT) {
				return;
			}
			Project project = (Project) item;
			userInterface.showProject(project);
		}
	}

}