package scopes;

public class scopeTree {
    public scopeClass startNode;

    public scopeTree(scopeClass parentScope) {
        this.startNode = parentScope;
    }



    public symbolTableRow find(scopeClass current,String id){
        if(current.getSymbolTable().get(id) != null) {
            return current.getSymbolTable().get(id);
        }else{
            if(current.getFatherNode() != null){
                return find(current.getFatherNode(), id);
            }else {
                return null;
            }
        }
    }

    public void BFS(scopeClass scopeClass){
        System.out.println("name: " + scopeClass.getScopeID() );
        System.out.println(scopeClass.getSymbolTable());
        System.out.println("------------------------------------------------------");

        for (scopeClass scopeClass1 : scopeClass.getChildrens())
            BFS(scopeClass1);
    }
}
