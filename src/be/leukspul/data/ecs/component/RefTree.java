package be.leukspul.data.ecs.component;

import be.leukspul.data.ecs.Entity;
import be.leukspul.data.tree.quad.TreeNode;
import org.json.JSONObject;

public class RefTree extends Component<RefTree> {
    public RefTree(TreeNode<Entity> tn) {
        treeNode = tn;
    }

    @Override
    public String name() {
        return "NodeRef";
    }

    public TreeNode<Entity> treeNode;

    public JSONObject toJSON() throws IllegalAccessException {
        return null;
    }
}
