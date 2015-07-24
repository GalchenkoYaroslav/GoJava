package ua.goit.kyrychok.kickstarter.controller;

import ua.goit.kyrychok.kickstarter.StandByMode;
import ua.goit.kyrychok.kickstarter.model.Reward;
import ua.goit.kyrychok.kickstarter.view.DonatePageView;

import java.util.List;

import static java.lang.Integer.parseInt;

public class DonatePageController extends AbstractController {
    private List<Reward> model;
    private DonatePageView view;
    private PaymentController paymentController;
    private PaymentRewardController paymentRewardController;
    private int projectId;

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setView(DonatePageView view) {
        this.view = view;
    }

    public void setPaymentRewardController(PaymentRewardController paymentRewardController) {
        this.paymentRewardController = paymentRewardController;
    }

    public void setPaymentController(PaymentController paymentController) {
        this.paymentController = paymentController;
    }

    private int getRewardId(int index) {
        return model.get(index).getId();
    }

    private AbstractPaymentController returnNextController(String input) {
        AbstractPaymentController controller;
        int index = parseInt(input);
        if (index == 1) {
            controller = paymentController;
        } else if (index > 1 && index <= model.size() + 1) {
            paymentRewardController.setRewardId(getRewardId(index - 2));
            controller = paymentRewardController;
        } else {
            throw new IndexOutOfBoundsException("Unexpected input value");
        }
        controller.setCurrentMode(StandByMode.USER);
        controller.setProjectId(projectId);
        return controller;
    }

    @Override
    protected boolean isValid(String input) {
        try {
            int inputValue = parseInt(input);
            return !(inputValue < 1 || inputValue > model.size() + 1);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void updateModel() {
        model = dataProvider.getRewards(projectId);
    }

    @Override
    protected void renderModel() {
        view.render(model);
    }

    @Override
    protected void doValidControl(String input) {
        setNextController(returnNextController(input));
    }
}
