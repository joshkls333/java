/**
*
*  Author :  Josh Klaus
*  Date    :  11/27/15
*  Homework Assignment : 5 & 6
*  Objective  :  A class that creates a GUI Drawing Board
*              that allows the user to draw an image, save 
*              that picture into a file that can be reloaded.
*               
*/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;

import java.io.*;

import java.util.*;
import java.util.List;

public class DrawingBoard implements Serializable
{
    public static void main(String args[]) throws Exception
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new JFrame();
 
                frame.setTitle("Drawing Board");
                frame.getContentPane().add(new DrawingBoardPanel());
                frame.setSize(800,600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
        
                frame.pack();
                frame.setVisible(true);

            }
        });
    }
}

////////////////// class that holds the container for the Panel ////////////

class DrawingBoardPanel extends JPanel
{
    JLabel cfHeader = new JLabel("Drawing Board");

    JButton saveButton, loadButton, colorButton, 
            brushButton, exitButton, clearButton,
            undoButton, drawButton, rectButton,
            circleButton, lineButton, fillButton;

    JPanel bottomButtonPanel, topButtonPanel;

    DrawingCanvas paintshop;

    private int currentX, currentY, oldX, oldY;

    boolean check = true;
    boolean lineCheck, circleCheck, rectCheck, fillCheck;

    private List<Point> pointList = new ArrayList<Point>();
    private List<Color> colorList = new ArrayList<Color>();
    private BufferedImage bImage;
 
    private Image image;

    private Graphics2D g2;

    private String msg = "*";

    Point pointStart = null;
    Point pointEnd   = null;

    Color tmpColor = Color.BLACK;


//////////////////// Constructor for the Layout /////////////////////////////

    public DrawingBoardPanel() 
    {

        cfHeader.setFont(new Font("Arial", Font.BOLD, 35));

        setLayout(new BorderLayout());
 
        buildTopButtonPanel();

        buildBottomButtonPanel();

        paintshop = new DrawingCanvas();
        paintshop.addMouseListener(new MyMouseListener());
        paintshop.addMouseMotionListener(new MyMouseMotionListener());


        add(topButtonPanel, BorderLayout.NORTH);

        add(paintshop, BorderLayout.CENTER);

        add(bottomButtonPanel, BorderLayout.SOUTH);

    }

///////////////// Panel Constructor for the buttons at the Top of Frame //////

    private void buildTopButtonPanel()
    {
        topButtonPanel = new JPanel();
        topButtonPanel.setLayout(new GridLayout(1,4));
        topButtonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        rectButton = new JButton("RECTANGLE");
        rectButton.setToolTipText("<html><h3>Click this button to draw " + 
            "a rectangle.</h3>  <h4>Click once for upper-left corner and " +
            "twice for lower-right corner.</h4>" + 
            "<h5>Click fill to the right if you DO want it filled in.</h5>" + 
            "  <h3>Click Draw to return to normal drawing.</h3></html>");
        rectButton.addActionListener(new ButtonListener());


        circleButton = new JButton("CIRCLE");
        circleButton.setToolTipText("<html><h3>Click this button to draw " + 
            "a circle.</h3>  <h4>Click once for upper-left corner and " +
            "twice for lower-right corner.</h4>" +
            "<h5>Click fill to the right if you DO want it filled in.</h5>" + 
            "  <h3>Click Draw to return to normal drawing.</h3></html>");
        circleButton.addActionListener(new ButtonListener());

        lineButton = new JButton("LINE");
        lineButton.setToolTipText("<html><h3>Click this button to draw " + 
            "a line.</h3>  <h4>Click once for left point and " +
            "twice for right point.</h4>" + 
            "  <h3>Click Draw to return to normal drawing.</h3></html>");
        lineButton.addActionListener(new ButtonListener());

        fillButton = new JButton("FILL");
        fillButton.setToolTipText("<html><h3>Click this button FIRST to create" + 
            " a Rectangle or Circle that is NOT hollow.</h3></html>");
        fillButton.addActionListener(new ButtonListener());

        topButtonPanel.add(rectButton);
        topButtonPanel.add(circleButton);
        topButtonPanel.add(lineButton);
        topButtonPanel.add(fillButton);
    }

///////////////// Panel Constructor for the buttons at the Bottom of Frame //////

