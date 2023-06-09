package twitter.state.profile;

import twitter.Context;
import twitter.logic.HandleAccount;
import twitter.logic.HandleTweet;
import twitter.utils.ConsoleColors;
import twitter.state.RequestSentListState;
import twitter.state.State;
import twitter.state.TweetListState;
import twitter.utils.Logger;

public class ViewPersonalInfoState extends State {


    @Override
    public void printCliMenu(Context context) {

        HandleAccount handleAccount = context.getHandleAccount();
        HandleTweet handleTweet = context.getHandleTweet();

        System.out.println(ConsoleColors.BLUE + "Profile information:");
        System.out.println(ConsoleColors.BLUE + "Username: @" + handleAccount.getUser().getUserName());
        System.out.println(ConsoleColors.BLUE + "Name: " + handleAccount.getUser().getName());
        System.out.println(ConsoleColors.BLUE + "Biography: " + handleAccount.getUser().getBiography());
        System.out.println(ConsoleColors.BLUE + "Date of birth: " + handleAccount.getUser().getDateOfBirth());
        System.out.println(ConsoleColors.BLUE + "Email address: " + handleAccount.getUser().getEmailAddress());
        System.out.println(ConsoleColors.BLUE + "Phone number: " + handleAccount.getUser().getPhoneNumber());
        System.out.println(ConsoleColors.BLUE + "Followers: " + handleAccount.getUser().getNumberOfFollowers());
        System.out.println(ConsoleColors.BLUE + "Followings: " + handleAccount.getUser().getNumberOfFollowings());
        System.out.println(ConsoleColors.BLUE + "Tweets: " + handleAccount.getUser().getNumberOfTweets());

        System.out.println(ConsoleColors.YELLOW + "What do you want to do next?");
        System.out.println(ConsoleColors.YELLOW + "1.View saved tweets");
        System.out.println(ConsoleColors.YELLOW + "2.View followers' list");
        System.out.println(ConsoleColors.YELLOW + "3.View followings' list");
        System.out.println(ConsoleColors.YELLOW + "4.View tweets list");
        System.out.println(ConsoleColors.YELLOW + "5.View follow requests");
        System.out.println(ConsoleColors.YELLOW + "6.View list of requests you sent");
        System.out.println(ConsoleColors.YELLOW + "7.Go to setting");
        System.out.println(ConsoleColors.YELLOW + "8.Back");
    }

    @Override
    public State doAction(Context context) {

        printCliMenu(context);

        HandleAccount handleAccount = context.getHandleAccount();
        HandleTweet handleTweet = context.getHandleTweet();
        Logger log = context.getLogger();

        log.info("User checked their profile info.");

        String choice = context.getScanner().nextLine();

        switch (choice) {
            case "1":

                log.info("User wants to view their saved tweets.");
                for (long idTweet : handleAccount.getUser().getSavedTweet()) {
                    String tweet = handleTweet.getTweet(idTweet).getTextOfTweet();
                    System.out.println(ConsoleColors.BLUE + tweet);
                }
                return this;

            case "2":

                log.info("User wants to view their followers' list.");
                return new FollowerListState(handleAccount.getUser().getUserName());

            case "3":
                log.info("User wants to view their followings' list.");
                return new FollowingListState(handleAccount.getUser().getUserName());

            case "4":
                log.info("User wants to view their tweets.");
                if (handleAccount.getUser().getNumberOfTweets() != 0) {
                    return new TweetListState(handleAccount.getUser().getUserName());
                } else {
                    log.info("There is no tweet to show.");
                    System.out.println(ConsoleColors.RED + "There is no tweet to show!");
                    return this;
                }
            case "5":
                log.info("User wants to view follow request's list.");
                if (handleAccount.getUser().getNumberOfFollowRequest() != 0) {
                    return new FollowRequestsListState();
                } else {
                    log.info("There is no list to show.");
                    System.out.println(ConsoleColors.RED + "There is no list to show!");
                    return this;
                }
            case "6":
                log.info("User wants to view the list of follow request that they sent.");
                if (handleAccount.getUser().getNumberOfAccountsSentRequest() != 0) {
                    return new RequestSentListState();
                } else {
                    log.info("There is no list to show.");
                    System.out.println(ConsoleColors.RED + "There is no list to show!");
                    return this;
                }
            case "7":

                log.info("User wants to go to the settings.");
                return new SettingState();

            case "8":

                log.info("User chose to go back.");
                return null;

            default:

                log.info("User entered invalid number.");
                printFinalCliError();
                return this;
        }
    }

    @Override
    public void printFinalCliError() {
        System.out.println(ConsoleColors.RED + "Enter valid number.");
    }

    @Override
    public void close(Context context) {

    }
}
