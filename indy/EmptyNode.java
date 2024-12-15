package indy;

/**
 * This is the EmptyNode class, which implements the TreeNode interface.
 * It has very little functionality, mostly only able to insert
 */
public class EmptyNode implements TreeNode{
    public EmptyNode() {

    }

    @Override
    public TreeNode getLeft(){
        return new EmptyNode();
    }

    @Override
    public TreeNode getRight(){
        return new EmptyNode();
    }

    @Override
    public void select(){
    }

    @Override
    public int getCost(){
        return 0;
    }

    @Override
    public Upgrade getUpgrade(){
        return Upgrade.NONE;
    }

    @Override
    public TreeNode insert(Upgrade newUpgrade, int newVal, int cost){
        return new TakenNode(newUpgrade, newVal, cost);
    }

}
