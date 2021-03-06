package lulu2_package;// Generated from D:/Intelli j/lulu2_final/src\lulu2_grammer.g4 by ANTLR 4.8

import com.sun.deploy.security.ValidationState;
import enums.AccessLabel;
import enums.Types;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import scopes.scopeClass;
import enums.scopeTypeEnum;
import scopes.scopeTree;
import scopes.symbolTableRow;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static checks.checks.checkingPrimitiveType1;
import static checks.checks.findExprType;
import static checks.checks.gettingType;
import static scopes.scopeTree.find;

public class lulu2_grammerBaseListener implements lulu2_grammerListener {

	scopeClass global = new scopeClass(null, "global", scopeTypeEnum.GLOBAL); //global scope
	scopeClass currentScope = global; 		//pointer to current scope
	scopeTree tree = new scopeTree(global);
	HashMap<String, symbolTableRow> currentSymbolTable = currentScope.getSymbolTable(); //pointer to current symbol table
	scopeClass temp_scopeClass;
	symbolTableRow temp_symbolTableRow;
	HashMap<String, Types> tempMap;
	int cnt = 1; //counter for getArguments

	HashMap<String, Types> function_arguments = new HashMap<>();
	HashMap<String, Types> function_parameters = new HashMap<>();


	@Override public void enterProgram(lulu2_grammerParser.ProgramContext ctx) { }
	@Override public void exitProgram(lulu2_grammerParser.ProgramContext ctx) {
		//print childs and fathers
//		tree.BFS(tree.startNode);

		//check rule 1
		checks.checks.checkRule1(global);

		boolean startFlag = false;
		for(String srt : global.getSymbolTable().keySet()){
			if(srt.equals("start")) startFlag = true;
		}

		if(startFlag != true) System.err.println("ERROR: COULD NOT FIND START FUNCTION ");
	}
	@Override public void enterFt_dcl(lulu2_grammerParser.Ft_dclContext ctx) {
		currentSymbolTable.put("declare",new symbolTableRow(Types.DECLARE));

		temp_scopeClass = new scopeClass(currentScope, "declare", scopeTypeEnum.DECLARE);
		currentScope.addChild(temp_scopeClass);
		currentScope = temp_scopeClass;
		currentSymbolTable = temp_scopeClass.getSymbolTable();
	}
	@Override public void exitFt_dcl(lulu2_grammerParser.Ft_dclContext ctx) {
		currentScope = currentScope.getFatherNode();
		currentSymbolTable = currentScope.getSymbolTable();
	}

	//------------------------------------------------------------------------------------------
	public void getParameters(lulu2_grammerParser.ArgsContext ttx){
		Types parameterType = null;
		if(ttx.type().getText().equals("int")) parameterType = Types.INT;
		else if(ttx.type().getText().equals("bool")) parameterType = Types.BOOL;
		else if(ttx.type().getText().equals("string")) parameterType = Types.STRING;
		else if(ttx.type().getText().equals("float")) parameterType = Types.FLOAT;       //array missing!

		tempMap.put("arg"+ cnt++,parameterType);

		if(ttx.getChild(1) != null){
			if(ttx.getChild(1).getText().equals(","))
				getParameters(ttx.args());
		}
	}

	public void getArguments(lulu2_grammerParser.Args_varContext ttx){
		Types parameterType = null;
		if(ttx.type().getText().equals("int")) parameterType = Types.INT;
		else if(ttx.type().getText().equals("bool")) parameterType = Types.BOOL;
		else if(ttx.type().getText().equals("string")) parameterType = Types.STRING;
		else if(ttx.type().getText().equals("float")) parameterType = Types.FLOAT;       //array missing!

		tempMap.put(ttx.ID().getText() ,parameterType);

		if(ttx.getChild(1) != null){
			if(ttx.getChild(1).getText().equals(","))
				getArguments(ttx.args_var());
		}
	}
	//------------------------------------------------------------------------------------------

