package tracker;

public class IDAndPoints implements Comparable<IDAndPoints>{

    final private int ID;
    private int points;


    public IDAndPoints(int ID, int points) {
        this.ID = ID;
        this.points = points;
    }

    public int getID() {
        return ID;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int compareTo(IDAndPoints idAndPoints) {

        if(this.points > idAndPoints.getPoints())
            return -1;
        else if(this.points < idAndPoints.getPoints())
            return 1;
        else return Integer.compare(this.ID, idAndPoints.getID());
    }
}
