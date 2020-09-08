package recommender.src;
import java.util.LinkedList;

public class Dataset<T extends IAttributeDatum> implements IAttributeDataset<T> {
    public LinkedList<String> names;
    public LinkedList<T> datum;

    public Dataset(LinkedList<String> names, LinkedList<T> datum){
        this.names = names;
        this.datum = datum;
    }

    @Override
    public LinkedList<String> getAttributes() {
        return names; // returns the list of attributes
    }

    @Override
    public boolean allSameValue(String ofAttribute) {
        Object firstDatum = this.datum.get(0).getValueOf(ofAttribute);
        for (T i : datum) {
            if (!i.getValueOf(ofAttribute).equals(firstDatum))
                return false;
        }
        return true;
    }

    @Override
    public int size() {
        return this.datum.size();
    }

    @Override
    public LinkedList<IAttributeDataset<T>> partition(String onAttribute) {
        LinkedList<IAttributeDataset<T>> partitions = new LinkedList<>();
        LinkedList<LinkedList<T>> sortedData = new LinkedList<LinkedList<T>>();

        for(T thisDatum: this.datum){
            this.boolHelper(sortedData, thisDatum, onAttribute);
        }

        for(LinkedList<T> subList : sortedData){
            partitions.add(new Dataset<T>(this.names, subList));
        }

        return partitions;

        //Dataset part = new Dataset(new LinkedList<>(), new LinkedList<>());
//        LinkedList<IAttributeDataset<T>> lst = new LinkedList<>();
//        lst.add(new Dataset(l1, l2));
//        return lst;
    }


    public boolean boolHelper(LinkedList<LinkedList<T>> list, T tar, String onAttribute) {
        for (LinkedList<T> someList : list){
            if (someList.getFirst().getValueOf(onAttribute).equals(tar.getValueOf(onAttribute))) {
                return someList.add(tar);
            }
        }
        LinkedList<T> tempData = new LinkedList<>();
        tempData.add(tar);
        list.addLast(tempData); // this was: new Dataset<T>(this.names, null).datum.set(0, tar)
        return false;
    }

    @Override
    public Object getSharedValue(String ofAttribute) {
        return this.datum.get(0).getValueOf(ofAttribute);
    }

    public static class valNode {
        Object val;
        int count;

        valNode(Object val, int count) {
            this.val = val;
            this.count = count;
        }
    }

    @Override
    public Object mostCommonValue(String ofAttribute) {
        LinkedList<valNode> valList = new LinkedList<valNode>();
//        valList.add(new valNode(this.datum.get(0), 1));
        for (T thisDatum : this.datum) {
            Object curVal = thisDatum.getValueOf(ofAttribute);
            if (!updateList(valList, curVal)) {
                valList.addLast(new valNode(curVal, 1));
            }
        }
        return argMax(valList);
    }

    public boolean updateList(LinkedList<valNode> list, Object val) {
        for(valNode current : list) {
            if (current.val.equals(val)) {
                current.count = current.count + 1;
                return true;
            }
        } return false;
    }

    public Object argMax(LinkedList<valNode> list){
        valNode maxVal = new valNode(list.getFirst().val, list.getFirst().count);
        for(valNode current : list) {
            if (current.count > maxVal.count) {
                maxVal = current;
            }
        }
        return maxVal.val;
    }

}