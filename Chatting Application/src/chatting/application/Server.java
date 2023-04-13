
package chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;   // socket programming for server class
import java.io.*;


public class Server implements ActionListener{
    
    JTextField text;
    JPanel displayPanel;
    static Box vertical = Box.createVerticalBox();  // isse msg vertically ek ke niche ek align ho jayenge
    static JFrame frame = new JFrame();
    static DataOutputStream dout;
    
    // constructor
    Server(){
        
     
        frame.setLayout(null);
        
        // created a panel 
        JPanel panel  = new JPanel();
        panel.setBackground(new Color(7,94,84));
        panel.setBounds(0,0,450,70);
        panel.setLayout(null);
        frame.add(panel);
        
        // Image icon 
        /* ab hume image ko file directories se nikalna hai ,, to humare paa ek class hoti hai class loade
        or uske andar ek method hota hai getSystemResource,, toh uske andar hume apne image ka naam dena hota hai */
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image img = image.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT);  // image ko scaled ki 
        ImageIcon img2 = new ImageIcon(img);  // ab usko firse image icon mein convert kar diya
        JLabel back = new JLabel(img2);   // hum image ko directly frame mein nahi laga sakte isliye JLabel mein pass kiya
        back.setBounds(5,20,25,25);
        panel.add(back);  // or ab uss image ko frame mein add karke,, panel mein add kar diya
        
        // ab mujhe back button pe mouse click se listen karwana hai 
       back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
       
       // Profile pic setting 
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.jpeg"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        panel.add(profile);
        // Video Image
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        panel.add(video);
        // phone image 
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        panel.add(phone);
       // icon image        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        panel.add(morevert);
        
        // name label of profile pic
        JLabel name = new JLabel("AJAY");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        panel.add(name);
        
        // status Label
        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        panel.add(status);
        
        // Panel to display Text
         displayPanel = new JPanel();
        displayPanel.setBounds(5, 75, 440, 570);
        frame.add(displayPanel);
        
        // user text likhega 
        text = new JTextField();
        text.setBounds(5, 600, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
         frame. add(text);
        
        //Send Button
        JButton send = new JButton("Send");
        send.setBounds(320, 600, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        frame.add(send);
        
         // setting Frame
        frame.setBounds(200,10,450,650);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(Color.WHITE);
              
        frame.setVisible(true);
        
    }
    
     @Override
    public void actionPerformed(ActionEvent e) {
      
        // getting all the inputs
        try{
           String getuserText =text.getText();  // jo user ne text input diya hai usko get kiya
           
        /*   // ab mai isme rightSide mein directly string pass nhi kar sakta so ,, so mai ek or panel banaunga
           JLabel userText = new JLabel(getuserText); //  user ke text ko label mein le liye
           JPanel display = new JPanel();  // uss label ke object ko panel mein daala,, hum direct panel mein label add nahi kar sakte,, uska object bana kar add karna padega 
            display.add(userText);   // or ab usko panel mein add kar sakte hai   */
           
           JPanel display = formatLabel(getuserText);   // this is to format my messages into box shape
           // humne apne display panel pe new  borderLayout set kar diya
           displayPanel.setLayout(new BorderLayout());    // Border layout hamare element ko place karta hai ( top, bottom, left, right)
           
           // niche ki dono line se message right side align hoga           
           JPanel rightSide = new JPanel(new BorderLayout()); // ab mujhe uss text ko border layout ki help se panel ke right side mein set karna hai           
           rightSide.add(display, BorderLayout.LINE_END); // maine, jo user se text liya hai usko mai panel ke right side ke end mein place karna chahta hu 
          
           
           vertical.add(rightSide); // agar multiple messages hai toh wo ek ke niche ek align ho jayenge
           vertical.add(Box.createVerticalStrut(15));
           
           // humne and displayPanel mein add karna hai vertical ko ,, and hume uss text ko kaha dikhana hai toh,, hume usko page ke start mein dikhana hai 
           displayPanel.add(vertical,BorderLayout.PAGE_START);
           
           /* hume frame ko repaint karna padega,, becoz jab mai koi text type kar rha hu toh mujhe chahiye ki wo text 
           mere display pe show ho ,, but wo abhi show nahi koga becoz mujhe apne frame ek ek or new frame ko add karna
           padega (means mujhe apne frame ko repaint karna padega ,, jispe wo text show karega)*/
           
           
           // with data output stream we write the messages and withe the writeUTF function we sends the meassages
           dout.writeUTF(getuserText); // sending msg
           
           // messgae send karne ke baad ,,,repaint hone se pehle,, hamara wo text area khaali ho jaaeyga 
           text.setText("");
           
           
           // ye 3 functins hai jiska use kar sakte hai 
           frame.repaint();
           frame.invalidate();
           frame.validate();
           
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }
    
    // method to format the messagaes,, means ki mujhe mere messages box mein dikhana hai 
        public static JPanel formatLabel(String getuserText) {
        JPanel panel = new JPanel();   // maine ek panel banaya jispe hume work karke usko return karna hai 
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // panel pe box layout lagaya,, jisme 2 arguments paas karte hai ,, kaha par lagana hai box layout ko or kis side lagana hai  
        
        // ek label banaya  jispe userText add kar diya 
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + getuserText + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);  // ye function 
        output.setBorder(new EmptyBorder(15, 15, 15, 50));  //padding to get space befoe and after text
        
        // or us ouput label ko panel mein add kar diya 
        panel.add(output);
        
        // Calender class to display time 
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        //  Label banaya or uspe time ko set kar diya ,, becoz frame pe kuch bhi likhna ho toh usko ek JLabel ke andar daalna hota hai
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));  // setText function is used to set the value dynamically 
        
        panel.add(time);       // usko panel mein add kar liya 
        
        return panel;    // finally panel ko return kar diya 
    }
    
    public static void main(String[] args){
        
        new Server();
       
         try {
             // mujhe ek server banana hai or usme port number pass kar diya
            ServerSocket skt = new ServerSocket(6001);
            // or ab  uss server ko  infinite times chalana hai or apne messages ko accept karna hai
            while(true) {
                Socket s = skt.accept();  // accepting message from client and storing it in the socket
                // ab mere paas do option hai,, ya toh message recieve kar sakta hu ya send kar sakta hu
                DataInputStream din = new DataInputStream(s.getInputStream()); // recieving messages with the help of socket and reading that message with data input stream and storing it in din object
                dout = new DataOutputStream(s.getOutputStream());  // writing the message with data output stream and storing it in dout object
                
                // recieving the messages 
                while(true) {
                    // agar mere paas koi message aaya hai toh mai usko readUTF function ki help se read karunga.,, or readUTF function ek string ko return karta hai
                    String msg = din.readUTF();  // ab jo msg aaya hai wo din mein hai or usko read karunga readUTF function se,, kyonki mere pass ek input aa rhi hai 
                    // ab jo message client se aaya hai wo msg mein store kar liya hai 
                    JPanel panel = formatLabel(msg); // or ab humne uss msg ko formaLabel function mein pass kar diya taaki hamare msg ki formatting ho sake(time aa jaye, backgroung color aa jaye),, or usko panel mein recieve kar liya 
                    
                    // ab ek or panel banaya becoz mai recieved msg ko mai panel ke left mein dikhaunga
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    frame.validate();  // apne frame ko refresh karna hai 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
            
}
