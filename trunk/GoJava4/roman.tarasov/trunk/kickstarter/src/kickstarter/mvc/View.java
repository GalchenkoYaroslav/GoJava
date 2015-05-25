package kickstarter.mvc;

import java.util.ArrayList;
import java.util.List;

import kickstarter.mvc.interfaces.iModel;
import kickstarter.mvc.interfaces.iView;

import kickstarter.pages.viewContent.PageView;
import kickstarter.ui.iUserInterface;

public class View implements iView {
	private iModel imodel;
	private PageView page;
	private iUserInterface ui;
	private List<PageView> pagesView;
	private int currentPage;

	public View(iModel imodel, iUserInterface ui) {
		this.imodel = imodel;
		this.ui = ui;
		pagesView = new ArrayList<PageView>();
	}

	@Override
	public void addPageView(PageView page) {
		pagesView.add(page);
	}

	@Override
	public void showPage() {
		
		currentPage = imodel.getCurrentPage();
		page = pagesView.get(currentPage);
		ui.display(page.getHeader());
	}

	@Override
	public void setUserInterface(iUserInterface ui) {
		this.ui=ui;
		
	}
}