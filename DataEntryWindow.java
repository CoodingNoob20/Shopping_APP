import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;


public class DataEntryWindow implements ActionListener, WindowListener {
    Frame f;
    Button shop, submit, clear;
    Label l1, l2, l3, l4, l5,stat;
    TextField tf1,tf2,tf3,tf4;
    Font fn = new Font("SansSerif", Font.BOLD, 20);
    static Connection con;
    static PreparedStatement st;

    public DataEntryWindow() {
        f = new Frame("STOCK ENTRY");
        f.addWindowListener(this);

        f.setLayout(null);
        f.setSize(1000, 800);
        f.setResizable(false);
        f.setVisible(true);

        l1 = new Label("Data Entry");
        l1.setBounds(450, 50, 100, 30);
        l1.setFont(fn);
        f.add(l1);
        //vegetable id
        l2 = new Label("Vegetable ID :");
        l2.setBounds(200,200,150,30);
        l2.setFont(fn);
        f.add(l2);

        tf1 = new TextField();
        tf1.setBounds(400,200,150,30);
        f.add(tf1);
        ////////////////////
        l3 = new Label("Vegetable Description :");
        l3.setBounds(200,250,150,30);
        l3.setFont(fn);
        f.add(l3);

        tf2 = new TextField();
        tf2.setBounds(400,250,150,30);
        f.add(tf2);
        /////////////////
        l4 = new Label("Vegetable Quantity:");
        l4.setBounds(200,300,150,30);
        l4.setFont(fn);
        f.add(l4);

        tf3 = new TextField();
        tf3.setBounds(400,300,150,30);
        f.add(tf3);

        l5 = new Label("Vegetable Rates:");
        l5.setBounds(200,350,150,30);
        l5.setFont(fn);
        f.add(l5);

        tf4 = new TextField();
        tf4.setBounds(400,350,150,30);
        f.add(tf4);

        stat = new Label("");
        stat.setBounds(800,100,70,30);
        f.add(stat);

        submit = new Button("Submit");
        submit.addActionListener(this);
        submit.setBounds(400,400,70,30);
        f.add(submit);

        clear = new Button("Clear");
        clear.addActionListener(this);
        clear.setBounds(500,400,70,30);
        f.add(clear);

        shop = new Button("Shopping window");
        shop.addActionListener(this);
        shop.setBounds(600,400,100,30);
        f.add(shop);

    }


    public static void main(String[] args) {
        try {


            // Establishing Connection
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project", "root", "Sanu@0011");

            if (con != null) {
                System.out.println("Connected");
            } else {
                System.out.println("Not Connected");
            }

            st = con.prepareStatement("INSERT INTO `project`.`vegetable` VALUES (?,?,?,?);");

        } catch (Exception e) {
            System.out.println(e);
        }


        new DataEntryWindow();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int id,price,quantity;
        String desc;

        if (ae.getActionCommand() == "Submit" ){
            id = Integer.parseInt(tf1.getText());
            System.out.println("the id is"+id);
            desc = tf2.getText();
            System.out.println("the name is" + desc);
            quantity = Integer.parseInt(tf3.getText());
            System.out.println("the quantity is"+quantity);
            price = Integer.parseInt(tf4.getText());
            System.out.println("the price is"+price);



            try {
                st.setInt(1,id);
                st.setString(2,desc);
                st.setInt(3,quantity);
                st.setInt(4,price);
                int res =  st.executeUpdate();

                if(res>0){
                    stat.setText("Data Inserted Successfully");
                    System.out.println("Inserted Successfully");
                }
                else{
                    stat.setText("Insertion Failed!!");
                    System.out.println("Insertion Failed!!");
                }
            }
            catch (Exception ex){
                System.out.println(ex);
                stat.setText(String.valueOf(ex));
            }
        }
        if (ae.getActionCommand() == "Clear"){
            tf1.setText("");
            tf2.setText("");
            tf3.setText("");
            tf4.setText("");
        }

        if (ae.getActionCommand() == "Shopping window"){
            f.dispose();
            new ShoppingWindow();
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            con.close();
            System.out.println("connection closed");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        f.dispose();
        System.exit(0);

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}







