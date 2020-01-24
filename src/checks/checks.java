package checks;

import enums.Types;
import enums.scopeTypeEnum;
import lulu2_package.lulu2_grammerParser;
import scopes.scopeClass;
import scopes.symbolTableRow;

import java.util.HashMap;

public class checks {

    public static void checkRule1 (scopeClass gloablNode){

        boolean searched = false;
        scopeClass declare = null;

        if(gloablNode.getChildrens() != null) {
            for (scopeClass child : gloablNode.getChildrens()) {
                if (child.getScopeID().equals("declare")) {
                    declare = child;
                    break;
                }
            }
        }

        if(declare != null) {
            for (String str : declare.getSymbolTable().keySet()) {
                if (declare.getSymbolTable().get(str).getType() == Types.CLASS) {
                    searched = false;
                    for (scopeClass child : gloablNode.getChildrens()) {
                        if (child.getScopeType() == scopeTypeEnum.USER_DEFINE_TYPE) {
                            if (child.getScopeID().equals(str)) searched = true;
                        }
                    }

                    if (searched == false) System.err.println("ERROR: TYPE NAME IN DECLARE ISN'T USED");
                }
            }
        }

    }
    public static void chechRule9 (){}  //error antlr
    public static boolean checkRule11 (HashMap<String, symbolTableRow> currentST, String ID){
        if(currentST != null) {
            for (String key : currentST.keySet()) {
                if (ID.equals(key)) {
                    System.err.println("ERROR: REPETITIVE ID FOR VARIABLES");
                    return false;
                }
            }
        }
        return true;
    }
    public static void chechRule14 (){}  //dar listenner tarif shod enterExpr
    public static void chechRule15 (){}  //dar listenner tarif shod enterExpr




    //utility
    public static Types checkingPrimitiveType(scopeClass current,String id){
        if(current.getSymbolTable().get(id) != null) {
            if(current.getSymbolTable().get(id).getType() == Types.STRING ||
                    current.getSymbolTable().get(id).getType() == Types.INT ||
                    current.getSymbolTable().get(id).getType() == Types.FLOAT ||
                    current.getSymbolTable().get(id).getType() == Types.BOOL) return current.getSymbolTable().get(id).getType();
            else {
                System.err.println("ERROR: TYPE IN MATHEMATICAL AND COMPARATOR OPERATOR ISNT PRIMITIVE ");
                return null;
            }
        }else{
            if(current.getFatherNode() != null)
                return checkingPrimitiveType(current.getFatherNode(), id);
            else return null;
        }
    }
    public static Types gettingType(scopeClass current, String id){ //getting type from an id in symboltable and fathers
        if(current.getSymbolTable().get(id) != null) {
            return current.getSymbolTable().get(id).getType();
        }else{
            if(current.getFatherNode() != null) return gettingType(current.getFatherNode(), id);
            else return null;
        }
    }
    public static Types findExprType(lulu2_grammerParser.ExprContext expr, scopeClass current){
        if(expr.getChildCount() == 1){
            return gettingType(current, expr.var().ref(0).ID().getText());
        }else{
            Types type1, type2;
            type1 = findExprType(expr.expr(0), current);
            type2 = findExprType(expr.expr(1), current);

            int width1, width2;

            if(type1 == Types.INT) width1 = 4;
            else if(type1 == Types.FLOAT) width1 = 8;
            else if(type1 == Types.BOOL) width1 = 1;
            else if(type1 == Types.STRING) width1 = 9;
            else width1 = 4;

            if(type2 == Types.INT) width2 = 4;
            else if(type2 == Types.FLOAT) width2 = 8;
            else if(type2 == Types.BOOL) width2 = 1;
            else if(type2 == Types.STRING) width2 = 9;
            else width2 = 4;

            if(width1 >= width2) return type1;
            else if(width1 < width2) return type2;
            else return null;
        }
    }


}
