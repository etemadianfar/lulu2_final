import lulu2_package.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Main extends Frame implements ActionListener{

    private static String st;
    TextArea b;
    Clipboard clpbrd=Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection strsel;
    MenuItem open,save,exit,cut,copy,past,al,compile;
    TextArea status;

    Main(String s) {
        super(s);
        b=new TextArea();
        add(b);
        winada a=new winada();
        status=new TextArea("ready to work",1,50,TextArea.SCROLLBARS_NONE);
        status.setEditable(false);
        add(status,BorderLayout.SOUTH);
        addWindowListener(a);
        MenuBar m=new MenuBar();
        Menu file=new Menu("File");
        Menu edit=new Menu("Edit");
        Menu compiling=new Menu("compile");
        al=new MenuItem("New");
        open=new MenuItem("Open");
        save=new MenuItem("Save");
        exit=new MenuItem("Exit");
        cut=new MenuItem("Cut");
        copy=new MenuItem("Copy");
        past=new MenuItem("Paste");
        compile=new MenuItem("compile");
        file.add(al);
        file.add(open);
        file.add(save);
        file.add(exit);
        m.add(file);
        edit.add(cut);
        edit.add(copy);
        edit.add(past);
        m.add(edit);
        compiling.add(compile);
        m.add(compiling);
        setMenuBar(m);
        al.addActionListener(this);
        open.addActionListener(this);
        exit.addActionListener(this);
        save.addActionListener(this);
        copy.addActionListener(this);
        past.addActionListener(this);
        cut.addActionListener(this);
        compile.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        Object a=ae.getSource();
        if(a==al)
        {
            b.setText("");
        }
        if(a==open)
        {
            FileDialog f=new FileDialog(this,"Select file to open...");
            f.setVisible(true);
            String	s=f.getDirectory()+f.getFile();;
            try
            {
                File fin=new File(s);
                FileInputStream fis1 = new FileInputStream(fin);
                int fileSize = fis1.available();
                byte buf1[] = new byte[fileSize];
                fis1.read(buf1);
                b.setText(new String(buf1));
            }
            catch(Exception e)
            {
            }
            status.setText("Opened");
        }
        if(a==exit)
        {
            System.exit(1);
        }
        if(a==save)
        {
            FileDialog f=new FileDialog(this,"Select the folder and Enter file name to save..",FileDialog.SAVE);
            f.setVisible(true);
            String s=f.getDirectory()+f.getFile();
            try
            {
                File fin=new File(s);
                FileOutputStream fis1 = new FileOutputStream(fin);
                Writer out = new OutputStreamWriter(fis1, "UTF8");
                out.write(b.getText());
                out.close();
            }
            catch(Exception e)
            {
            }
            status.setText("Saved");
        }
        if(a==copy)
        {
            strsel=new StringSelection(b.getSelectedText());
            clpbrd.setContents(strsel, strsel);
        }
        if(a==past)
        {
            try{
                b.setText(b.getText()+clpbrd.getData(DataFlavor.stringFlavor));
            }catch(Exception e){}
        }
        if(a==cut)
        {
            strsel=new StringSelection(b.getSelectedText());
            clpbrd.setContents(strsel, strsel);
            int strt,end;
            strt=b.getSelectionStart();
            end=b.getSelectionEnd();
            b.setText(b.getText().substring(0,strt)+b.getText().substring(end,b.getText().length()));
        }
        if(a==compile){
            st = b.getText();

            CharStream input = CharStreams.fromString(st);
            lulu2_grammerLexer lexer = new lulu2_grammerLexer(input);
            lexer.setTokenFactory(new CommonTokenFactory(true));
            TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lexer);
            lulu2_grammerParser parser = new lulu2_grammerParser(tokens);
            parser.setBuildParseTree(true);
            ParseTree tree = parser.program();

            lulu2_grammerListener listener = new lulu2_grammerBaseListener();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, tree);

            System.out.println("finish");
        }
    }

    public static void main(String[] args) throws Exception {
        Main a=new Main("COMPILER IDE");
        a.setSize(1366,754);
        a.setVisible(true);
    }
}

class winada extends WindowAdapter
{
    public void windowClosing(WindowEvent e)
    {
        System.exit(1);
    }
}