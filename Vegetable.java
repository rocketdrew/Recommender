package recommender.src;

public class Vegetable implements IAttributeDatum {
  public String name;
  public String color;
  public boolean lowCarb;
  public boolean highFiber;
  public boolean likeToEat;

  public Vegetable(String name, String color, boolean lowCarb, boolean highFiber, boolean likeToEat) {
    this.name = name;
    this.color = color;
    this.lowCarb = lowCarb;
    this.highFiber = highFiber;
    this.likeToEat = likeToEat;
  }

  @Override
  public Object getValueOf(String attributeName) {
    if (attributeName.equals("name")) {
      return this.name;
    } else if (attributeName.equals("color")) {
      return this.color;
    } else if (attributeName.equals("lowCarb")) {
      return this.lowCarb;
    } else if (attributeName.equals("highFiber")) {
      return this.highFiber;
    } else if (attributeName.equals("likeToEat")) {
      return this.likeToEat;
    } else {
        throw new RuntimeException("Not a valid vegetable attribute");
    }

  }
}