    private void buildBottomButtonPanel()
    {
        bottomButtonPanel = new JPanel();
        bottomButtonPanel.setLayout(new GridLayout(1,8));
        bottomButtonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        saveButton = new JButton("SAVE");
        saveButton.addActionListener(new ButtonListener());
        saveButton.setToolTipText("<html><h3>Click this button to Save " + 
            "your picture to a file.</h3></html>");

        loadButton = new JButton("LOAD");
        loadButton.setToolTipText("<html><h3>Click this button to Load " + 
            "a picture from a file.</h3></html>");
        loadButton.addActionListener(new ButtonListener());

        colorButton = new JButton("COLOR");
        colorButton.setToolTipText("<html><h3>Click this button to " + 
            "select a color to paint with.</h3></html>");
        colorButton.addActionListener(new ButtonListener());

        exitButton = new JButton("EXIT");
        exitButton.setToolTipText("<html><h3>Click this button to Exit " + 
            "the program.</h3></html>");
        exitButton.addActionListener(new ButtonListener());

        brushButton = new JButton("BRUSH");
        brushButton.setToolTipText("<html><h3>Select a brush style from " + 
            "Dialog box.</h3></html>");
        brushButton.addActionListener(new ButtonListener());

        clearButton = new JButton("CLEAR");
        clearButton.setToolTipText("<html><h3>Click this button to " + 
            "clear the drawing board.</h3></html>");
        clearButton.addActionListener(new ButtonListener());

        undoButton = new JButton("UNDO");
        undoButton.setToolTipText("<html><h3>Click this button to " + 
            "switch the brush to erase mode.</h3>" +
            "<h4>You can also right-click mouse to erase</h4></html>");
        undoButton.addActionListener(new ButtonListener());

        drawButton = new JButton("DRAW");
        drawButton.setToolTipText("<html><h3>Click this button to " + 
            "switch the brush to draw mode.</h3></html>");
        drawButton.addActionListener(new ButtonListener());

        bottomButtonPanel.add(saveButton);
        bottomButtonPanel.add(loadButton);
        bottomButtonPanel.add(colorButton);
        bottomButtonPanel.add(exitButton);
        bottomButtonPanel.add(brushButton);
        bottomButtonPanel.add(clearButton);
        bottomButtonPanel.add(undoButton);
        bottomButtonPanel.add(drawButton);

    }
//////////////// Inner Class for the Drawing Area /////////////////////

    class DrawingCanvas extends JPanel
    {

///////////////// Constructor for the Drawing Area ////////////////////

        public DrawingCanvas() 
        {

            setDoubleBuffered(false);

        }

        public DrawingCanvas(File file)
        {

            try
            {
                image = ImageIO.read(file);
            }   catch(IOException ex) {
                System.out.println("ERROR");
                }
            
        }
     
//////////// PaintComponent method for Drawing Canvas /////////////// 

        public void paintComponent(Graphics g) 
        {
            update(g);
 
        }
        public void update(Graphics g)
        {
            if (image == null) 
            {
                image = createImage(getSize().width, getSize().height);
                g2 = (Graphics2D) image.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                clear();
            }

            g.drawImage(image, 0, 0, null);
            g2.setPaint(tmpColor);
            colorList.add(tmpColor);

        }        


/////////// Clear method for creating a blank Drawing area ///////////

        public void clear()
        {
            g2.setPaint(Color.white);
            g2.fillRect(0, 0, getSize().width, getSize().height);
            g2.setPaint(tmpColor);
            repaint();
 
        }

// getPreferredSize method for setting size of Drawing Canvas Component // 

