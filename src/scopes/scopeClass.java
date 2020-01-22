package scopes;

import enums.scopeTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class scopeClass {

    private HashMap<String, symbolTableRow> symbolTable; //for keeping contents's informations
    private scopeClass fatherNode;
    private ArrayList<scopeClass> childrens;
    private int scopeWidth;
    private String scopeID;
    private scopeTypeEnum scopeType;

    public void addChild(scopeClass child){
        this.childrens.add(child);
    }

    public scopeClass(scopeClass fatherNode, String scopeID, scopeTypeEnum scopeType) {
        symbolTable = new HashMap<String, symbolTableRow>();

        childrens = new ArrayList<>();
        this.fatherNode = fatherNode;
        this.scopeID = scopeID;
        this.scopeType = scopeType;
    }

    public HashMap<String, symbolTableRow> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(HashMap<String, symbolTableRow> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public scopeClass getFatherNode() {
        return fatherNode;
    }

    public void setFatherNode(scopeClass fatherNode) {
        this.fatherNode = fatherNode;
    }

    public ArrayList<scopeClass> getChildrens() {
        return childrens;
    }

    public void setChildrens(ArrayList<scopeClass> childrens) {
        this.childrens = childrens;
    }

    public int getScopeWidth() {
        return scopeWidth;
    }

    public void setScopeWidth(int scopeWidth) {
        this.scopeWidth = scopeWidth;
    }

    public String getScopeID() {
        return scopeID;
    }

    public void setScopeID(String scopeID) {
        this.scopeID = scopeID;
    }

    public scopeTypeEnum getScopeType() {
        return scopeType;
    }

    public void setScopeType(scopeTypeEnum scopeType) {
        this.scopeType = scopeType;
    }
}
