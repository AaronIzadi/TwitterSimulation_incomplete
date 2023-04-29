package twitter.state.profile;

import twitter.Context;
import twitter.logic.HandleAccount;
import twitter.utils.ConsoleColors;
import twitter.state.State;
import twitter.utils.Logger;

public class FollowingListState extends State {

    private final String username;

    public FollowingListState(String username) {
        this.username = username;
    }

    @Override
    public void printCliMenu(Context context) {
        System.out.println(ConsoleColors.YELLOW + "Followings:");
    }

    @Override
    public State doAction(Context context) {

        printCliMenu(context);

        HandleAccount handleAccount = context.getHandleAccount();
        Logger log = context.getLogger();

        if (handleAccount.getUser().getNumberOfFollowings() == 0) {
            log.info("There is no list to show.");
            System.out.println(ConsoleColors.RED + "There is no list to show!");
            return null;
        }

        for (String following: handleAccount.viewAccountList(handleAccount.getFollowingsList(username))) {
            System.out.println(ConsoleColors.BLUE + following);
        }

        System.out.println(ConsoleColors.YELLOW + "Do you want to check a profile?");

        String choice = context.getScanner().nextLine();

        switch (choice) {
            case "y":
            case "Y":
                log.info("User wants to check a profile.");
                return new ViewProfileState();
            case "n":
            case "N":
                log.info("User wants to go back.");
                return null;
            default:
                printFinalCliError();
                return this;
        }
    }

    @Override
    public void printFinalCliError() {
        System.out.println(ConsoleColors.RED + "Please only enter y or n to continue.");
    }

    @Override
    public void close(Context context) {

    }
}
