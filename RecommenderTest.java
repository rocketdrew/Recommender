package recommender.src;

import tester.Tester;

import java.util.LinkedList;

public class RecommenderTest {

  /**
   * A method to generate an example VegetableDataset
   */

  public VegetableDataset setupVeg() {
    VegetableDataset veggies = new VegetableDataset(new LinkedList<String>(), new LinkedList<Vegetable>());
    veggies.datum.add(new Vegetable("lettuce", "green", true, false, true));
    veggies.datum.add(new Vegetable("carrot", "orange", false, false, false));
    veggies.datum.add(new Vegetable("peas", "green", false, true, true));
    veggies.datum.add(new Vegetable("kale", "green", true, true, true));
    veggies.datum.add(new Vegetable("spinach", "green", true, true, false));
    veggies.names.add("likeToEat");
    veggies.names.add("highFiber");
    veggies.names.add("lowCarb");
    veggies.names.add("color");
    veggies.names.add("name");
    return veggies;
  }

  public VegetableDataset setupOneVeg() {
    VegetableDataset oneVeg = new VegetableDataset(new LinkedList<String>(), new LinkedList<Vegetable>());
    oneVeg.datum.add(new Vegetable("lettuce", "green", true, false, true));
    oneVeg.names.addFirst("likeToEat");
    oneVeg.names.addFirst("highFiber");
    oneVeg.names.addFirst("lowCarb");
    oneVeg.names.addFirst("color");
    oneVeg.names.addFirst("name");
    return oneVeg;
  }

  public FruitDataset setupFruit() {
    FruitDataset fruits = new FruitDataset(new LinkedList<String>(), new LinkedList<Fruit>());
    fruits.datum.add(new Fruit("apple", true, 1.2, 50, true));
    fruits.datum.add(new Fruit("banana", true, 1.5, 30, false));
    fruits.datum.add(new Fruit("tomato", false, 1.2, 23, false));
    fruits.names.add("likeToEat");
    fruits.names.add("sugar");
    fruits.names.add("weight");
    fruits.names.add("fruitSalad");
    fruits.names.add("name");
    return fruits;

  }

  Edge greenEdge = new Edge("green", new Leaf(true));
  Edge orangeEdge = new Edge("orange", new Leaf(false));
  LinkedList<Edge> edgeList = new LinkedList<Edge>();
  AttributeNode x = new AttributeNode("color", false, this.edgeList);


  /**
   * A method to test the VegetableDataset class
   *
   * @param t
   */
  public void testVegetableDataset(Tester t) {


    t.checkExpect(this.setupVeg().getAttributes(), this.setupVeg().names);
    t.checkExpect(this.setupFruit().getAttributes(), this.setupFruit().names);

    t.checkExpect(this.setupOneVeg().getAttributes(), this.setupOneVeg().names);

    t.checkExpect(this.setupVeg().allSameValue("color"), false);
    t.checkExpect(this.setupFruit().allSameValue("likeToEat"), false);

    VegetableDataset allSameTest = this.setupVeg();
    allSameTest.datum.remove(1);
    t.checkExpect(allSameTest.allSameValue("color"), true);

    t.checkExpect(this.setupOneVeg().allSameValue("color"), true);

    FruitDataset allSameFruitTest = this.setupFruit();
    allSameFruitTest.datum.remove(1);
    t.checkExpect(allSameFruitTest.allSameValue("weight"), true);

    t.checkExpect(this.setupVeg().size(), 5);
    t.checkExpect(this.setupOneVeg().size(), 1);
    t.checkExpect(this.setupFruit().size(), 3);

    t.checkExpect(this.setupVeg().partition("color").size(), 2);
    t.checkExpect(this.setupVeg().partition("name").size(), 5);

    t.checkExpect(this.setupVeg().partition("color").getFirst().size(), 4);
    t.checkExpect(this.setupVeg().partition("color").getLast().size(), 1);

    t.checkExpect(this.setupOneVeg().partition("color").size(), 1);
    t.checkExpect(this.setupOneVeg().partition("name").size(), 1);

    t.checkExpect(this.setupFruit().partition("fruitSalad").size(), 2);
    t.checkExpect(this.setupFruit().partition("weight").size(), 2);

    t.checkExpect(this.setupFruit().partition("fruitSalad").getFirst().size(), 2);
    t.checkExpect(this.setupFruit().partition("weight").getLast().size(), 1);

    t.checkExpect(this.setupVeg().partition("color").getFirst().getSharedValue("color"), "green");
    t.checkExpect(this.setupVeg().partition("color").getLast().getSharedValue("color"), "orange");

    t.checkExpect(this.setupVeg().mostCommonValue("likeToEat"), true);
    t.checkExpect(this.setupVeg().mostCommonValue("lowCarb"), true);
    t.checkExpect(this.setupOneVeg().mostCommonValue("likeToEat"), true);
    t.checkExpect(this.setupFruit().mostCommonValue("fruitSalad"), true);
    t.checkExpect(this.setupFruit().mostCommonValue("sugar"), 50);
  }

  /**
   * A method to test the Vegetable class
   *
   * @param t
   */
  public void testVegetable(Tester t) {
    t.checkExpect(this.setupVeg().datum.getLast().getValueOf("color"), "green");
    t.checkExpect(this.setupVeg().datum.getLast().getValueOf("lowCarb"), true);
    t.checkExpect(this.setupVeg().datum.getLast().getValueOf("likeToEat"), false);
    t.checkExpect(this.setupOneVeg().datum.getFirst().getValueOf("color"), "green");
    t.checkExpect(this.setupOneVeg().datum.getFirst().getValueOf("lowCarb"), true);
    t.checkExpect(this.setupOneVeg().datum.getFirst().getValueOf("highFiber"), false);

    t.checkExpect(this.setupFruit().datum.getLast().getValueOf("name"), "tomato");

    TreeGenerator<Fruit> fruit = new TreeGenerator<>(this.setupFruit());


    //this.x.printNode("   ");
    fruit.buildClassifier("likeToEat").printNode("   ");

  }

  /**
   * A method to test the AttributeNode class
   *
   * @param
   */


  public static void main(String[] args) {
    Tester.run(new RecommenderTest());
  }
}
