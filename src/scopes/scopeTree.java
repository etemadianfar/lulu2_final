package scopes;

public class scopeTree {
    private scopeClass startNode;

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
}
