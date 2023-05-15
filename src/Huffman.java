// name:        date: 
import java.util.*;
import java.io.*;
public class Huffman
{
    private static HashMap<Character, String> codes = new HashMap<>();

   public static Scanner keyboard = new Scanner(System.in);
   public static void main(String[] args) throws IOException
   {
      //Prompt for two strings 
      System.out.print("Encoding using Huffman codes");
      System.out.print("\nWhat message? ");
      String message = keyboard.nextLine();
   
      System.out.print("\nEnter middle part of filename:  ");
      String middlePart = keyboard.next();
   
      huffmanize( message, middlePart );
   }
   public static void huffmanize(String message, String middlePart) throws IOException
   {
         //Make a frequency table of the letters
      	//Put each letter-frequency pair into a HuffmanTreeNode. Put each 
   		//        node into a priority queue (or a min-heap).     
      	//Use the priority queue of nodes to build the Huffman tree
      	//Process the string letter-by-letter and search the tree for the 
   		//       letter. It's recursive. As you recur, build the path through the tree, 
   		//       where going left is 0 and going right is 1.
         //System.out.println the binary message 
      	//Write the binary message to the hard drive using the file name ("message." + middlePart + ".txt")
         //System.out.println the scheme from the tree--needs a recursive helper method
      	//Write the scheme to the hard drive using the file name ("scheme." + middlePart + ".txt")
	   
	   String text = message;
       HashMap<Character, Integer> frequencyMap = buildFrequencyMap(text);
       HuffmanTreeNode root = buildHuffmanTree(frequencyMap);
       generateHuffmanCodes(root, "");
       String encodedText = encode(text);
       String decodedText = decode(encodedText, root);
       
       System.out.println("Original text: " + text);
       System.out.println("Encoded text: " + encodedText);
       System.out.println("Decoded text: " + decodedText);
       
    // Save encoded text to a file
       String fileName = "message."+middlePart+".txt";
       saveToFile(encodedText, fileName);
       System.out.println("Encoded text saved to file: " + fileName);
       
    // Save scheme code to a file
       String binaryCodeFileName = "scheme."+middlePart+".txt";
       buildHuffmanTree(text, binaryCodeFileName);
       System.out.println("Huffman binary code saved to file: " + binaryCodeFileName);
         
            
   }
   
   private static void saveToFile(String content, String fileName) {
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
           writer.write(content);
       } catch (IOException e) {
           System.out.println("Error while saving to file: " + e.getMessage());
       }
   }
   
   
   private static HashMap<Character, Integer> buildFrequencyMap(String text) {
       HashMap<Character, Integer> frequencyMap = new HashMap<>();
       for (char c : text.toCharArray()) {
           frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
       }
       return frequencyMap;
   }

   private static HuffmanTreeNode buildHuffmanTree(HashMap<Character, Integer> frequencyMap) {
       PriorityQueue<HuffmanTreeNode> priorityQueue = new PriorityQueue<>();
       for (char c : frequencyMap.keySet()) {
           HuffmanTreeNode node = new HuffmanTreeNode();
           node.data = c;
           node.frequency = frequencyMap.get(c);
           node.left = null;
           node.right = null;
           priorityQueue.add(node);
       }

       while (priorityQueue.size() > 1) {
           HuffmanTreeNode leftNode = priorityQueue.poll();
           HuffmanTreeNode rightNode = priorityQueue.poll();

           HuffmanTreeNode parent = new HuffmanTreeNode();
           parent.frequency = leftNode.frequency + rightNode.frequency;
           parent.data = '-';
           parent.left = leftNode;
           parent.right = rightNode;

           priorityQueue.add(parent);
       }

       return priorityQueue.poll();
   }

   private static void generateHuffmanCodes(HuffmanTreeNode root, String code) {
       if (root == null) {
           return;
       }

       if (root.left == null && root.right == null) {
           codes.put(root.data, code);
       }

       generateHuffmanCodes(root.left, code + "0");
       generateHuffmanCodes(root.right, code + "1");
   }

   private static String encode(String text) {
       StringBuilder encodedText = new StringBuilder();
       for (char c : text.toCharArray()) {
           encodedText.append(codes.get(c));
       }
       return encodedText.toString();
   }
   private static String decode(String encodedText, HuffmanTreeNode root) {
       StringBuilder decodedText = new StringBuilder();
       HuffmanTreeNode current = root;
       for (char c : encodedText.toCharArray()) {
           if (c == '0') {
               current = current.left;
           } else {
               current = current.right;
           }

           if (current.left == null && current.right == null) {
               decodedText.append(current.data);
               current = root;
           }
       }
       return decodedText.toString();
   }
   
   ///
   public static void printCodes( HuffmanTreeNode root, String code, BufferedWriter writer) throws IOException {
       if (root.isLeaf()) {
           writer.write(root.data + " : " + code);
           writer.newLine();
           return;
       }

       printCodes(root.left, code + "0", writer);
       printCodes(root.right, code + "1", writer);
   }
   
   public static void buildHuffmanTree(String text, String encodedDataFilename) throws IOException {
       Map<Character, Integer> frequencyMap = new HashMap<>();

       for (int i = 0; i < text.length(); i++) {
           char c = text.charAt(i);
           frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
       }

       PriorityQueue< HuffmanTreeNode> pq =new PriorityQueue<>();

       for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
           pq.offer(new  HuffmanTreeNode(entry.getKey(), entry.getValue(), null, null));
       }

       while (pq.size() > 1) {
    	   HuffmanTreeNode left = pq.poll();
    	   HuffmanTreeNode right = pq.poll();

    	   HuffmanTreeNode newNode = new  HuffmanTreeNode('\0', left.frequency + right.frequency, left, right);
           pq.offer(newNode);
       }

       HuffmanTreeNode root = pq.poll();

       // Create a BufferedWriter to write the encoded data to a new file
       BufferedWriter writer = new BufferedWriter(new FileWriter(encodedDataFilename));

       printCodes(root, "", writer);

       // Close the writer
       writer.close();

       System.out.println("Huffman coding completed. Encoded data saved to " + encodedDataFilename);
   }

}
	/*
	  * This tree node stores two values.  Look at TreeNode's API for some help.
	  * The compareTo method must ensure that the lowest frequency has the highest priority.
	  */
class HuffmanTreeNode implements Comparable<HuffmanTreeNode>
{
	char data;
    int frequency;
    HuffmanTreeNode left, right;

    public int compareTo(HuffmanTreeNode node) {
        return frequency - node.frequency;
    }
    public HuffmanTreeNode()
    {
    	
    }
    public HuffmanTreeNode(char data, int frequency, HuffmanTreeNode left, HuffmanTreeNode right) {
        this.data = data;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }

    public int compare(HuffmanTreeNode node1, HuffmanTreeNode node2) {
        return node1.frequency - node2.frequency;
    }
}

//HuffmanComparator implements Comparator<HuffmanNode>





