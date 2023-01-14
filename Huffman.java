/**
 * Assignment 5:
 * Vrishti Dawra
 * B00906945
 * This class executes all the function specified in the assignment.
 * Sorts the values in queue S in ascending order
 * applies the huffman principal on it and save the binary tree in queue T
 * It takes the text from user and successfully encodes and decodes it
 */

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class Huffman {
    // for storing the letters and the encoded text
    static HashMap<Character,String> values= new HashMap<>();


    public static void main(String[] args) throws FileNotFoundException {

        System.out.print("Enter the filename to read from: ");
        // scanner object to read the input file
        Scanner in = new Scanner(System.in);
        // file to read the probabilities
        String input = in.nextLine();

        File file = new File(input);
        Scanner inputFile = new Scanner(file);
        // list to store all the records of binary tree object
        ArrayList<BinaryTree<Pair>> records = new ArrayList<>();
        // the queue to be sorted
        Queue<BinaryTree<Pair>> S = new LinkedList<>();
        // queue to implement huffman principle
        Queue<BinaryTree<Pair>> T = new LinkedList<>();


        char letter;  // letter from input file
        double prob;  // probability from input file

// takes the values from input file and stores it records array as binary tree objects
        while (inputFile.hasNext()) {

            letter = inputFile.next().charAt(0);
            prob = inputFile.nextDouble();

            Pair value = new Pair(letter, prob);
            BinaryTree<Pair> tree = new BinaryTree<Pair>();
            tree.setData(value);

            records.add(tree);
        }

        inputFile.close();

        // adding the values from record array to queue S
        for (BinaryTree<Pair> i :
                records) {
            S.add(i);

        }
        // calling the function to sort the queue S
        sort(S);

        // loop to check the smallest probability in S and T and adds to Queue T
        while (!S.isEmpty()) {
            BinaryTree<Pair> A = new BinaryTree<>();  // Value holding smallest probability
            BinaryTree<Pair> B = new BinaryTree<>();  // Value holding smallest probability

            // if T is empty the it only adds values from S and hence removes it from S
            if (T.isEmpty()) {
                A = S.peek();
                S.remove();
                B = S.peek();
                S.remove();
            }
            // checking for the smallesT probability among Queue S and T
            // When found it removes it from the queue
            else {
                if ((T.isEmpty()) || S.peek().getData().getProb() < T.peek().getData().getProb()) {
                    A = S.peek();
                    S.remove();
                } else if (S.peek().getData().getProb() > T.peek().getData().getProb()) {
                    A = T.peek();
                    T.remove();
                }
                if (T.isEmpty() || (S.peek().getData().getProb()) < (T.peek().getData().getProb())) {
                    B = S.peek();
                    S.remove();
                } else if (S.peek().getData().getProb() > T.peek().getData().getProb()) {
                    B = T.peek();
                    T.remove();
                }
            }
            // making the parent root that will contain the sum of smallest probabilities
            BinaryTree<Pair> P = new BinaryTree<>();
            Pair pr = new Pair('a', A.getData().getProb() + B.getData().getProb());
            P.makeRoot(pr);
            P.attachLeft(A);
            P.attachRight(B);

// adding the parent root to the Queue T
            T.add(P);

        }
        // execute till there is just one tree left in T
        while (T.size() > 1) {
            BinaryTree<Pair> w = new BinaryTree<>();
            BinaryTree<Pair> A = T.poll();
            BinaryTree<Pair> B = T.poll();
          // adding the 2 Binary trees values in T and making it the parent
            double sum = A.getData().getProb() + B.getData().getProb();

            Pair p = new Pair('-', sum);
            w.setData(p);
            // attactching the earlier nodes of T as the left and right child of parent
            w.attachLeft(A);
            w.attachRight(B);

            A.setParent(w);
            B.setParent(w);
            T.add(w);

        }
         // getting the encoded Binary tree
        getCodes(T.peek(),"");
        // arraylist to store encoded values
      ArrayList<String> codes=new ArrayList<>();

        System.out.print("Enter a line of text (uppercase letters only): ");
        String line = in.nextLine();
        // to store the string in an array
        char[] ch = new char[line.length()];

        for (int i = 0; i < line.length(); i++) {
            ch[i] = line.charAt(i);
        }
        System.out.print("Here’s the encoded line:    ");
        // loop to match the letter to the encoded value
        for(int i=0;i<line.length();i++){
            codes.add(values.get(ch[i]));
        }
        // loop to display the encoded values of the String
        for (String i:
            codes ) {
            System.out.print(i);
        }

        String decode="";
        // for decoding the codded calues into letters
        for (int i=0;i<ch.length;i++){
            BinaryTree<Pair> t=T.peek();
            while(t.getLeft()!=null && t.getRight()!=null ){
                if(codes.get(i).equals("1")){   // check if the value is 1, and goes right
                    t=t.getRight();
                }
                else{                      // if value is 0 it goes left of the binary tree
                    t=t.getLeft();
                }

            }
                 if(t!=null)
                decode+= t.getData().getValue();

        }
        System.out.println("");
        System.out.print("Here’s the Decoded line:    ");
        System.out.print(decode);


    }

    /**
     * method to push the front element in queue to back
     * @param q binary tree of the records
     * @param qsize  Size of the queue S
     */
    public static void FrontToBack(Queue<BinaryTree<Pair>> q, int qsize) {
        if (qsize <= 0)
            return;
        q.add(q.poll());
        FrontToBack(q, qsize - 1);
    }

    /**
     * Pushes the element to the queue
     * @param q binary tree containing the nodes
     * @param temp binary tree in the front
     * @param qsize size of queue S
     */
    static void push(Queue<BinaryTree<Pair>> q,
                            BinaryTree<Pair> temp, int qsize) {
        if (q.isEmpty() || qsize == 0) {
            q.add(temp);
            return;
        } else if (temp.getData().getProb() <= q.peek().getData().getProb()) {
            q.add(temp);
            FrontToBack(q, qsize);
        } else {
            q.add(q.poll());
            push(q, temp, qsize - 1);
        }
    }

    /**
     * Main fuction for sorting Queue S
     * @param q binary tree containing all the records.
     */
    static void sort(Queue<BinaryTree<Pair>> q) {
        if (q.isEmpty())
            return;
        BinaryTree t = q.poll();
        sort(q);
        push(q, t, q.size());
    }

    /**
     * method to get the encoded data of the Binary tree
     * @param bt binary tree to be encoded
     * @param s The encode values (0/1)
     */
    public static void getCodes(BinaryTree<Pair> bt, String s) {
    if (bt != null) {
        // if the value is on right side return 1
        if (bt.getRight() != null)
            getCodes(bt.getRight(), s + "1");
       // if the value is on left side return 0
        if (bt.getLeft() != null)
            getCodes(bt.getLeft(), s + "0");

        if (bt.getLeft() == null && bt.getRight() == null)
            // adding the values to the hashtable
            values.put(bt.getData().getValue(), s);
    }
}

}