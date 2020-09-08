package recommender.src;

public class Edge {
  public Object value;
  public INode nextNode;

  public Edge(Object value, INode nextNode) {
    this.value = value;
    this.nextNode = nextNode;
  }

}
