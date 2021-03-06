package scopes;

public class scopeTree {
    public scopeClass startNode;

    public scopeTree(scopeClass parentScope) {
        this.startNode = parentScope;
    }


    public static Boolean find(scopeClass current,String id){
        if(current.getScopeID().equals(id)) {
            return true;
        }else{
            if(current.getFatherNode() != null){
                return find(current.getFatherNode(), id);
            }else {
                return false;
            }
        }
    }

    public void BFS(scopeClass scopeClass){
        System.out.println("name: " + scopeClass.getScopeID() );
        if(scopeClass.getFatherNode() != null)
            System.out.println("father: " + scopeClass.getFatherNode().getScopeID());
        System.out.println("child Count: " + scopeClass.getChildrens().size());
        System.out.println("------------------------------------------------------");

        for (scopeClass scopeClass1 : scopeClass.getChildrens())
            BFS(scopeClass1);
    }
}
