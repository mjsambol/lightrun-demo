public class SuperFastDemoRunner {

    public static void main(String[] args) {
        System.out.println("This is a java application which collects statistics about random wikipedia articles.");

        SuperFastDemo sfd = new SuperFastDemo();
        sfd.gatherArticleStats(10000);
    }

}