	@Override public void enterFunc_dcl(lulu2_grammerParser.Func_dclContext ctx) {
		currentSymbolTable = currentScope.getSymbolTable();
		HashMap<String, Types> temp_arguments = null;
		HashMap<String, Types> temp_parameters;

		//setting parameters
		tempMap = new HashMap<String, Types>();
		getParameters(ctx.args(0));
		temp_parameters = tempMap;


		//setting arguments
		if(ctx.args(1) != null){
			tempMap = new HashMap<String, Types>();
			getParameters(ctx.args(1));
			temp_arguments = tempMap;
		}
		if(ctx.args_var() != null){
			tempMap = new HashMap<String, Types>();
			getArguments(ctx.args_var());
			temp_arguments = tempMap;
		}
		if(ctx.args_var() == null && ctx.args(1) == null){
			temp_arguments = null;
		}

		currentSymbolTable.put(ctx.ID().getText(),new symbolTableRow(Types.FUNCTION, temp_arguments, temp_parameters));
	}
	@Override public void exitFunc_dcl(lulu2_grammerParser.Func_dclContext ctx) { }
	@Override public void enterArgs(lulu2_grammerParser.ArgsContext ctx) { }
	@Override public void exitArgs(lulu2_grammerParser.ArgsContext ctx) { }
	@Override public void enterArgs_var(lulu2_grammerParser.Args_varContext ctx) { }
	@Override public void exitArgs_var(lulu2_grammerParser.Args_varContext ctx) { }
	@Override public void enterType_dcl(lulu2_grammerParser.Type_dclContext ctx) {
		currentSymbolTable = currentScope.getSymbolTable();
		currentSymbolTable.put(ctx.ID().getText(),new symbolTableRow(Types.CLASS));
	}
	@Override public void exitType_dcl(lulu2_grammerParser.Type_dclContext ctx) { }
	@Override public void enterVar_def(lulu2_grammerParser.Var_defContext ctx) {
		currentSymbolTable = currentScope.getSymbolTable();
		Types type;
		int width;
		ArrayList<Integer> dimention = new ArrayList<>();

		if(ctx.type().getText().equals("int")){
			type = Types.INT;
			width = 4;
		}
		else if(ctx.type().getText().equals("bool")){
			type = Types.BOOL;
			width = 1;
		}
		else if(ctx.type().getText().equals("string")){
			type = Types.STRING;
			width = 0;
		}
		else if(ctx.type().getText().equals("float")){
			type = Types.FLOAT;
			width = 8;
		}else {
			type = Types.USER_DEFINED;
			width = 4;
		}

		String temp = ctx.var_val(0).ref().getText();
		for (int i = 0 ; i<temp.length() ; i++){
			if(temp.charAt(i) == '['){
				dimention.add(Integer.valueOf(temp.charAt(++i)));
			}
		}

		if(checks.checks.checkRule11(currentSymbolTable, ctx.var_val(0).ref().ID().getText()))
			currentSymbolTable.put(ctx.var_val(0).ref().ID().getText(),new symbolTableRow(type,width,AccessLabel.NULL,dimention));

		//baraye chand tarifi:
		String number = "";
		if(ctx.var_val().size() != 1){
			for (int j=1; j<ctx.var_val().size() ; j++){
				dimention.clear();
				number = "";
				temp = ctx.var_val(j).ref().getText();
				for (int i = 0 ; i<temp.length() ; i++){
					if(temp.charAt(i) == '['){
						int h = i+1;
						while (temp.charAt(h) != ']'){
							number = number + temp.charAt(h);
							h++;
						}
						dimention.add(Integer.valueOf(temp.charAt(++i)));
					}
				}
				if(checks.checks.checkRule11(currentSymbolTable, ctx.var_val(0).ref().ID().getText()))
					currentSymbolTable.put(ctx.var_val(j).ref().ID().getText(),new symbolTableRow(type,width,AccessLabel.NULL,dimention));
			}
		}

		if(ctx.var_val(0).expr() != null){
			Types type1=null, type2=null;
			if(ctx.type().getText().equals("int")) type1 = Types.INT;
			if(ctx.type().getText().equals("string")) type1 = Types.STRING;
			if(ctx.type().getText().equals("bool")) type1 = Types.BOOL;
			if(ctx.type().getText().equals("float")) type1 = Types.FLOAT;
			if(ctx.type().ID() != null) type1 = Types.USER_DEFINED;

			//type2:
			if(ctx.var_val(0).expr().op1() != null || ctx.var_val(0).expr().op2() != null || ctx.var_val(0).expr().op3() != null || ctx.var_val(0).expr().op4() != null || ctx.var_val(0).expr().logical() != null || ctx.var_val(0).expr().bitwise() != null){
				type2 = findExprType(ctx.var_val(0).expr(), currentScope);
			}else if(ctx.var_val(0).expr().const_val() != null){
				if(ctx.var_val(0).expr().const_val().INT_CONST() != null) type2 = Types.INT;
				else if(ctx.var_val(0).expr().const_val().BOOL_CONST() != null) type2 = Types.BOOL;
				else if(ctx.var_val(0).expr().const_val().STRING_CONST() != null) type2 = Types.STRING;
				else if(ctx.var_val(0).expr().const_val().REAL_CONST() != null) type2 = Types.FLOAT;
			}else if(ctx.var_val(0).expr().getText().charAt(0) == '('){
				type2 = findExprType(ctx.var_val(0).expr().expr(0), currentScope);
			}else if(ctx.var_val(0).expr().var() != null){
				type2 = gettingType(currentScope, ctx.var_val(0).expr().var().ref(0).ID().getText());
			}

			if(type1 == type2) ;
			else if(type2 == Types.INT && (type1==Types.BOOL || type1==Types.FLOAT || type1==Types.STRING)) ;
			else if(type2 == Types.BOOL && type1 == Types.INT);
			else if(type2 == Types.USER_DEFINED && type1 == Types.INT);
			else System.err.println("ERROR(TYPE): TYPES IN ASSIGN IS NOT MATCHED OR CONVERTABLE");
		}
	}
	@Override public void exitVar_def(lulu2_grammerParser.Var_defContext ctx) { }
	@Override public void enterVar_val(lulu2_grammerParser.Var_valContext ctx) { }
	@Override public void exitVar_val(lulu2_grammerParser.Var_valContext ctx) { }
	@Override public void enterFt_def(lulu2_grammerParser.Ft_defContext ctx) { }
	@Override public void exitFt_def(lulu2_grammerParser.Ft_defContext ctx) { }
	@Override public void enterType_def(lulu2_grammerParser.Type_defContext ctx) {
		currentSymbolTable = currentScope.getSymbolTable();
		currentSymbolTable.put(ctx.ID().get(0).getText(), new symbolTableRow(Types.CLASS));

		temp_scopeClass = new scopeClass(currentScope, ctx.getChild(1).getText(), scopeTypeEnum.USER_DEFINE_TYPE);
		currentScope.addChild(temp_scopeClass);
		currentScope = temp_scopeClass;
		currentSymbolTable = temp_scopeClass.getSymbolTable();
	}
	@Override public void exitType_def(lulu2_grammerParser.Type_defContext ctx) {

		int width_included = 0;

		for(symbolTableRow str : currentScope.getSymbolTable().values()){
			if(str.getType() == Types.INT) width_included += 4;
			else if(str.getType() == Types.FLOAT) width_included += 8;
			else if(str.getType() == Types.BOOL) width_included += 1;
			else if(str.getType() == Types.STRING) width_included += 0;
			else if(str.getType() == Types.USER_DEFINED) width_included += 4;
			else if(str.getType() == Types.FUNCTION) width_included += str.getWidth();
		}

		currentScope.setScopeWidth(width_included + 10);

		currentScope = currentScope.getFatherNode();
		currentSymbolTable = currentScope.getSymbolTable();
	}
	@Override public void enterComponent(lulu2_grammerParser.ComponentContext ctx) { }
	@Override public void exitComponent(lulu2_grammerParser.ComponentContext ctx) { }
	@Override public void enterAccess_modifier(lulu2_grammerParser.Access_modifierContext ctx) { }
	@Override public void exitAccess_modifier(lulu2_grammerParser.Access_modifierContext ctx) { }
	@Override public void enterFun_def(lulu2_grammerParser.Fun_defContext ctx) {
		currentSymbolTable = currentScope.getSymbolTable();
		HashMap<String, Types> temp_arguments;
		HashMap<String, Types> temp_parameters= null;

		//set arguments
		if(ctx.args_var(1) != null){
			tempMap = new HashMap<String, Types>();
			getArguments(ctx.args_var(1));
			temp_arguments = tempMap;
		}else {
			temp_arguments = null;
		}

		//set parameters
		if(ctx.args_var(0) != null) {                    //notice that this returns for us parameters. but we use grtArguments;
			tempMap = new HashMap<String, Types>();
			getArguments(ctx.args_var(0));
			temp_parameters = tempMap;
		}else if (ctx.args_var(0) != null){
			temp_parameters = null;
		}

		if(temp_arguments != null) {
			for (String key : temp_arguments.keySet()) {
				if (temp_arguments.get(key) == Types.INT)
					function_arguments.put(key, temp_arguments.get(key));
				else if (temp_arguments.get(key) == Types.FLOAT)
					function_arguments.put(key, temp_arguments.get(key));
				else if (temp_arguments.get(key) == Types.BOOL)
					function_arguments.put(key, temp_arguments.get(key));
				else if (temp_arguments.get(key) == Types.STRING)
					function_arguments.put(key, temp_arguments.get(key));
				else if (temp_arguments.get(key) == Types.USER_DEFINED)
					function_arguments.put(key, temp_arguments.get(key));
			}
		}

		if(temp_parameters != null) {
			for (String key : temp_parameters.keySet()) {
				if (temp_parameters.get(key) == Types.INT)
					function_parameters.put(key, temp_parameters.get(key));
				else if (temp_parameters.get(key) == Types.FLOAT)
					function_parameters.put(key, temp_parameters.get(key));
				else if (temp_parameters.get(key) == Types.BOOL)
					function_parameters.put(key, temp_parameters.get(key));
				else if (temp_parameters.get(key) == Types.STRING)
					function_parameters.put(key, temp_parameters.get(key));
				else if (temp_parameters.get(key) == Types.USER_DEFINED)
					function_parameters.put(key, temp_parameters.get(key));
			}
		}

		currentSymbolTable.put(ctx.ID().getText(), new symbolTableRow(Types.FUNCTION, temp_arguments, temp_parameters)); // here we create a row in current symboltable

	}
	@Override public void exitFun_def(lulu2_grammerParser.Fun_defContext ctx) {
		int width_arg = 0;
		int width_parameters = 0;
		int width_included = 0;

		symbolTableRow temp = currentScope.getFatherNode().getSymbolTable().get(ctx.ID().getText());

		//calculate args width
		if(temp.getArguments() != null) {
			for (Types type : temp.getArguments().values()) {
				if (type == Types.INT) width_arg += 4;
				else if (type == Types.FLOAT) width_arg += 8;
				else if (type == Types.BOOL) width_arg += 1;
				else if (type == Types.STRING) width_arg += 0;
				else if (type == Types.USER_DEFINED) width_arg += 4;
			}
		}

		//calculate params width
		if(temp.getParameters() != null) {
			for (Types type : temp.getParameters().values()) {
				if (type == Types.INT) width_parameters += 4;
				else if (type == Types.FLOAT) width_parameters += 8;
				else if (type == Types.BOOL) width_parameters += 1;
				else if (type == Types.STRING) width_parameters += 0;
				else if (type == Types.USER_DEFINED) width_parameters += 4;
			}
		}

		//calculate included width
		if(currentScope != null) {
			for (symbolTableRow str : currentScope.getSymbolTable().values()) {
				if (str.getType() == Types.INT) width_included += 4;
				else if (str.getType() == Types.FLOAT) width_included += 8;
				else if (str.getType() == Types.BOOL) width_included += 1;
				else if (str.getType() == Types.STRING) width_included += 0;
				else if (str.getType() == Types.USER_DEFINED) width_included += 4;
			}
		}

		currentScope.setScopeWidth(width_arg + width_included + width_parameters);

		currentScope = currentScope.getFatherNode();
		currentSymbolTable = currentScope.getSymbolTable();
	}
	@Override public void enterBlock(lulu2_grammerParser.BlockContext ctx) {
		String parentName = ctx.getParent().getClass().getSimpleName().toLowerCase().replace("context","");

		if(parentName.equals("fun_def")) {
			if(ctx.getParent().getChildCount() == 10){
				temp_scopeClass = new scopeClass(currentScope, ctx.getParent().getChild(5).getText(), scopeTypeEnum.FUNCTION);
				currentScope.addChild(temp_scopeClass);
				currentScope = temp_scopeClass;
				currentSymbolTable = temp_scopeClass.getSymbolTable();
			}
			else if(ctx.getParent().getChildCount() == 6) {
				temp_scopeClass = new scopeClass(currentScope, ctx.getParent().getChild(1).getText(), scopeTypeEnum.FUNCTION);
				currentScope.addChild(temp_scopeClass);
				currentScope = temp_scopeClass;
				currentSymbolTable = temp_scopeClass.getSymbolTable();
			}
			else if(ctx.getParent().getChildCount() == 9) {
				temp_scopeClass = new scopeClass(currentScope, ctx.getParent().getChild(5).getText(), scopeTypeEnum.FUNCTION);
				currentScope.addChild(temp_scopeClass);
				currentScope = temp_scopeClass;
				currentSymbolTable = temp_scopeClass.getSymbolTable();
			}
			else if(ctx.getParent().getChildCount() == 5) {
				temp_scopeClass = new scopeClass(currentScope, ctx.getParent().getChild(1).getText(), scopeTypeEnum.FUNCTION);
				currentScope.addChild(temp_scopeClass);
				currentScope = temp_scopeClass;
				currentSymbolTable = temp_scopeClass.getSymbolTable();
			}
		}
		else if(parentName.equals("cond_stmt")) {
			temp_scopeClass = new scopeClass(currentScope, "if", scopeTypeEnum.OTHERS);
			currentScope.addChild(temp_scopeClass);
			currentScope = temp_scopeClass;
			currentSymbolTable = temp_scopeClass.getSymbolTable();
		}
		else if(parentName.equals("switch_body")) {
			temp_scopeClass = new scopeClass(currentScope, "switch", scopeTypeEnum.OTHERS);
			currentScope.addChild(temp_scopeClass);
			currentScope = temp_scopeClass;
			currentSymbolTable = temp_scopeClass.getSymbolTable();
		}
		else if(parentName.equals("loop_stmt"))
			; //chon bayad motoghayere shomarandeh ro be jadval add kard dar loop_stmt gharar gerefteh




		if(ctx.getParent().getClass().getSimpleName().toLowerCase().replace("context","").equals("fun_def")){
			if(function_arguments != null) {
				for (String key : function_arguments.keySet()) {
					if (function_arguments.get(key) == Types.INT)
					currentSymbolTable.put(key , new symbolTableRow(function_arguments.get(key), 4, AccessLabel.NULL, null));
				else if (function_arguments.get(key) == Types.FLOAT)
					currentSymbolTable.put(key , new symbolTableRow(function_arguments.get(key), 8, AccessLabel.NULL, null));
				else if (function_arguments.get(key) == Types.BOOL)
					currentSymbolTable.put(key , new symbolTableRow(function_arguments.get(key), 1, AccessLabel.NULL, null));
				else if (function_arguments.get(key) == Types.STRING)
					currentSymbolTable.put(key , new symbolTableRow(function_arguments.get(key), 0, AccessLabel.NULL, null));
				else if (function_arguments.get(key) == Types.USER_DEFINED)
					currentSymbolTable.put(key , new symbolTableRow(function_arguments.get(key), 4, AccessLabel.NULL, null));
				}
			}

			// add parameters
			if(function_parameters != null) {
				for (String key : function_parameters.keySet()) {
					if (function_parameters.get(key) == Types.INT)
						currentSymbolTable.put(key, new symbolTableRow(function_parameters.get(key), 4, AccessLabel.NULL, null));
					else if (function_parameters.get(key) == Types.FLOAT)
						currentSymbolTable.put(key, new symbolTableRow(function_parameters.get(key), 8, AccessLabel.NULL, null));
					else if (function_parameters.get(key) == Types.BOOL)
						currentSymbolTable.put(key, new symbolTableRow(function_parameters.get(key), 1, AccessLabel.NULL, null));
					else if (function_parameters.get(key) == Types.STRING)
						currentSymbolTable.put(key, new symbolTableRow(function_parameters.get(key), 0, AccessLabel.NULL, null));
					else if (function_parameters.get(key) == Types.USER_DEFINED)
						currentSymbolTable.put(key, new symbolTableRow(function_parameters.get(key), 4, AccessLabel.NULL, null));
				}
			}

			function_parameters.clear();
			function_arguments.clear();
		}

	}
	@Override public void exitBlock(lulu2_grammerParser.BlockContext ctx) {
		if(ctx.getParent().getClass().getSimpleName().toLowerCase().replace("context","").equals("switch_body")){
			currentScope = currentScope.getFatherNode();
			currentSymbolTable = currentScope.getSymbolTable();
		}

		if(ctx.getParent().getClass().getSimpleName().toLowerCase().replace("context","").equals("cond_stmt")){
			currentScope = currentScope.getFatherNode();
			currentSymbolTable = currentScope.getSymbolTable();
		}
	}
	@Override public void enterStmt(lulu2_grammerParser.StmtContext ctx) {
		String str = ctx.getText().toLowerCase().replace(";","");

		//SWITCH
		if(str.equals("break")) {
			if (find(currentScope, "switch") || find(currentScope, "loop")) ;
			else System.err.println("ERROR: BREAK SHOULD BE IN THE SWITCH OR LOOP ");
		}

		//LOOP
		if(str.equals("continue")){
			if (!find(currentScope, "loop")) System.err.println("ERROR: CONTINUE SHOULD BE IN THE LOOP");
		}

		if(ctx.ID() != null){
			Types types = gettingType(currentScope, ctx.ID().getText());
			if(types == null) System.err.println("ERROR: DESTRUCT HAS NOT ON USER DEFINE TYPE");
			else{
				if(types != Types.USER_DEFINED) System.err.println("ERROR: DESTRUCT HAS NOT ON USER DEFINE TYPE");
			}
		}

	}
	@Override public void exitStmt(lulu2_grammerParser.StmtContext ctx) { }
	@Override public void enterAssign(lulu2_grammerParser.AssignContext ctx) {
		Types type1=null, type2=null;
		type1 = gettingType(currentScope, ctx.var(0).ref(0).ID().getText());

		//type2:
		if(ctx.expr().op1() != null || ctx.expr().op2() != null || ctx.expr().op3() != null || ctx.expr().op4() != null || ctx.expr().logical() != null || ctx.expr().bitwise() != null){
			type2 = findExprType(ctx.expr(), currentScope);
		}else if(ctx.expr().const_val() != null){
			if(ctx.expr().const_val().INT_CONST() != null) type2 = Types.INT;
			else if(ctx.expr().const_val().BOOL_CONST() != null) type2 = Types.BOOL;
			else if(ctx.expr().const_val().STRING_CONST() != null) type2 = Types.STRING;
			else if(ctx.expr().const_val().REAL_CONST() != null) type2 = Types.FLOAT;
		}else if(ctx.expr().getText().charAt(0) == '('){
			type2 = findExprType(ctx.expr().expr(0), currentScope);
		}else if(ctx.expr().var() != null){
			type2 = gettingType(currentScope, ctx.expr().var().ref(0).ID().getText());
		}

		if(type1 == type2) ;
		else if(type2 == Types.INT && (type1==Types.BOOL || type1==Types.FLOAT || type1==Types.STRING)) ;
		else if(type2 == Types.BOOL && type1 == Types.INT);
		else if(type2 == Types.USER_DEFINED && type1 == Types.INT);
		else System.err.println("ERROR(TYPE): TYPES IN ASSIGN IS NOT MATCHED OR CONVERTABLE");

	}
	@Override public void exitAssign(lulu2_grammerParser.AssignContext ctx) { }
	@Override public void enterVar(lulu2_grammerParser.VarContext ctx) { }
	@Override public void exitVar(lulu2_grammerParser.VarContext ctx) { }
	@Override public void enterRef(lulu2_grammerParser.RefContext ctx) { }
	@Override public void exitRef(lulu2_grammerParser.RefContext ctx) { }
	@Override public void enterExpr(lulu2_grammerParser.ExprContext ctx) {
		Types type1 = null, type2 = null;

		if(ctx.op1() != null || ctx.op2() != null || ctx.op3() != null || ctx.op4() != null) {
			if ((ctx.expr(0).var().ref(0).ID() != null) && (ctx.expr(1).const_val() != null)) {
				if (!checkingPrimitiveType1(ctx.expr(0), currentScope))
					System.err.println("ERROR: TYPE IN MATHEMATICAL AND COMPARATOR OPERATOR ISNT PRIMITIVE ");
				else {
					type1 = gettingType(currentScope, ctx.expr(0).var().ref(0).ID().getText());
					type2 = findExprType(ctx.expr(1), currentScope);
				}
			} else if ((ctx.expr(0).var().ref(0).ID() != null) && (ctx.expr(1).var().ref(0).ID() != null)) {
				if (!(checkingPrimitiveType1(ctx.expr(0), currentScope) && checkingPrimitiveType1(ctx.expr(1), currentScope)))
					System.err.println("ERROR: TYPE IN MATHEMATICAL AND COMPARATOR OPERATOR ISNT PRIMITIVE ");
				else {
					type1 = gettingType(currentScope, ctx.expr(0).var().ref(0).ID().getText());
					type2 = gettingType(currentScope, ctx.expr(1).var().ref(0).ID().getText());
				}
			} else if ((ctx.expr(0).const_val() != null) && (ctx.expr(1).var().ref(0).ID() != null)) {
				if (!checkingPrimitiveType1(ctx.expr(1), currentScope))
					System.err.println("ERROR: TYPE IN MATHEMATICAL AND COMPARATOR OPERATOR ISNT PRIMITIVE ");
				else {
					type2 = gettingType(currentScope, ctx.expr(1).var().ref(0).ID().getText());
					type1 = findExprType(ctx.expr(0), currentScope);
				}
			} else if ((ctx.expr(0).const_val() != null) && (ctx.expr(1).const_val() != null)) {
				type1 = findExprType(ctx.expr(0), currentScope);
				type2 = findExprType(ctx.expr(1), currentScope);
			}

			if (type1 == type2) ; //yeksan boodan
			else if (type2 == Types.INT && (type1 == Types.BOOL || type1 == Types.FLOAT || type1 == Types.STRING)) ;
			else if (type2 == Types.BOOL && type1 == Types.INT) ;
			else if (type2 == Types.USER_DEFINED && type1 == Types.INT) ;
			else if (type1 == Types.INT && (type2 == Types.BOOL || type2 == Types.FLOAT || type2 == Types.STRING)) ;
			else if (type1 == Types.BOOL && type2 == Types.INT) ;
			else if (type1 == Types.USER_DEFINED && type2 == Types.INT) ;
			else System.err.println("ERROR(TYPE): TYPES IN ASSIGN IS NOT MATCHED OR CONVERTABLE");
		}else if(ctx.bitwise() != null ) {
			type1 = findExprType(ctx.expr(0), currentScope);
			type2 = findExprType(ctx.expr(1), currentScope);

			if (type1 == Types.INT && type2 == Types.INT) ;
			else if ((type1 == Types.BOOL && type2 == Types.INT) || (type2 == Types.BOOL && type1 == Types.INT)) ;
			else if ((type1 == Types.USER_DEFINED && type2 == Types.INT) || (type2 == Types.USER_DEFINED && type1 == Types.INT))
				;
			else if ((type1 == Types.USER_DEFINED && type2 == Types.BOOL) || (type2 == Types.USER_DEFINED && type1 == Types.BOOL))
				;
			else System.err.println("ERROR(TYPE): TYPES ARE NOT INT OR CONVETABLE TO INT");
		}else if(ctx.logical() != null){
			type1 = findExprType(ctx.expr(0), currentScope);
			type2 = findExprType(ctx.expr(1), currentScope);

			if(type1 == Types.BOOL && type2 == Types.BOOL) ;
			else if((type1 == Types.BOOL && type2 == Types.INT) || (type2 == Types.BOOL && type1 == Types.INT) ) ;
			else System.err.println("ERROR(TYPE): TYPES ARE NOT INT OR CONVETABLE TO BOOL");
		}
	}
	@Override public void exitExpr(lulu2_grammerParser.ExprContext ctx) { }
	@Override public void enterFunc_call(lulu2_grammerParser.Func_callContext ctx) { }
	@Override public void exitFunc_call(lulu2_grammerParser.Func_callContext ctx) { }
	@Override public void enterList(lulu2_grammerParser.ListContext ctx) { }
	@Override public void exitList(lulu2_grammerParser.ListContext ctx) { }
	@Override public void enterHandle_call(lulu2_grammerParser.Handle_callContext ctx) { }
	@Override public void exitHandle_call(lulu2_grammerParser.Handle_callContext ctx) { }
	@Override public void enterParams(lulu2_grammerParser.ParamsContext ctx) { }
	@Override public void exitParams(lulu2_grammerParser.ParamsContext ctx) { }
	@Override public void enterCond_stmt(lulu2_grammerParser.Cond_stmtContext ctx) {
		Types type = null;
		if(ctx.expr() != null){
			if(ctx.expr().getText().charAt(0) == '(') {
				type = findExprType(ctx.expr().expr(0), currentScope);
			}
			else type = findExprType(ctx.expr(), currentScope);

			if(type == Types.BOOL || type == Types.INT);
			else System.err.println("ERROR: CONDITION IN IF STATEMENT SHOULD BE BOOL OR INT ");
		}else if(ctx.var() != null){
			type = gettingType(currentScope, ctx.var().ref(0).ID().getText());
			if(type == Types.INT || type == Types.BOOL) ;
			else System.err.println("ERROR: CONDITION IN IF STATEMENT SHOULD BE BOOL OR INT ");
		}
	}
	@Override public void exitCond_stmt(lulu2_grammerParser.Cond_stmtContext ctx) { }
	@Override public void enterSwitch_body(lulu2_grammerParser.Switch_bodyContext ctx) { }
	@Override public void exitSwitch_body(lulu2_grammerParser.Switch_bodyContext ctx) { }
	@Override public void enterLoop_stmt(lulu2_grammerParser.Loop_stmtContext ctx) {
		temp_scopeClass = new scopeClass(currentScope, "loop", scopeTypeEnum.OTHERS);
		currentScope.addChild(temp_scopeClass);
		currentScope = temp_scopeClass;
		currentSymbolTable = temp_scopeClass.getSymbolTable();

		if(ctx.type() != null){
			Types type;
			int width;
			ArrayList<Integer> dimention = new ArrayList<>();

			if(ctx.type().getText().equals("int")){
				type = Types.INT;
				width = 4;
			}
			else if(ctx.type().getText().equals("bool")){
				type = Types.BOOL;
				width = 1;
			}
			else if(ctx.type().getText().equals("string")){
				type = Types.STRING;
				width = 0;
			}
			else if(ctx.type().getText().equals("float")){
				type = Types.FLOAT;
				width = 8;
			}else {
				type = Types.USER_DEFINED;
				width = 4;
			}

			currentSymbolTable.put(ctx.assign(0).var(0).ref(0).ID().getText(),new symbolTableRow(type,width,AccessLabel.NULL,dimention));


			//checking type of conditional stmt
			Types type1 = null;
			if(ctx.expr().getText().charAt(0) == '(') {
				type1 = findExprType(ctx.expr().expr(0), currentScope);
			}
			else {
				type1 = findExprType(ctx.expr(), currentScope);
			}

			if(type1 == Types.BOOL || type1 == Types.INT);
			else System.err.println("ERROR: CONDITION IN IF STATEMENT SHOULD BE BOOL OR INT ");

		}
	}
	@Override public void exitLoop_stmt(lulu2_grammerParser.Loop_stmtContext ctx) {
		currentScope = currentScope.getFatherNode();
		currentSymbolTable = currentScope.getSymbolTable();
	}
	@Override public void enterType(lulu2_grammerParser.TypeContext ctx) { }
	@Override public void exitType(lulu2_grammerParser.TypeContext ctx) { }
	@Override public void enterConst_val(lulu2_grammerParser.Const_valContext ctx) { }
	@Override public void exitConst_val(lulu2_grammerParser.Const_valContext ctx) { }
	@Override public void enterUnary_op(lulu2_grammerParser.Unary_opContext ctx) { }
	@Override public void exitUnary_op(lulu2_grammerParser.Unary_opContext ctx) { }
	@Override public void enterOp1(lulu2_grammerParser.Op1Context ctx) { }
	@Override public void exitOp1(lulu2_grammerParser.Op1Context ctx) { }
	@Override public void enterOp2(lulu2_grammerParser.Op2Context ctx) { }
	@Override public void exitOp2(lulu2_grammerParser.Op2Context ctx) { }
	@Override public void enterOp3(lulu2_grammerParser.Op3Context ctx) { }
	@Override public void exitOp3(lulu2_grammerParser.Op3Context ctx) { }
	@Override public void enterOp4(lulu2_grammerParser.Op4Context ctx) { }
	@Override public void exitOp4(lulu2_grammerParser.Op4Context ctx) { }
	@Override public void enterBitwise(lulu2_grammerParser.BitwiseContext ctx) { }
	@Override public void exitBitwise(lulu2_grammerParser.BitwiseContext ctx) { }
	@Override public void enterLogical(lulu2_grammerParser.LogicalContext ctx) { }
	@Override public void exitLogical(lulu2_grammerParser.LogicalContext ctx) { }
	@Override public void enterEveryRule(ParserRuleContext ctx) { }
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	@Override public void visitTerminal(TerminalNode node) { }
	@Override public void visitErrorNode(ErrorNode node) { }
}