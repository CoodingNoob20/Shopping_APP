import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;



public class ShoppingWindow implements ActionListener, WindowListener {
    Frame f;
    Font ft = new Font("SansSerif", Font.BOLD, 20);
    Font fts = new Font("SansSerif", Font.BOLD, 15);
    Label l1,l2,l3,l4,l5;
    static Label update;
    TextField tf1,tf2,tf3,tf4;
    Button add,clear,checkout,reset,data;
    static TextArea prc,basket;
    static Connection con;
    static StringBuilder rateinfo = new StringBuilder("");
    static StringBuilder bask = new StringBuilder("");;

    public ShoppingWindow(){
        f = new Frame("Shopping Window");
        f.addWindowListener(this);
        f.setLayout(null);
        f.setSize(1000,800);
        f.setResizable(false);
        f.setVisible(true);

        prc = new TextArea();
        prc.setBounds(50,50,900,200);
        prc.setFont(ft);
        prc.setText("Vegetable id\t\tVegetable name\t\tVegetable quantity\t\tVegetable price");
        prc.setEditable(false);
        f.add(prc);

        basket = new TextArea();
        basket.setBounds(700,300,250,400);
        basket.setFont(ft);
        basket.setText("\t Shopping List\n\n");
        basket.setEditable(false);
        f.add(basket);

        l1=new Label("Shopping View");
        l1.setBounds(300,280,150,30);
        l1.setFont(fts);
        f.add(l1);

        l2=new Label("Vegetable id");
        l2.setBounds(50,400,100,30);
        l2.setFont(fts);
        f.add(l2);

        l3=new Label("Vegetable Name");
        l3.setBounds(200,400,140,30);
        l3.setFont(fts);
        f.add(l3);

        l4=new Label("Vegetable Quantity");
        l4.setBounds(350,400,142,30);
        l4.setFont(fts);
        f.add(l4);

        l5=new Label("Vegetable Rate");
        l5.setBounds(550,400,140,30);
        l5.setFont(fts);
        f.add(l5);

        update = new Label("");
        update.setBounds( 50,750,600,40);
        f.add(update);

        tf1 = new TextField("id");
        tf1.setBounds(50,430,100,30);
        f.add(tf1);

        tf2 = new TextField("name");
        tf2.setBounds(200,430,140,30);
        tf2.setEditable(false);
        f.add(tf2);

        tf3 = new TextField("quantity");
        tf3.setBounds(350,430,142,30);
        f.add(tf3);

        tf4 = new TextField("rate");
        tf4.setBounds(550,430,140,30);
        tf4.setEditable(false);
        f.add(tf4);

        checkout = new Button("Checkout");
        checkout.setBounds(700,725,250,30);
        f.add(checkout);

        add = new Button("Add Item to list");
        add.setBounds(500,550,100,30);
        add.addActionListener(this);
        f.add(add);

        reset = new Button("Reset Shopping List");
        reset.setBounds(350,550,120,30);
        reset.addActionListener(this);
        f.add(reset);

        clear = new Button("Clear");
        clear.setBounds(200,550,120,30);
        clear.addActionListener(this);
        f.add(clear);

        data = new Button("Data");
        data.setBounds(70,550,120,30);
        data.addActionListener(this);
        f.add(data);



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

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from vegetable;");
            System.out.println("id\t\tname\t\tquantity\t\tprice");

            // Condition check
            while (rs.next()) {
                StringBuilder s;
                int id = rs.getInt("vegetable_id");
                String name = rs.getString("vegetable_name");
                int quantity = rs.getInt("vegetable_quantity");
                int price = rs.getInt("vegetable_price");
//                System.out.println(id + "\t\t" + name
//                        + "\t\t" + quantity + "\t\t\t\t" + price+"\n");
                s=new StringBuilder("\t"+id + "\t\t\t" + name
                        + "\t\t\t\t\t  " + quantity + "\t\t\t\t\t\t  " + price+"\n");
                rateinfo.append(s);
            }

        } catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("error");
//            update.setText("ERROR!!");

        }

        new ShoppingWindow();
        System.out.println(rateinfo);
        prc.setText("Vegetable id\tVegetable name\tVegetable quantity available for sale\tVegetable price\n"+rateinfo);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if(ae.getActionCommand() == "Clear"){
            tf1.setText("");
            tf2.setText("");
            tf3.setText("");
            tf4.setText("");
        }

        if (ae.getActionCommand() == "Add Item to list"){
            int id = Integer.parseInt(tf1.getText());
            int quan = Integer.parseInt(tf3.getText());
            int price=0;
            String name = "";

            try {
                Statement st = con.createStatement();
                //st = con.prepareStatement("SELECT * FROM vegetable WHERE vegetable_id='?';");
                //st.setInt(1,id);
                ResultSet rs = st.executeQuery("SELECT * FROM vegetable WHERE vegetable_id="+id+";");
                while (rs.next()){
                    name = rs.getString("vegetable_name");
                    price = rs.getInt("Vegetable_price");
                    System.out.println(name);
                    StringBuilder a = new StringBuilder("\t"+id+"\t\t\t\t"+name+"\t\t\t\t  "+quan+"\t\t\t\t\t"+price+"\n");
                    bask.append(a);
                }
            }
            catch (SQLException e) {
                System.out.println(e);
                System.out.println("error");
                update.setText(String.valueOf(e));
            }
            basket.setText("\t Shopping List\n\n"+
                    "Vegetable id\t\tVegetable Name\t\tVegetable Quantity\t\tVegetable Price\n"+bask);
            //ResultSet rs = st.executeQuery("Select * from vegetable where ");
//****************************************************************************************************************

            try {
                PreparedStatement sta = con.prepareStatement("INSERT INTO vegetable_sale value(?,?,?,?)");
                sta.setInt(1,id);
                sta.setString(2,name);
                sta.setInt(3,quan);
                sta.setInt(4,price);
                int res= sta.executeUpdate();
                if (res>0){
                    System.out.println("inserted");
                }
            } catch (SQLException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }

        if (ae.getActionCommand() == "Reset Shopping List"){
            basket.setText("\t Shopping List\n\n");
            try {
                Statement st = con.createStatement();
                st.execute("DELETE FROM vegetable_sale");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (ae.getActionCommand() == "Checkout"){
            int resultquantity;

            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM vegetable");
                Statement mt = con.createStatement();
                ResultSet ms = mt.executeQuery("SELECT * FROM vegetable_sales");
                rs.getInt(2);
            }
            catch (Exception e){

            }

        }

        if (ae.getActionCommand() == "Data"){
//            new shoppingApp();
        }


    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            Statement st = con.createStatement();
            st.execute("DELETE FROM vegetable_sale");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.exit(0);
        f.dispose();
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


