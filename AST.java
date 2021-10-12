import java.util.List;
import java.util.ArrayList;

class faux{
    public static void error(String msg){
        System.err.println("Interpreter error: "+msg);
        System.exit(-1);
    }
}


enum Type {
    INT_TYPE, BOOL_TYPE;
}

class Value {
    public Type valueType;
    public int i;
    public boolean b;

    Value(int i) {
        valueType = Type.INT_TYPE;
        this.i = i;
    }

    Value(boolean b) {
        valueType = Type.BOOL_TYPE;
        this.b = b;
    }

    public String toString() {
        if (valueType == Type.INT_TYPE) {
            return ""+i;

        } else {return ""+b;}
    }

}

public abstract class AST{
    abstract public Value eval(Environment enm);
    abstract public Type typeCheck(Environment env);
};

class TypeIdent extends AST {

    public Type valueType;
    public String ident;
    TypeIdent(Type valueType, String ident) {
        this.valueType = valueType;
        this.ident = ident;
    }



    @Override
    public Value eval(Environment enm) {
        faux.error("TypedIdent.eval should not be called!");
        return null;
    }

    @Override
    public Type typeCheck(Environment env) {
        faux.error("TypedIdent.eval should not be called!");
        return null;
    }
}











abstract class Expr extends AST{
   // abstract public Double eval(Environment env);


}

class Addition extends Expr{
    Expr e1,e2;
    Addition(Expr e1,Expr e2){
        this.e1=e1;
        this.e2=e2;
    }
    public Value eval(Environment env){
        Value v1 = e1.eval(env);
        Value v2 = e2.eval(env);
        return new Value(v1.i + v2.i);
    }

    @Override
    public Type typeCheck(Environment env) {
        return null;
    }
}

class Multiplication extends Expr{
    Expr e1,e2;
    Multiplication(Expr e1,Expr e2) {
        this.e1=e1; this.e2=e2;
    }
    public Value eval(Environment env){
        Value v1 = e1.eval(env);
        Value v2 = e2.eval(env);

	    return new Value(v1.i * v2.i);
    }

    @Override
    public Type typeCheck(Environment env) {
        return null;
    }
}


class Division extends Expr{
    Expr e1,e2;
    Division(Expr e1,Expr e2) {
        this.e1=e1; this.e2=e2;
    }
    public Value eval(Environment env){
        Value v1 = e1.eval(env);
        Value v2 = e2.eval(env);
        return new Value(v1.i / v2.i);
    }

    @Override
    public Type typeCheck(Environment env) {
        return null;
    }
}


class Subtraction extends Expr{
    Expr e1,e2;
    Subtraction(Expr e1,Expr e2) {
        this.e1=e1; this.e2=e2;
    }
    public Value eval(Environment env){
        Value v1 = e1.eval(env);
        Value v2 = e2.eval(env);
        return new Value(v1.i - v2.i);
    }

    @Override
    public Type typeCheck(Environment env) {
        return null;
    }
}

class Constant extends Expr{
    Value d;
    Constant(Value d){
        this.d=d;
    }
    public Value eval(Environment env){
	return d;
    }

    @Override
    public Type typeCheck(Environment env) {
        return null;
    }
}

class Variable extends Expr{
    String varname;
    Variable(String varname){
        this.varname=varname;
    }
    public Value eval(Environment env){

	return env.getVariable(varname);
    }

