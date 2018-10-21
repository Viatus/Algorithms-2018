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
        Node<T> vertex = find((T) o);
        if (vertex == null) {
            return false;
        }
        Node<T> parent = findParent(vertex.value);
        if (vertex.left == null && vertex.right == null) {
            if (parent.left == vertex) {
                parent.left = null;
            }
            if (parent.right == vertex) {
                parent.right = null;
            }
        } else {
            if (vertex.left == null || vertex.right == null) {
                if (vertex.left == null) {
                    if (parent.left == vertex) {
                        parent.left = vertex.right;
                    } else {
                        parent.right = vertex.right;
                    }
                } else {
                    if (parent.left == vertex) {
                        parent.left = vertex.left;
                    } else {
                        parent.right = vertex.left;
                    }
                }
            } else {
                Node<T> successor;
                Iterator<T> iterator = new BinaryTreeIterator();
                while (iterator.hasNext()) {
                    if (iterator.next() == vertex) {
                        break;
                    }
                }
                successor = find(iterator.next());
                Node<T> temp = new Node<>(successor.value);
                temp.right = vertex.right;
                temp.left = vertex.left;
                vertex = temp;
                if (findParent(successor.value).left == successor) {
                    findParent(successor.value).left = successor.right;
                } else {
                    findParent(successor.value).right = successor.left;
                }
            }
        }
        return true;
        /*@SuppressWarnings("unchecked")
        T t = (T) o;
        if (root == null) {
            return false;
        }
        delete(root, t);
        return true;*/
    }

    private Node<T> delete(Node<T> root, T value) {
        if (value.compareTo(root.value) < 0) {
            root.left = delete(root.left, value);
        } else {
            if (value.compareTo(root.value) > 0) {
                root.right = delete(root.right, value);
            } else {
                if (root.left != null && root.right != null) {
                    Node<T> temp = new Node<>(minimum(root.right).value);
                    temp.left = root.left;
                    temp.right = root.right;
                    root = temp;
                    root.right = delete(root.right, root.value);
                } else {
                    if (root.left != null) {
                        root = root.left;
                    } else {
                        root = root.right;
                    }
                }
            }
        }
        return root;
    }

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

    private Node<T> findParent(T value) {
        Node<T> current = root;
        if (current.value.compareTo(value) == 0) {
            return null;
        }
        while (true) {
            if (current.right != null) {
                if (current.right.value.compareTo(value) == 0) {
                    return current;
                } else {
                    if (current.value.compareTo(value) < 0) {
                        if (current.right != null) {
                            current = current.right;
                        } else {
                            return null;
                        }
                    }
                }
            }
            if (current.left != null) {
                if (current.left.value.compareTo(value) == 0) {
                    return current;
                } else {
                    if (current.value.compareTo(value) > 0) {
                        if (current.left != null) {
                            current = current.left;
                        } else {
                            return null;
                        }
                    }
                }
            }

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
            if (current == null) {
                current = root;
                while (current.left != null) {
                    current = current.left;
                }
                return current;
            }
            if (current.right != null) {
                current = current.right;
                while (current.left != null) {
                    current = current.left;
                }
                return current;
            }
            while (true) {
                if (findParent(current.value) == null) {
                    return null;
                }
                if (findParent(current.value).left == current) {
                    return findParent(current.value);
                }
                current = findParent(current.value);
            }
        }

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
            // TODO
            throw new NotImplementedError();
        }
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
