package recommender.src;

public class Leaf implements INode {
  public Object value;

  public Leaf(Object value) {
    this.value = value;
  }

  @Override
  public Object lookupDecision(IAttributeDatum attrVals) {
    return this.value;
  }

  @Override
  public void printNode(String leadspace) {
    System.out.println(leadspace + this.value);
  }
}