    @Override
    public Type typeCheck(Environment env) {
        return null;
    }
}
//
//abstract class Command extends AST{
//    abstract public void eval(Environment env);
//}
//
//// Do nothing command
//class NOP extends Command{
//    public void eval(Environment env){};
//}
//
//class Sequence extends Command{
//    Command c1,c2;
//    Sequence(Command c1, Command c2){this.c1=c1; this.c2=c2;}
//    public void eval(Environment env){
//	c1.eval(env);
//	c2.eval(env);
//    }
//}
//
//
//class Assignment extends Command{
//    String v;
//    Expr e;
//    Assignment(String v, Expr e){
//	this.v=v; this.e=e;
//    }
//    public void eval(Environment env){
//	Double d=e.eval(env);
//	env.setVariable(v,d);
//    }
//}
//
//class Output extends Command{
//    Expr e;
//    Output(Expr e){
//	this.e=e;
//    }
//    public void eval(Environment env){
//	Double d=e.eval(env);
//	System.out.println(d);
//    }
//}
//
//class While extends Command{
//    Condition c;
//    Command body;
//    While(Condition c, Command body){
//	this.c=c; this.body=body;
//    }
//    public void eval(Environment env){
//	while (c.eval(env))
//	    body.eval(env);
//    }
//}
//
//
//
//class Forloop extends Command{
//    String str;
//    Expr e1, e2;
//    Command body;
//    Forloop(String str, Expr e1, Expr e2, Command body){
//        this.str = str;
//        this.e1 = e1;
//        this.e2 = e2;
//        this.body=body;
//    }
//    public void eval(Environment env){
//
//        //new Assignment(str, e1);
//        double e1Double = e1.eval(env);
//        int e1Int = (int) e1Double;
//
//        double e2Double = e2.eval(env);
//        int e2Int = (int) e2Double;
//
//        Environment envNew = env;
//
//        for (int i = e1Int; i <= e2Int ; i++) {
//            envNew.setVariable(str, (double) i);
//            body.eval(envNew);
//        }
//
//    }
//}
//
//class Array extends Command{
//    String arrName;
//    Expr index, value;
//
//    Array(String arrName, Expr index, Expr value){
//        this.arrName = arrName;
//        this.index = index;
//        this.value = value;
//    }
//
//    public void eval(Environment env){
//        String arrNameWithIndex = arrName +"[" + index.eval(env).intValue() + "]";
//        env.setVariable(arrNameWithIndex, value.eval(env));
//    }
//
//}
//
//
//
//class ArrayRead extends Expr{
//    String arrName;
//    Expr index;
//
//    ArrayRead(String arrName, Expr index){
//        this.arrName = arrName;
//        this.index = index;
//    }
//
//    public Double eval(Environment env){
//
//        String arrNameWithIndex = arrName +"[" + index.eval(env).intValue() + "]";
//        return env.getVariable(arrNameWithIndex);
//    }
//}
//
//
//
//
//
//
//
//
//class IfStatement extends Command{
//    Condition c;
//    Command body;
//    IfStatement(Condition c, Command body){
//        this.c=c; this.body=body;
//    }
//    public void eval(Environment env){
//        if (c.eval(env)) {
//            body.eval(env);
//        }
//    }
//}
//
//
//abstract class Condition extends AST{
//    abstract public Boolean eval(Environment env);
//}
//
//class Unequal extends Condition{
//    Expr e1,e2;
//    Unequal(Expr e1,Expr e2){this.e1=e1; this.e2=e2;}
//    public Boolean eval(Environment env){
//	return ! e1.eval(env).equals(e2.eval(env));
//    }
//
//}
//
//class Equal extends Condition{
//    Expr e1,e2;
//    Equal(Expr e1,Expr e2){
//        this.e1=e1; this.e2=e2;
//    }
//
//    public Boolean eval(Environment env){
//        return e1.eval(env).equals(e2.eval(env));
//    }
//
//}
//
//
//class GreaterThan extends Condition{
//    Expr e1,e2;
//    GreaterThan(Expr e1,Expr e2){
//        this.e1=e1; this.e2=e2;
//    }
//
//    public Boolean eval(Environment env){
//
//        boolean result = false;
//        if (e1.eval(env) > e2.eval(env)) {
//            result = true;
//        }
//
//        return result;
//    }
//
//}
//
//
//
//class LessThan extends Condition{
//    Expr e1,e2;
//    LessThan(Expr e1,Expr e2){
//        this.e1=e1; this.e2=e2;
//    }
//
//    public Boolean eval(Environment env){
//        boolean result = false;
//        if (e1.eval(env) < e2.eval(env)) {
//            result = true;
//        }
//        return result;
//    }
//}
//
//
//
//class OrBinary extends Condition{
//    Condition c1,c2;
//    OrBinary(Condition c1,Condition c2){
//        this.c1=c1; this.c2=c2;
//    }
//
//    public Boolean eval(Environment env){
//        boolean result = false;
//
//
//        if (c1.eval(env) || c2.eval(env)) {
//            result = true;
//        }
//        return result;
//    }
//}
//
//
//class AndBinary extends Condition{
//    Condition c1,c2;
//    AndBinary(Condition c1,Condition c2){
//        this.c1=c1; this.c2=c2;
//    }
//
//    public Boolean eval(Environment env){
//        boolean result = false;
//        if (c1.eval(env) && c2.eval(env)) {
//            result = true;
//        }
//        return result;
//    }
//}
//


























