import jdk.dynalink.Operation;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException{

	// we expect exactly one argument: the name of the input file
	if (args.length!=1) {
	    System.err.println("\n");
	    System.err.println("Simple interpreter\n");
	    System.err.println("==================\n\n");
	    System.err.println("Please give as input argument a filename\n");
	    System.exit(-1);
	}
	String filename=args[0];

	// open the input file
	CharStream input = CharStreams.fromFileName(filename);
	    //new ANTLRFileStream (filename); // depricated
	
	// create a lexer/scanner
	implLexer lex = new implLexer(input);
	
	// get the stream of tokens from the scanner
	CommonTokenStream tokens = new CommonTokenStream(lex);
	
	// create a parser
	implParser parser = new implParser(tokens);
	
	// and parse anything from the grammar for "start"
	ParseTree parseTree = parser.start();

	// Construct an interpreter and run it on the parse tree
	//Interpreter interpreter = new Interpreter();
	Command p = (Command) new AstMaker().visit(parseTree);
	p.eval(new Environment());
    }
}

// We write an interpreter that implements interface
// "implVisitor<T>" that is automatically generated by ANTLR
// This is parameterized over a return type "<T>" which is in our case
// simply a Double.


class AstMaker extends AbstractParseTreeVisitor<AST> implements implVisitor<AST> {

    public AST visitStart(implParser.StartContext ctx){
	Command program=new NOP();
	for(implParser.CommandContext c:ctx.cs)
	    program=new Sequence(program,(Command)visit(c));
	return program;
    };

    public AST visitSingleCommand(implParser.SingleCommandContext ctx){
	return visit(ctx.c);
    }

    public AST visitMultipleCommands(implParser.MultipleCommandsContext ctx){
	Command program=new NOP();
	for(implParser.CommandContext c:ctx.cs)
	    program=new Sequence(program,(Command)visit(c));
	return program;
    }
    
    public AST visitAssignment(implParser.AssignmentContext ctx){
	String v=ctx.x.getText();
 	Expr e=(Expr)visit(ctx.e);
	return new Assignment(v,e);
    }
    
    public AST visitOutput(implParser.OutputContext ctx){
	Expr e=(Expr)visit(ctx.e);
	return new Output(e);
    }

    public AST visitWhileLoop(implParser.WhileLoopContext ctx){
	Condition c=(Condition)visit(ctx.c);
	Command body=(Command)visit(ctx.p);
	return new While(c,body);
    }


	public AST visitForLoop(implParser.ForLoopContext ctx){

		String str = ctx.s.getText();
		Expr e1 = (Expr)visit(ctx.e1);
		Expr e2 = (Expr)visit(ctx.e2);
		Command body=(Command)visit(ctx.p);

		return new Forloop(str, e1, e2, body);
	}



	public AST visitArray(implParser.ArrayContext ctx){

		String s = ctx.s.getText();
		Expr index = (Expr)visit(ctx.index);
		Expr value = (Expr)visit(ctx.val);

		return new Array(s, index, value);
	}

	public AST visitArrayRead(implParser.ArrayReadContext ctx){

		String s = ctx.s.getText();
		Expr index = (Expr)visit(ctx.index);

		return new ArrayRead(s, index);
	}










	public AST visitIfStatement(implParser.IfStatementContext ctx){
		Condition c=(Condition)visit(ctx.c);
		Command body=(Command)visit(ctx.p);
		return new IfStatement(c,body);
	}


    
    public AST visitParenthesis(implParser.ParenthesisContext ctx){
	return visit(ctx.e);
    };
    
    public AST visitVariable(implParser.VariableContext ctx){
	return new Variable(ctx.x.getText());
    }


//	public AST visitSubtraction(implParser.SubtractionContext ctx) {
//		return new Subtraction((Expr) visit(ctx.e1), (Expr)visit(ctx.e2));	}
//	;
    
    public AST visitAddition(implParser.AdditionContext ctx){

    	if (ctx.op.getText().equals("-")) {
			return new Subtraction((Expr) visit(ctx.e1), (Expr)visit(ctx.e2));

		} else  {
			return new Addition((Expr) visit(ctx.e1), (Expr)visit(ctx.e2));
		}

    };

    public AST visitMultiplication(implParser.MultiplicationContext ctx){
	return new Multiplication((Expr) visit(ctx.e1), (Expr)visit(ctx.e2));
    };

	public AST visitDivision(implParser.DivisionContext ctx){
		return new Division((Expr) visit(ctx.e1), (Expr)visit(ctx.e2));
	};




    public AST visitConstant(implParser.ConstantContext ctx){
	return new Constant(Double.parseDouble(ctx.c.getText())); 
    };

    public AST visitUnequal(implParser.UnequalContext ctx){
		Expr v1=(Expr)visit(ctx.e1);
		Expr v2=(Expr)visit(ctx.e2);
		return new Unequal(v1,v2);
    }


	public AST visitEqual(implParser.EqualContext ctx){
		Expr v1=(Expr)visit(ctx.e1);
		Expr v2=(Expr)visit(ctx.e2);
		return new Equal(v1,v2);
	}


	public AST visitGreaterThan(implParser.GreaterThanContext ctx){
		Expr v1=(Expr)visit(ctx.e1);
		Expr v2=(Expr)visit(ctx.e2);
		return new GreaterThan(v1,v2);
	}







	public AST visitLessThan(implParser.LessThanContext ctx){
		Expr v1=(Expr)visit(ctx.e1);
		Expr v2=(Expr)visit(ctx.e2);
		return new LessThan(v1,v2);
	}



	public AST visitOrBinary(implParser.OrBinaryContext ctx){

		Condition v1= (Condition) visit(ctx.e1);
		Condition v2= (Condition) visit(ctx.e2);

		return new OrBinary(v1,v2);
	}


	public AST visitAndBinary(implParser.AndBinaryContext ctx){

		Condition c1= (Condition) visit(ctx.c1);
		Condition c2= (Condition) visit(ctx.c2);

		return new AndBinary(c1,c2);
	}


}

