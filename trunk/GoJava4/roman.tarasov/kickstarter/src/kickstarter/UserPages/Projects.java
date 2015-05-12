package kickstarter.UserPages;

import kickstarter.UserInterface;
import kickstarter.Entities.Project;
import kickstarter.Repository.ProjectList;

public class Projects {
	ProjectList projects;
	UserInterface ui;

	public Projects(ProjectList projects, UserInterface ui) {
		this.projects = projects;
		this.ui = ui;
	}

	Project[] printProjects() {
		return projects.printList(ui);
	}

	public Project selectProject() {
		ui.display("________________________");
		ui.display("|     Projects         |");
		ui.display("|______________________|");
		Project[] options = printProjects();
		ui.display("------------------------");
		ui.display("Select Project:");
		while (true) {
			ui.display(" e- exit to Categories ");
			String stringFromUI = ui.inputString();
			if (stringFromUI.equals("e")) {
				ui.display("exit");
				return null;
			}
			try {
				int parsed = Integer.parseInt(stringFromUI);
				for (int index = 0; index < options.length; index++) {
					if (parsed == options[index].id) {
						Project projectToDetailedView = options[index];
						return projectToDetailedView;
					}
				}
				throw new IndexOutOfBoundsException();
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				ui.display("input correct command, please");
			}
		}
	}
}