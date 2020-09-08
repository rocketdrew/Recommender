package recommender.src;

public class Fruit implements IAttributeDatum {
    public String name;
    public boolean fruitSalad;
    public double weight;
    public int sugar;
    public boolean likeToEat;

    public Fruit(String name, boolean fruitSalad, double weight, int sugar, boolean likeToEat){
        this.name = name;
        this.fruitSalad = fruitSalad;
        this.weight = weight;
        this.sugar = sugar;
        this.likeToEat = likeToEat;
    }

    @Override
    public Object getValueOf(String attributeName) {
        if(attributeName.equals("name")){
            return this.name;
        } else if(attributeName.equals("fruitSalad")){
            return this.fruitSalad;
        } else if(attributeName.equals("weight")){
            return this.weight;
        } else if(attributeName.equals("sugar")){
            return this.sugar;
        } else if (attributeName.equals("likeToEat")){
            return this.likeToEat;
        } else {
            throw new RuntimeException("Not a valid vegetable attribute");
        }
    }
}
