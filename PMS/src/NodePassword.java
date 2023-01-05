public class NodePassword {
    public String serviceName;
    public String userName;
    public String password;
    public NodePassword next;
    public NodePassword previous;

    public void displayNode() {
        System.out.printf("| %-8s | %-8s | %-8s |\n", serviceName, userName, password);
    }

}
