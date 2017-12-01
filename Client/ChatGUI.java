import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * Write a description of class ChatGUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ChatGUI implements ActionListener{
    JFrame frame;
    JTextArea display;
    JTextField entry, username;
    JButton submit;
    OutputStream outstream;
    InputStream in;
    Socket s;
    PrintWriter out;
    Scanner scanner;

    public ChatGUI() throws IOException, InterruptedException {
        this.frame = new JFrame("ChatGUI");
        this.display = new JTextArea(50, 50);
        this.display.setEnabled(false);
        this.display.setLineWrap(true);
        this.frame.add(display, BorderLayout.NORTH);
        this.entry = new JTextField(50);
        this.frame.add(entry, BorderLayout.CENTER);
        this.username = new JTextField(10);
        this.frame.add(username, BorderLayout.WEST);
        this.submit = new JButton("Send");
        this.submit.addActionListener(this);
        this.frame.add(submit, BorderLayout.EAST);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
        this.frame.setSize(new Dimension(500, 500));
        this.s = new Socket("192.168.1.3", 8081);
        this.in = s.getInputStream();
        this.outstream = s.getOutputStream();
        this.out = new PrintWriter(this.outstream);
        this.scanner = new Scanner(this.in);
        this.scanner.useDelimiter("qwert");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ChatGUI x = new ChatGUI();
        while (true) {
            try {
                x.update();
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println("1" + e);
                Thread.sleep(10);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        this.display.setText("");
        this.display.append(this.username.getText() + ": " + this.entry.getText() + "\n");
        try {
            this.out.print(this.display.getText());
            this.out.flush();
        } catch (Exception ee) {
            System.out.println("2" + ee);
        }
    }

    public void update() throws IOException{
        System.out.println(in.available());
        if (in.available() > 0) {
            this.display.setText(this.scanner.next());
        }
    }
}
