package recommender.src;

import java.util.LinkedList;
import java.util.Random;

public class TreeGenerator<T extends IAttributeDatum> implements IGenerator {
  public Dataset<T> curData;
  public INode rootNode;
  public LinkedList<String> dep;
  public Dataset<T> depData;

  public TreeGenerator(Dataset<T> dataset) {
    this.curData = dataset;
    this.rootNode = null;
    this.dep = new LinkedList<>(this.curData.names);
    this.depData = new Dataset<T>(this.dep, this.curData.datum);
  }

  @Override
  public INode buildClassifier(String targetAttr) {
//    for (String thisname : this.dep) {
//      if (thisname.equals(targetAttr)) {
//        this.dep.remove(targetAttr);// remove the target attribute before we partition any attribute so we don't partition on that target attribute
//      }
//    }

    return buildClassifierBody(targetAttr, this.dep);
  }

  public INode buildClassifierBody(String targetAttr, LinkedList<String> names) {

    if (this.dep.isEmpty()) { // Base case checking if we have an empty list of names is empty
      return new Leaf(this.depData.mostCommonValue(targetAttr)); // Just returns the most common value of the target attribute with the current dataset because we can no longer partition on anything
    }

    if (this.depData.datum.isEmpty()) { // Base case checking if there is an empty dataset passed in
      throw new RuntimeException("Can't build from empty dataset"); // throw a runtime exception because within partition we account for this that there should never be an empty dataset assuming that there is at least one datum to start in the original dataset
    }

    if (this.depData.allSameValue(targetAttr)) { // Base case checking if all the datum within a dataset has the same value for the target attribute
      return new Leaf(this.depData.getSharedValue(targetAttr)); // return that shared attribute value
    }

    String nextAt = this.randomAt(names); //select a random attribute to partition the dataset on
    Object commonValue = this.depData.mostCommonValue(targetAttr); // calculate the default answer of the mostCommonValue on the target attribute in the dataset at that point to store in the node

    AttributeNode newNode = new AttributeNode(nextAt, commonValue, buildClassifierHelper(this.depData.partition(nextAt), nextAt, targetAttr, names));  //create a node
    INode startNode = newNode;
    this.rootNode = startNode;
    return startNode; // Not sure if we return the rootNode which is the shallow copy we made earlier or the newNode
  }

  public LinkedList<Edge> buildClassifierHelper(LinkedList<IAttributeDataset<T>> partitionList, String nextAt, String tarAttr, LinkedList<String> names) {
    LinkedList<Edge> edgeList = new LinkedList<>(); //create a new list of edges
    for (IAttributeDataset<T> part : partitionList) {// for each partioned list
      if (part.size() == 1) {
        edgeList.add(new Edge(part.getSharedValue(nextAt), new Leaf(part.mostCommonValue(tarAttr))));
      } else {
        this.depData = (Dataset<T>) part;
        edgeList.addFirst(new Edge(part.getSharedValue(nextAt), buildClassifierBody(tarAttr, names))); // addcreates the edge with the recursive call to build the rest of the tree
      }
    }
    return edgeList;
  }

  public String randomAt(LinkedList<String> names) {
    Random random = new Random();
    int index = random.nextInt(names.size());
    String ranAttr = names.get(index); // assigns the random attribute from the list
    names.remove(ranAttr); // removes it from the list so it is not used twice
    return ranAttr; // returns that ran attribute
  }


  @Override
  public Object lookupRecommendation(IAttributeDatum forVals) {
//        if (rootNode == null) {
//        this.buildClassifier()
//        }
    return this.rootNode.lookupDecision(forVals);
  }

  @Override
  public void printTree() {

  }
}
