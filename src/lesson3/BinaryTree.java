package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        if (root == null) {
            return false;
        }
        root = delete(root, t);
        size--;
        return true;
    }
    //Трудоемкость и ресурсоемкость O(h), где h - высота дерева

    private Node<T> delete(Node<T> treeRoot, T value) {
        if (treeRoot == null) {
            return null;
        }
        if (value.compareTo(treeRoot.value) < 0) {
            treeRoot.left = delete(treeRoot.left, value);
        } else {
            if (value.compareTo(treeRoot.value) > 0) {
                treeRoot.right = delete(treeRoot.right, value);
            } else {
                if (treeRoot.left != null && treeRoot.right != null) {
                    Node<T> temp = new Node<>(minimum(treeRoot.right).value);
                    temp.left = treeRoot.left;
                    temp.right = treeRoot.right;
                    treeRoot = temp;
                    treeRoot.right = delete(treeRoot.right, treeRoot.value);
                } else {
                    if (treeRoot.left != null) {
                        treeRoot = treeRoot.left;
                    } else {
                        treeRoot = treeRoot.right;
                    }
                }
            }
        }
        return treeRoot;
    }
    //Трудоемкость и ресурсоемкость O(1)

    private Node<T> minimum(Node<T> treeRoot) {
        if (treeRoot.left == null) {
            return treeRoot;
        }
        return minimum(treeRoot.left);
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }


    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private Node<T> findNext() {
            Node<T> parent = root;
            Node<T> successor = null;
            if (current == null) {
                successor = root;
                while (successor.left != null) {
                    successor = successor.left;                         //Трудоемкость O(h), где h - высота дерева
                }
                return successor;
            }
            while (parent != null) {
                if (parent.value.compareTo(current.value) > 0) {        //Трудоемкость O(h), где h - высота дерева
                    successor = parent;
                    parent = parent.left;
                } else {
                    parent = parent.right;
                }
            }
            return successor;
        }
        //Трудоемкость - O(h), где h - высота дерева, ресурсоемкость - O(1)

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            root = delete(root, current.value);
            size--;
        }
        //Трудоемкость и ресурсоемкость O(h), где h - высота дерева
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
