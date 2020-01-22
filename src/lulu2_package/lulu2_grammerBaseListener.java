package lulu2_package;// Generated from D:/Intelli j/lulu2_final/src\lulu2_grammer.g4 by ANTLR 4.8

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

public class lulu2_grammerBaseListener implements lulu2_grammerListener {

	scopeClass global = new scopeClass(null, "global", scopeTypeEnum.GLOBAL); //global scope
	scopeClass currentScope = global; 		//pointer to current scope
	scopeTree tree = new scopeTree(global);
	HashMap<String, symbolTableRow> currentSymbolTable = currentScope.getSymbolTable(); //pointer to current symbol table
	scopeClass temp_scopeClass;
	symbolTableRow temp_symbolTableRow;
	HashMap<String, Types> tempMap;
	int cnt = 1; //counter for getArguments


	@Override public void enterProgram(lulu2_grammerParser.ProgramContext ctx) { }
	@Override public void exitProgram(lulu2_grammerParser.ProgramContext ctx) { }
	@Override public void enterFt_dcl(lulu2_grammerParser.Ft_dclContext ctx) {
		temp_scopeClass = new scopeClass(currentScope, "decalre", scopeTypeEnum.DECLARE);
		currentScope.addChild(temp_scopeClass);
		currentScope = temp_scopeClass;
		currentSymbolTable = temp_scopeClass.getSymbolTable();
	}
	@Override public void exitFt_dcl(lulu2_grammerParser.Ft_dclContext ctx) { }

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
	@Override public void enterType_dcl(lulu2_grammerParser.Type_dclContext ctx) { }
	@Override public void exitType_dcl(lulu2_grammerParser.Type_dclContext ctx) { }
	@Override public void enterVar_def(lulu2_grammerParser.Var_defContext ctx) { }
	@Override public void exitVar_def(lulu2_grammerParser.Var_defContext ctx) { }
	@Override public void enterVar_val(lulu2_grammerParser.Var_valContext ctx) { }
	@Override public void exitVar_val(lulu2_grammerParser.Var_valContext ctx) { }
	@Override public void enterFt_def(lulu2_grammerParser.Ft_defContext ctx) { }
	@Override public void exitFt_def(lulu2_grammerParser.Ft_defContext ctx) { }
	@Override public void enterType_def(lulu2_grammerParser.Type_defContext ctx) {
		temp_scopeClass = new scopeClass(currentScope, ctx.getChild(1).getText(), scopeTypeEnum.USER_DEFINE_TYPE);
		currentScope.addChild(temp_scopeClass);
		currentScope = temp_scopeClass;
		currentSymbolTable = temp_scopeClass.getSymbolTable();
	}
	@Override public void exitType_def(lulu2_grammerParser.Type_defContext ctx) { }
	@Override public void enterComponent(lulu2_grammerParser.ComponentContext ctx) { }
	@Override public void exitComponent(lulu2_grammerParser.ComponentContext ctx) { }
	@Override public void enterAccess_modifier(lulu2_grammerParser.Access_modifierContext ctx) { }
	@Override public void exitAccess_modifier(lulu2_grammerParser.Access_modifierContext ctx) { }
	@Override public void enterFun_def(lulu2_grammerParser.Fun_defContext ctx) { }
	@Override public void exitFun_def(lulu2_grammerParser.Fun_defContext ctx) { }
	@Override public void enterBlock(lulu2_grammerParser.BlockContext ctx) {
		String parentName = ctx.getParent().getClass().getSimpleName().toLowerCase().replace("context","");

		if(parentName.equals("fun_def")) {
			if(ctx.getParent().getChildCount() == 10)
				temp_scopeClass = new scopeClass(currentScope, ctx.getParent().getChild(5).getText(), scopeTypeEnum.FUNCTION);
			else if(ctx.getParent().getChildCount() == 6)
				temp_scopeClass = new scopeClass(currentScope, ctx.getParent().getChild(1).getText(), scopeTypeEnum.FUNCTION);
			else if(ctx.getParent().getChildCount() == 9)
				temp_scopeClass = new scopeClass(currentScope, ctx.getParent().getChild(5).getText(), scopeTypeEnum.FUNCTION);
			else if(ctx.getParent().getChildCount() == 5)
				temp_scopeClass = new scopeClass(currentScope, ctx.getParent().getChild(1).getText(), scopeTypeEnum.FUNCTION);
		}
		else if(parentName.equals("cond_stmt"))
			temp_scopeClass = new scopeClass(currentScope, "if", scopeTypeEnum.OTHERS);
		else if(parentName.equals("switch_body"))
			temp_scopeClass = new scopeClass(currentScope, "switch", scopeTypeEnum.OTHERS);
		else if(parentName.equals("loop_stmt"))
			; //chon bayad motoghayere shomarandeh ro be jadval add kard dar loop_stmt gharar gerefteh

		currentScope.addChild(temp_scopeClass);
		currentScope = temp_scopeClass;
		currentSymbolTable = temp_scopeClass.getSymbolTable();
	}
	@Override public void exitBlock(lulu2_grammerParser.BlockContext ctx) { }
	@Override public void enterStmt(lulu2_grammerParser.StmtContext ctx) { }
	@Override public void exitStmt(lulu2_grammerParser.StmtContext ctx) { }
	@Override public void enterAssign(lulu2_grammerParser.AssignContext ctx) { }
	@Override public void exitAssign(lulu2_grammerParser.AssignContext ctx) { }
	@Override public void enterVar(lulu2_grammerParser.VarContext ctx) { }
	@Override public void exitVar(lulu2_grammerParser.VarContext ctx) { }
	@Override public void enterRef(lulu2_grammerParser.RefContext ctx) { }
	@Override public void exitRef(lulu2_grammerParser.RefContext ctx) { }
	@Override public void enterExpr(lulu2_grammerParser.ExprContext ctx) { }
	@Override public void exitExpr(lulu2_grammerParser.ExprContext ctx) { }
	@Override public void enterFunc_call(lulu2_grammerParser.Func_callContext ctx) { }
	@Override public void exitFunc_call(lulu2_grammerParser.Func_callContext ctx) { }
	@Override public void enterList(lulu2_grammerParser.ListContext ctx) { }
	@Override public void exitList(lulu2_grammerParser.ListContext ctx) { }
	@Override public void enterHandle_call(lulu2_grammerParser.Handle_callContext ctx) { }
	@Override public void exitHandle_call(lulu2_grammerParser.Handle_callContext ctx) { }
	@Override public void enterParams(lulu2_grammerParser.ParamsContext ctx) { }
	@Override public void exitParams(lulu2_grammerParser.ParamsContext ctx) { }
	@Override public void enterCond_stmt(lulu2_grammerParser.Cond_stmtContext ctx) { }
	@Override public void exitCond_stmt(lulu2_grammerParser.Cond_stmtContext ctx) { }
	@Override public void enterSwitch_body(lulu2_grammerParser.Switch_bodyContext ctx) { }
	@Override public void exitSwitch_body(lulu2_grammerParser.Switch_bodyContext ctx) { }
	@Override public void enterLoop_stmt(lulu2_grammerParser.Loop_stmtContext ctx) {
		temp_scopeClass = new scopeClass(currentScope, "loop", scopeTypeEnum.OTHERS);
		currentScope.addChild(temp_scopeClass);
		currentScope = temp_scopeClass;
		currentSymbolTable = temp_scopeClass.getSymbolTable();
	}
	@Override public void exitLoop_stmt(lulu2_grammerParser.Loop_stmtContext ctx) { }
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