package lesson5;

import kotlin.NotImplementedError;
import lesson5.impl.GraphBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     * <p>
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        throw new NotImplementedError();
    }


    /**
     * Минимальное остовное дерево.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ:
     * <p>
     * G    H
     * |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     * <p>
     * Дан граф без циклов (получатель), например
     * <p>
     * G -- H -- J
     * |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     * <p>
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     * <p>
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     * <p>
     * В данном случае ответ (A, E, F, D, G, J)
     * <p>
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        Map<Set<Graph.Vertex>, Boolean> setsWithIndependence = new HashMap<>();
        for (Graph.Vertex vertex : graph.getVertices()) {
            setsWithIndependence.put(new HashSet<>(Collections.singleton(vertex)), true);
        }
        List<Set<Graph.Vertex>> previous = new ArrayList<>();
        for (Graph.Vertex vertex : graph.getVertices()) {
            previous.add(new HashSet<>(Collections.singleton(vertex)));
        }
        for (int i = 0; i < graph.getVertices().size() - 1; i++) {
            List<Set<Graph.Vertex>> newPrevious = new ArrayList<>();
            for (Set<Graph.Vertex> set : previous) {
                if (setsWithIndependence.get(set)) {
                    for (Graph.Vertex addingVertex : graph.getVertices()) {
                        Set<Graph.Vertex> newSet = new HashSet<>(set);
                        newSet.add(addingVertex);
                        if (!previous.contains(newSet) && !newPrevious.contains(newSet)) {
                            newPrevious.add(newSet);
                            setsWithIndependence.put(newSet, setsWithIndependence.get(set) && isIndependent(graph, set, addingVertex));
                        }
                    }
                }
            }
            previous = newPrevious;
        }
        int max = 0;
        Set<Graph.Vertex> maxSet = new HashSet<>();
        for (Map.Entry<Set<Graph.Vertex>, Boolean> entry : setsWithIndependence.entrySet()) {
            if (entry.getValue() && entry.getKey().size() > max) {
                max = entry.getKey().size();
                maxSet = entry.getKey();
            }
        }
        return maxSet;
    }

    //Трудоемкость - O(2^V*E), так как наиболее трудоемкой частью является перебор всех 2^V подмножеств множества вершин,
    //внутри которого вызывается функция проверки независимости элемента относительно множества трудоемкостью O(E),
    //Ресурсоемкость - O(2^V), потому что создатеся ассоциативный массив, размером в количество всех подмножеств

    private static boolean isIndependent(Graph graph, Set<Graph.Vertex> set, Graph.Vertex vertex) {
        for (Graph.Edge edge : graph.getEdges()) {
            if (edge.getBegin() == vertex && set.contains(edge.getEnd()) || edge.getEnd() == vertex && set.contains(edge.getBegin())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     * <p>
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        List<Graph.Vertex> possiblePath = new ArrayList<>();
        for (Graph.Vertex vertex : graph.getVertices()) {
            Map<Graph.Vertex, Graph.Vertex> cameFrom = new HashMap<>();
            Queue<Graph.Vertex> open = new ArrayDeque<>();
            Set<Graph.Vertex> closed = new HashSet<>();
            Map<Graph.Vertex, Integer> costSoFar = new HashMap<>();
            costSoFar.put(vertex, 0);
            open.add(vertex);
            cameFrom.put(vertex, null);
            while (!open.isEmpty()) {
                Graph.Vertex current = open.poll();
                closed.add(current);
                for (Graph.Vertex v : graph.getNeighbors(current)) {
                    if (!closed.contains(v) && !open.contains(v)) {
                        open.add(v);
                        cameFrom.put(v, current);                                  //Трудоемкость O(V + E)
                        costSoFar.put(v, costSoFar.get(current) + 1);
                        break;
                    }
                }
            }
            int max = 0;
            Graph.Vertex maxVertex = (Graph.Vertex) closed.toArray()[0];
            for (Map.Entry<Graph.Vertex, Integer> entry : costSoFar.entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();                                                  //Трудоемкость O(V)
                    maxVertex = entry.getKey();
                }
            }
            List<Graph.Vertex> list = new ArrayList<>();
            list.add(maxVertex);
            while (cameFrom.get(maxVertex) != null) {
                list.add(cameFrom.get(maxVertex));
                maxVertex = cameFrom.get(maxVertex);                                        //Трудоемкость O(V)
            }
            if (list.size() > possiblePath.size()) {
                possiblePath = list;
            }
        }
        return new Path(possiblePath, possiblePath.size() - 1);
    }
}

//Трудоемкость - O(V(V+E)), ресурсоемкость O(V+E), так как поиск в ширину имеет ресурсоемкость O(V+E), а остальные
//элементы в цикле имеют ресурсоемкость O(V) или O(1)