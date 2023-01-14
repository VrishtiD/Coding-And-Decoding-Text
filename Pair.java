/**
 * Assignment 5:
 * Vrishti Dawra
 * B00906945
 * The pair class acts as an object for the binary tree and hence
 * stores the values of letters and their probabilities
 */
public class Pair implements Comparable<Pair>{
    // declare all required fields
    private char value;   // for letters
    private double prob;  // for the probability
    //constructor
    public Pair(char value, double prob) {
        this.value = value;
        this.prob = prob;
    }

    //getters

    public char getValue() {
        return value;
    }

    public double getProb() {
        return prob;
    }

    //setters

    public void setValue(char value) {
        this.value = value;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }

    //toString

    @Override
    public String  toString() {
        return "Pair{" +
                "value=" + value +
                ", prob=" + prob +
                '}';
    }

    /**
     The compareTo method overrides the compareTo method of the
     Comparable interface.
     */
    @Override
    public int compareTo(Pair p){
        return Double.compare(this.getProb(), p.getProb());
    }
}

