package TreeNode;

/* TreeNode class for the AP Exams 
 */
public class TreeNode {
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

