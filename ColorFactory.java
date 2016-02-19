
/**
*
*  Author :  Josh Klaus
*  Date    :  11/20/15
*  Homework Assignment : 4
*  Objective  :  A class that creates a GUI Color Factory
*                in which the user can use sliders to discover
*                the numeric definition for a specific color
*               
*/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class ColorFactory
{
    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new JFrame();
 
                frame.setTitle("Color Factory");
                frame.getContentPane().add(new ColorLayout());
                frame.setSize(1000,400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
        
                frame.setVisible(true);

            }
        });
    }
}

////////////////// class that holds the container for the Panel ////////////

class ColorLayout extends JPanel
{
    JLabel cfHeader = new JLabel("Color Factory");

    JTextField colorText, redText, grnText, bluText;

    JRadioButton decButton, octButton, binButton, hexButton;

    ButtonGroup radioButtonGroup;

    JSlider redBar, greenBar, blueBar;

    int red = 0;
    int green = 0;
    int blue = 0;

    GraphicsPanel ovalDisplay;
    VerticalColorBar colorBars;

//////////////////// Constructor for the Layout /////////////////

    public ColorLayout() 
    {

        redBar   = getSlider(0, 255, red, 50, 5);
        greenBar = getSlider(0, 255, green, 50, 5);
        blueBar  = getSlider(0, 255, blue, 50, 5);

        cfHeader.setFont(new Font("Arial", Font.BOLD, 35));

        setLayout(new GridLayout(1, 3));

        ovalDisplay = new GraphicsPanel();
        colorBars = new VerticalColorBar();

        colorText = new JTextField("      R:0               G:0  " +
            "             B:0          ");

//        colorText = new JTextField(20);

        redText = new JTextField("Red:        ");
        grnText = new JTextField("Green:      ");
        bluText = new JTextField("Blue:       ");


        decButton = new JRadioButton("Decimal", true);
        decButton.setToolTipText("<html><h3>Click this button to " + 
            "display Decimal values of Color.</h3></html>");

        octButton = new JRadioButton("Octal");
        octButton.setToolTipText("<html><h3>Click this button to " +
            "display Octal values of Color.</h3></html>");

        binButton = new JRadioButton("Binary");
        binButton.setToolTipText("<html><h3>Click this button to " + 
            "display Binary values of Color.</h3></html>");

        hexButton = new JRadioButton("Hex");
        hexButton.setToolTipText("<html><h3>Click this button to " + 
            "display Hex values of Color.</h3></html>");

        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(decButton);
        radioButtonGroup.add(octButton);
        radioButtonGroup.add(binButton);
        radioButtonGroup.add(hexButton);

        decButton.addActionListener(new RadioButtonListener());
        octButton.addActionListener(new RadioButtonListener());
        binButton.addActionListener(new RadioButtonListener());
        hexButton.addActionListener(new RadioButtonListener());

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();


        panel1.add(colorBars);
        panel1.add(colorText);
        panel2.add(cfHeader);
        panel2.add(ovalDisplay);
        panel3.add(redBar);
        panel3.add(redText);
        panel3.add(greenBar);
        panel3.add(grnText);
        panel3.add(blueBar);
        panel3.add(bluText);
        panel3.add(decButton);
        panel3.add(octButton);
        panel3.add(binButton);
        panel3.add(hexButton);

        add(panel1);
        add(panel2);
        add(panel3);
    }

/////////////// getSlider method for assigning ticks and listeners to all 3 sliders////
    public JSlider getSlider(int min, int max, int init, int mjrTkSp, int mnrTkSp) 
    {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, init);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(mjrTkSp);
        slider.setMinorTickSpacing(mnrTkSp);
        slider.setPaintLabels(true);
        slider.setToolTipText("<html><h3>Move this slider to adjust color.</h3>" +
           "<h4>Click button below to update the value displayed under the " + 
           "bars on the left</h4></html>");
        slider.addChangeListener(new SliderListener());
        return slider;
    }

//////////////  GraphicsPanel inner class for creating Oval display //////////////////
    class GraphicsPanel extends JPanel
    {
        public GraphicsPanel()
        {
            this.setPreferredSize(new Dimension(300,150));
            this.setBackground(Color.white);
            this.setForeground(Color.black);
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(new Color(red, green, blue));
            g.fillOval(0,0,this.getWidth(),this.getHeight());
        }

    }

//////////////  VerticalColorBar inner class for creating Vertical Bars ///////////////
    class VerticalColorBar extends JPanel
    {
        public VerticalColorBar()
        {
            this.setPreferredSize(new Dimension(250, 300));
            this.setBackground(Color.white);
            this.setForeground(Color.black);
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(new Color(255, 0, 0));
            g.fillRect(5,290 - red,75,red + 1);

            g.setColor(new Color(0, 255, 0));
            g.fillRect(85,290 - green,75,green +1);

            g.setColor(new Color(0, 0, 255));
            g.fillRect(165,290 - blue,75,blue +1);
        }

    }

/////////// inner class that handles event when user clicks radio buttons //////
    class RadioButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String rgbValue;

            if (e.getSource() == decButton)
            {
                rgbValue =  ("    R:" + red + 
                    "             G:" + green + 
                    "             B:" + blue) ;
                colorText.setText(rgbValue);
            }
            else if (e.getSource() == octButton)
            {
                rgbValue =  ("   R:" + Integer.toOctalString(red) + 
                    "            G:" + Integer.toOctalString(green) + 
                    "            B:" + Integer.toOctalString(blue)) ;
                colorText.setText(rgbValue);
            }
            else if (e.getSource() == binButton)
            {
                rgbValue =  (" R:" + Integer.toBinaryString(red) + 
                    "  G:" + Integer.toBinaryString(green) + 
                    "  B:" + Integer.toBinaryString(blue)) ;
                colorText.setText(rgbValue);
            }
            else if (e.getSource() == hexButton)
            {
                rgbValue =  ("    R:" + Integer.toHexString(red) + 
                    "             G:" + Integer.toHexString(green) + 
                    "             B:" + Integer.toHexString(blue)) ;
                colorText.setText(rgbValue);
            }

        }
    }

/////////// inner class that handles event when user moves RGB sliders //////
    class SliderListener implements ChangeListener 
    {
        public void stateChanged(ChangeEvent e) 
        {
 
                red = redBar.getValue();
                green = greenBar.getValue();
                blue = blueBar.getValue();
                redText.setText("Red:" + red);
                grnText.setText("Green:" + green);
                bluText.setText("Blue:" + blue);
                ovalDisplay.repaint();
                colorBars.repaint();
        }
    }    

}
