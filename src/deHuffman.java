import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class deHuffman {
    private static TreeNode root;

    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("\nWhat binary message (middle part)? ");
        String middlePart = keyboard.next();
        keyboard.close();
        
        Scanner sc = new Scanner(new File("message." + middlePart + ".txt"));
        String binaryCode = sc.next();
        sc.close();
        
        Scanner huffLines = new Scanner(new File("scheme." + middlePart + ".txt"));
        TreeNode root = huffmanTree(huffLines);
        huffLines.close();
        
        String message = dehuff(binaryCode, root);
        System.out.println("Decoded :"+message);
        
    }
    
    
    public static TreeNode huffmanTree(Scanner huffLines) {
    	 root = new TreeNode("");

         while (huffLines.hasNextLine()) {
             String line = huffLines.nextLine();
             if (!line.isEmpty()) {
                 String character = line.substring(0, 1);
                 String code = line.substring(1);
                 
                 TreeNode currentNode = root;
                 for (char c : code.toCharArray()) {
                     if (c == '0') {
                         if (currentNode.getLeft() == null) {
                             currentNode.setLeft(new TreeNode(""));
                         }
                         currentNode = currentNode.getLeft();
                     } else if (c == '1') {
                         if (currentNode.getRight() == null) {
                             currentNode.setRight(new TreeNode(""));
                         }
                         currentNode = currentNode.getRight();
                     }
                 }
                 
                 currentNode.setValue(character);
             }
         }
         
         return root;
    }

    public static String dehuff(String text, TreeNode root) {
        StringBuilder message = new StringBuilder();
        TreeNode currentNode = root;
        
        for (char c : text.toCharArray()) {
            if (c == '0') {
                currentNode = currentNode.getLeft();
            } else if (c == '1') {
                currentNode = currentNode.getRight();
            }
            
            if (currentNode.getLeft() == null && currentNode.getRight() == null) {
                message.append(currentNode.getValue());
                currentNode = root;
            }
        }
        
        return message.toString();
    }
}

class TreeNode {
    private Object value;
    TreeNode left;
	TreeNode right;

    public TreeNode(Object initValue) {
        value = initValue;
        left = null;
        right = null;
    }

    public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight) {
        value = initValue;
        left = initLeft;
        right = initRight;
    }

    public Object getValue() {
        return value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setValue(Object theNewValue) {
        value = theNewValue;
    }

    public void setLeft(TreeNode theNewLeft) {
        left = theNewLeft;
    }

    public void setRight(TreeNode theNewRight) {
        right = theNewRight;
    }
    public boolean isLeaf() {
        return (left == null && right == null);
    }
}