        public Dimension getPreferredSize() 
        {
            return new Dimension(700,500);
        }
   
    }

///// inner class for handling Mouse Events ////////////////////////////

    class MyMouseListener extends MouseAdapter
    {
        ArrayList<Integer> xClicks = new ArrayList<>();
        ArrayList<Integer> yClicks = new ArrayList<>();

        ArrayList<Integer> xClicks2 = new ArrayList<>();
        ArrayList<Integer> yClicks2 = new ArrayList<>();


//////// method for overriding the MousePressed event handler ///////////
        public void mousePressed(MouseEvent e) 
        {
            oldX = e.getX();
            oldY = e.getY();
            pointList.add(e.getPoint());
            repaint();
        }

//////// method for overriding the MouseReleased event handler ///////////

        public void mouseReleased(MouseEvent e)
        {


            if( e.getClickCount() == 1)
            {
                xClicks.add(e.getX());
                yClicks.add(e.getY());
                pointList.add(e.getPoint());


            }
            
            if( e.getClickCount() == 2)
            {
                xClicks2.add(e.getX());
                yClicks2.add(e.getY());
                pointList.add(e.getPoint());

            }


            if(lineCheck == true && e.getClickCount() == 2)
            {

                g2.drawLine(xClicks.get(0), yClicks.get(0),
                            xClicks2.get(0), yClicks2.get(0));
                repaint();
                clearClicks();
 
            }
            
            else if(rectCheck == true && e.getClickCount() == 2)
            {
                if(fillCheck == true)
                {
                    g2.fillRect(xClicks.get(0), yClicks.get(0), 
                            xClicks2.get(0)-xClicks.get(0), 
                            yClicks2.get(0) - yClicks.get(0));
                    fillCheck = false;

                }
                else
                {
                    g2.drawRect(xClicks.get(0), yClicks.get(0), 
                            xClicks2.get(0)-xClicks.get(0), 
                            yClicks2.get(0) - yClicks.get(0));
                }
                repaint();
                clearClicks();
 
            }
            else if(circleCheck == true && e.getClickCount() == 2)
            {
                if(fillCheck == true)
                {
                    g2.fillOval(xClicks.get(0), yClicks.get(0), 
                            xClicks2.get(0)-xClicks.get(0), 
                            yClicks2.get(0) - yClicks.get(0));
                    fillCheck = false;

                }
                else
                {
                    g2.drawOval(xClicks.get(0), yClicks.get(0), 
                            xClicks2.get(0)-xClicks.get(0), 
                            yClicks2.get(0) - yClicks.get(0));
                }
                repaint();
                clearClicks();
            }


        }

///////  method to clear the points in the ArrayList of clicks ////////

        public void clearClicks()
        {
            xClicks.clear();
            xClicks2.clear();
            yClicks.clear();
            yClicks2.clear();

        }


    }

///// inner class for handling Mouse Motion Events ////////////////////
 
    class MyMouseMotionListener extends MouseMotionAdapter 
    {


//////// method for overriding the MouseDragged event handler ///////////

