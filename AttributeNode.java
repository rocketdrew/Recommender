package recommender.src;

import java.util.LinkedList;

public class AttributeNode implements INode {
  public String attribute; // Represents the name of the attribute in this node
  public Object commonValue; // The most common value of the target attribute with the subset dataset we have
  public LinkedList<Edge> edges;

  public AttributeNode(String attribute, Object commonValue, LinkedList<Edge> edges) {
    this.attribute = attribute;
    this.commonValue = commonValue;
    this.edges = edges;
  }

  @Override
  public Object lookupDecision(IAttributeDatum attrVals) {
    for (Edge current : this.edges) {// we should traverse through the next value list with the condition of i < size of nextvalue to traverse through all the values in the list
      if (current.value == null) {
        throw new RuntimeException("bad edge");
      }
      if (current.value.equals(attrVals.getValueOf(this.attribute))) {
        return current.nextNode.lookupDecision(attrVals);
      }
      return this.commonValue;
    }
    throw new RuntimeException("lookupDecision failed to return a leaf or most common value");
  }

  @Override
  public void printNode(String leadspace) {
    System.out.println(this.attribute + leadspace + "\n");
    for (Edge thisEdge : this.edges) {
      thisEdge.nextNode.printNode(leadspace);
    }
  }
}
