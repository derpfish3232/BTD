package indy;

/**
 * This is the TreeNode interface, used to construct the b-tree of upgrades
 */
public interface TreeNode {
    public TreeNode getLeft();
    public TreeNode getRight();
    public void select();
    public Upgrade getUpgrade();
    public int getCost();
    public TreeNode insert(Upgrade newUpgrade, int newVal, int cost);
}
