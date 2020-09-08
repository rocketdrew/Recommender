package recommender.src;

import java.util.LinkedList;

public class FruitDataset extends Dataset<Fruit> {

    public FruitDataset(LinkedList<String> names, LinkedList<Fruit> datum) {
        super(names, datum);
    }
}
