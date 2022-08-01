import java.util.Comparator;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        NodeManager manager = new NodeManager(new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Node node = manager.add(random.nextInt(20));
            System.out.println(node);
        }
        System.out.println("-------------");
        System.out.println(manager.getMax());
        System.out.println("-------------");
        System.out.println(manager.getMin());
        System.out.println("-------------");
        System.out.println(manager.get(5));
        System.out.println("-------------");
        System.out.println(manager.getGreaterThen(2));
    }
}
