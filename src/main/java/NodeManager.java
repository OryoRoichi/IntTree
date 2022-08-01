import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class NodeManager {

    private Node root;

    private Comparator<Node> comparator;

    public NodeManager(Comparator<Node> comparator) {
        this.comparator = comparator;
    }



    public List<Integer> getGreaterThen(Integer value) {
        List<Integer> list = new ArrayList<Integer>();
        if (this.root != null) {
            getGreater(this.root, value, list);
        }
        return list;
    }

    private void getGreater(Node root, Integer value, List<Integer> list) {
        Direction direction = getDirection(value, root);
        switch (direction) {
            case LEFT: {
                if (root.getValue() > value){
                    list.add(root.getValue());
                }
                if (root.getLeft() != null) {
                    getGreater(root.getLeft(), value,list);
                }
                break;
            }
            case RIGHT: {
                if (root.getValue() > value){
                    list.add(root.getValue());
                }
                if (root.getRight() != null) {
                    getGreater(root.getRight(), value, list);
                }
                break;
            }
        }
    }


    public Integer getMax() {
        if (this.root == null) {
            return this.root.getValue();
        }
        return findMax(this.root).getValue();
    }

    private Node findMax(Node root) {
        if (root.getRight() != null) {
            return findMax(root.getRight());
        } else {
            return root;
        }
    }

    public Integer getMin() {
        if (this.root == null) {
            return this.root.getValue();
        }
        return findMin(this.root).getValue();
    }

    private Node findMin(Node root) {
        if (root.getLeft() != null) {
            return findMin(root.getLeft());
        } else {
            return root;
        }
    }


    public Node get(Integer value) {
        if (this.root == null) {
            throw new NoSuchElementException();
        }
        return find(this.root, value);
    }

    private Node find(Node root, Integer value) {
        Direction direction = getDirection(value, root);
        switch (direction) {
            case LEFT: {
                if (root.getLeft() != null) {
                    return find(root.getLeft(), value);
                } else {
                    throw new NoSuchElementException();
                }
            }
            case RIGHT: {
                if (root.getRight() != null) {
                    return find(root.getRight(), value);
                } else {
                    throw new NoSuchElementException();
                }
            }
            default:
                return root;
        }
    }
    public Node add(Integer value) {
        // Проверяем корень дерева на null
        if (this.root == null) {
            // Если корень null, то создаем его, инициализируя переданным занчением
            this.root = new Node(value);
            // Возвращаем созданную ноду
            return this.root;
        }
        // Есали корень не null, то ищем для него место в дереве
        return getNext(this.root, value);
    }
    /**
     * @param root  - Текущая проверяемая нода, т.е. нода для которой
     *              пытаемся добавить элемент в качестве наследника
     * @param value - значение добавляемого элемента
     * @return - вновь созданную ноду
     */
    private Node getNext(Node root, Integer value) {
        // Определяем положение новой ноды в дереве (Слева или справа)
        Direction direction = getDirection(value, root);
        switch (direction) {
            // Если новое положение слева
            case LEFT: {
                // Проверяем у текущей ноды в дереве, свободно ли
                // у нее место слева
                if (root.getLeft() != null) {
                    // Если место не свободно, то рекурсивно вызываем
                    // этот же метод, нов качестве корня передаем следующую ноду
                    // расположенную СЛЕВА
                    return getNext(root.getLeft(), value);
                } else {
                    // Если место свободно, то создаем на этом месте новую ноду
                    Node node = new Node(value, root, Direction.LEFT);
                    // Родительской ноде назначаем наследника слева
                    root.setLeft(node);
                    // Возвращаяю вновь созданную ноду
                    return node;
                }
            }
            case RIGHT: {
                if (root.getRight() != null) {
                    return getNext(root.getRight(), value);
                } else {
                    Node node = new Node(value, root, Direction.RIGHT);
                    root.setRight(node);
                    return node;
                }
            }
            default:
                return root;
        }
    }

    private Direction getDirection(Integer value, Node comparingNode) {
        int compareResult = comparator.compare(comparingNode, new Node(value));
        return compareResult > 0 ? Direction.RIGHT :
                compareResult < 0 ? Direction.LEFT :
                        Direction.EQUAL;
    }
}