        public void mouseDragged(MouseEvent e) 
        {
            currentX = e.getX();
            currentY = e.getY();
            pointList.add(e.getPoint());

            if (g2 != null) 
            {

                if(e.getButton() == 1)
                {
                    g2.setColor(tmpColor);
                    colorList.add(tmpColor);
                }
                else
                {
                    g2.setColor(Color.WHITE);
                    colorList.add(tmpColor);
                }

                if(check == true)
                {
                    g2.drawString(msg, oldX, oldY);  
                }
                else
                {
                    g2.drawLine(oldX, oldY, currentX, currentY);
                }

                repaint();
  
                oldX = currentX;
                oldY = currentY;

            }
        }        
    }    


////////// inner class for holding ButtonListener action events ///////

    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == saveButton)
            {
                bImage = toBufferedImage(image);
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                   "JPG Images", "jpg");
                fileChooser.setFileFilter(filter);
                fileChooser.setCurrentDirectory(new File("."));
                File saveFile = new File("savedimage.jpg");
                fileChooser.setSelectedFile(saveFile);
                int result = fileChooser.showSaveDialog(paintshop);
                if (result == JFileChooser.APPROVE_OPTION) 
                {
                    saveFile = fileChooser.getSelectedFile();
                    try 
                    {
                        ImageIO.write(bImage, "jpg", saveFile);
                    }   catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            
            else if (e.getSource() == loadButton)
            {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                   "JPG Images", "jpg");
                fileChooser.setFileFilter(filter);
                fileChooser.setCurrentDirectory(new File("."));
                int result = fileChooser.showOpenDialog(paintshop);
                if (result == JFileChooser.APPROVE_OPTION) 
                {
                    File selectedFile = fileChooser.getSelectedFile();

                    paintshop.add(new DrawingCanvas(selectedFile));
                }

            }
            else if (e.getSource() == colorButton)
            {
                Color c = JColorChooser.showDialog(null, "Choose a Color", paintshop.getBackground());
                      
                if (c != null)
                    tmpColor = c;
            }
            else if (e.getSource() == exitButton)
            {
                System.exit(0);
            }
 
            else if (e.getSource() == brushButton)
            {
                brushDialog();
            }

            else if (e.getSource() == undoButton)
            {
                tmpColor = Color.white;
                g2.setStroke( new BasicStroke(6));
 
            }
            else if (e.getSource() == drawButton)
            {
                lineCheck = false;
                rectCheck = false;
                circleCheck = false;

                g2.setStroke( new BasicStroke(1));
                tmpColor = Color.black;
            }

            else if (e.getSource() == clearButton)
            {
                paintshop.clear();
            }
            else if (e.getSource() == rectButton)
            {
                lineCheck = false;
                circleCheck = false;
                rectCheck = true;
                repaint();
            }
            else if (e.getSource() == lineButton)
            {
                rectCheck = false;
                circleCheck = false;
                lineCheck = true;
                repaint();
            }
            else if (e.getSource() == circleButton)
            {
                lineCheck = false;
                rectCheck = false;
                circleCheck = true;
                repaint();
            }
            else if (e.getSource() == fillButton)
            {
                fillCheck = true;
                repaint();
            }


        }

//// method for InputDialog Box for user to select Brush Style ///////

        public void brushDialog()
        {           
            Object[] options = {"Asterisk *",
                "Normal Line",
                "Thick Line",
                "Dash",
                "Colon",
                "Word"};

            String selection = (String) JOptionPane.showInputDialog(null,
                        "Select a style of Brush stroke",
                        "Brush Style",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

            if(selection == "Asterisk *")
            {
                msg = "*";
                check = true;
                repaint();
            }
            else if(selection == "Normal Line")
            {
                check = false;
                g2.setStroke(new BasicStroke(1));
            }
            else if(selection == "Thick Line")
            {
                check = false;
                g2.setStroke(new BasicStroke(6));
            }
            else if(selection == "Dash")
            {
                check = false;
                g2.setStroke(new BasicStroke(2, 
                           BasicStroke.CAP_SQUARE,   
                           BasicStroke.JOIN_MITER,
                           10,  
                           new float[] {16,20},
                           0));    
            }
            else if(selection == "Colon")
            {
                msg = ":";
                check = true;
                repaint();
            }

            else if(selection == "Word")
            {
                String wordBrush = JOptionPane.showInputDialog(null,
                    "What word would you like?");

                msg = wordBrush;
                check = true;
                repaint();

            }
        }

////// method for converting image to buffered image for saving ///////

        public BufferedImage toBufferedImage(Image img)
        {
            if (img instanceof BufferedImage)
            {
                return (BufferedImage) img;
            }

            BufferedImage bimage = new BufferedImage(img.getWidth(null), 
                img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();

            return bimage;
        }
    }
}
