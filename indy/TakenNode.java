package indy;

/**
 * This is the TakenNode class, which implements the TreeNode interface
 * so that the BTree of upgrades works.
 */
public class TakenNode implements TreeNode {
    private TreeNode left;
    private TreeNode right;
    private boolean selected;
    private int val;
    private int cost;
    private Upgrade upgrade;

    /**
     * This is the TakenNode constructor initializing everything
     */
    public TakenNode(Upgrade upgrade, int val, int cost) {
        this.selected = false;
        this.upgrade = upgrade;
        this.val = val;
        this.cost = cost;
        this.left = new EmptyNode();
        this.right = new EmptyNode();
    }

    /**
     * Getter for the left node, gets the left-most, first non-selected node
     */
    @Override
    public TreeNode getLeft() {
        if(this.selected) return this.left.getLeft();
        return this;
    }

    /**
     * Getter for the right node, gets the right-most, first non-selected node
     */
    @Override
    public TreeNode getRight() {
        if(this.selected) return this.right.getRight();
        return this;
    }

    /**
     * Setter for the select variable
     */
    @Override
    public void select(){
        this.selected = true;
    }

    /**
     * Getter for the upgrade at the node
     */
    @Override
    public Upgrade getUpgrade() {
        return this.upgrade;
    }

    /**
     * Getter for the upgrade cost at the node
     */
    @Override
    public int getCost() {
        return this.cost;
    }

    /**
     * Adds node to the end in the sorted position
     */
    @Override
    public TreeNode insert(Upgrade newUpgrade, int newVal, int cost){
        if(newVal < this.val) this.left = this.left.insert(newUpgrade, newVal, cost);
        else this.right = this.right.insert(newUpgrade, newVal, cost);
        return this;
    }
}
