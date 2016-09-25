import javax.swing.*;
import javax.swing.event.ChangeEvent; // slider için
import javax.swing.event.ChangeListener; // slider için

import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.text.DecimalFormat; // for jlabel
import java.util.*;

// serials used so that we would have backwards compatibility
// whenever we are creating other classes
@SuppressWarnings("serial")
public class Lesson49 extends JFrame
{
	
		JButton brushBut, lineBut, ellipseBut, rectBut, strokeBut, fillBut;
		
		// Slider used to change the transparency
		
		JSlider transSlider;
		
		JLabel transLabel;
		
		// Makes sure the float for transparency only shows 1 digit and 2 decimals
//		event handling içerisinde trans slider value'sunun 0.01 ile çarpılması önemli
		DecimalFormat dec = new DecimalFormat("#.##");
		
		// Contains all of the rules for drawing 
		
		Graphics2D graphSettings;
		
		// Homework use graphSettings.setStroke(new BasicStroke(5F));
		// To change the stroke dynamically with a component
		
		// Going to be used to monitor what shape to draw next
		
		int currentAction = 1;
		
		// Transparency of the shape
//		şekiller float type olduğundan transparanlık da float olmalı
//		will be dynamic
		float transparentVal = 1.0f;
		
		// Default stroke and fill colors
		
		Color strokeColor=Color.BLACK, fillColor=Color.BLACK;
	
        public static void main(String [] args)
        {
                new Lesson49();
        }

        public Lesson49()
        {
        	// Define the defaults for the JFrame
        	
            this.setSize(800, 600);
            this.setTitle("Java Paint");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JPanel buttonPanel = new JPanel();
            
            // Swing box that will hold all the buttons
            
            Box theBox = Box.createHorizontalBox();
            
            // Make all the buttons in makeMeButtons by passing the
            // button icon. 
            
            brushBut = makeMeButtons("./src/brush.png", 1);
            lineBut = makeMeButtons("./src/line.jpg", 2);
            ellipseBut = makeMeButtons("./src/ellipse.png", 3);
            rectBut = makeMeButtons("./src/rectangle.png", 4);
            
            // Make all the buttons in makeMeColorButton by passing the
            // button icon and true for stroke color or false for fill
            
            strokeBut = makeMeColorButton("./src/stroke.png", 5, true);
            fillBut = makeMeColorButton("./src/fill.png", 6, false);
            
            // Add the buttons to the box
            
            theBox.add(brushBut);
            theBox.add(lineBut);
            theBox.add(ellipseBut);
            theBox.add(rectBut);
            theBox.add(strokeBut);
            theBox.add(fillBut);
            
            // Add the transparent label and slider
//            default val is 1
            transLabel = new JLabel("Transparent: 1"); // handling
            
            // Min value, Max value and starting value for slider
            
            transSlider = new JSlider(1,100, 100); // element and event
            
            // Create an instance of ListenForEvents to handle events
            
            ListenForSlider lForSlider = new ListenForSlider();
            
            // Tell Java that you want to be alerted when an event
            // occurs on the slider
            
//         ***slider (element), event ile ilişkilendiriliyor.
//         addChangeListener() binder görevinde***
            transSlider.addChangeListener(lForSlider); 

            theBox.add(transLabel);
            theBox.add(transSlider); // box layout
            
            // Add the box of buttons to the panel
            
            buttonPanel.add(theBox); // flow layout

            // Position the buttons in the bottom of the frame
//            ***Box Layout, Border Layout ve Flow Layout'ın 3'ü bir arada kullanılmış***
            this.add(buttonPanel, BorderLayout.SOUTH); // border layout
//          *frame->panel->box->button->addActionListener->event handling* // 6 button için
//          *frame->panel->box->label* // label için
//          *frame->panel->box->slider->addChangeListener->event handling* // slider için
            
            // Make the drawing area take up the rest of the frame
//    		***DrawStuff is our custom JComponent. 
//    		When it is added to the JFrame, the paint function is called.
//    		Java calls the paint method whenever needs to repaint the draw, ***
            this.add(new DrawingBoard(), BorderLayout.CENTER);
//          *frame->custom component(drawingboard)->addMouseListener->event handling*
//          ***frame'in üst kısmına tıklayınca event handling aktif hale geliyor 
//          ve paint metodu da çağırılıp şekiller renkleriyle (stroke ve fill) 
//          ve transparancy ile çiziliyor***
            
            // Show the frame
            
            this.setVisible(true);
        }
        
        // Spits out buttons based on the image supplied
        // actionNum represents each shape to be drawn
        
        public JButton makeMeButtons(String iconFile, final int actionNum){
        	JButton theBut = new JButton();
            Icon butIcon = new ImageIcon(iconFile);
            theBut.setIcon(butIcon);
            
            // Make the proper actionPerformed method execute when the
            // specific button is pressed
//          buttona tıklayınca aktif hale gelir
            theBut.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					currentAction = actionNum;
					System.out.println("actionNum: " + actionNum);
					
				}
            });
            
            return theBut;  
        }
        
        // Spits out buttons based on the image supplied and
        // whether a stroke or fill is to be defined
        
        public JButton makeMeColorButton(String iconFile, final int actionNum, final boolean stroke){
        	JButton theBut = new JButton(); // element
            Icon butIcon = new ImageIcon(iconFile);
            theBut.setIcon(butIcon);
            
//         ***theBut: element
//         addActionListener(): binder
//         new ActionListener(){}: event and handling***
            theBut.addActionListener(new ActionListener() {
//         buttona tıklayınca aktif hale gelir

				public void actionPerformed(ActionEvent e) { // event
					
					if(stroke){
						
						// JColorChooser is a popup that lets you pick a color
						
						strokeColor = JColorChooser.showDialog(null,  "Pick a Stroke", Color.BLACK); // handling
					} else {
						fillColor = JColorChooser.showDialog(null,  "Pick a Fill", Color.BLACK);
					}
					
				}
            });
            
            return theBut;  
        }

        private class DrawingBoard extends JComponent
        {
        	
        	// ArrayLists that contain each shape drawn along with
        	// that shapes stroke and fill
//         *we stored the values in array so they could be redrawn, 
//        	saved or for redoing / undoing etc.﻿*
                ArrayList<Shape> shapes = new ArrayList<Shape>();
                ArrayList<Color> shapeFill = new ArrayList<Color>();
                ArrayList<Color> shapeStroke = new ArrayList<Color>();
                ArrayList<Float> transPercent = new ArrayList<Float>();
                
                Point drawStart, drawEnd;

                // Monitors events on the drawing area of the frame
                
//            	***4 arraylist yukarıda tanımlandı. liste yerleştirilecek elemanlardan 
//            	2'si (fillColor ve strokeColor) yukarıdaki event handlerda tanımlanmıştı, 
//            	1 tanesi ise (aShape) aşağıdaki event handlerlarda tanımlanıyor. 
//             transPercent slider'ın event handler'ında tanımlanıyor.
//             list e yerleştirme işlemi aşağıdaki event handler'da gerçekleşiyor. 
//             print ise paint metodunda oluyor.***
                public DrawingBoard()
                {
//                	*this ile drawingboard'a (jcomponent class'a) ait instance kastediliyor*
//                	*new MouseAdapter(): event and handling*
//                	*drawing board da çizim yapınca addMouseListener metodu uyarılıyor*
                        this.addMouseListener(new MouseAdapter() 
                          {
                        	
                            public void mousePressed(MouseEvent e) // *event*
                            {
                            	
                            	if(currentAction != 1){ // if currentAction != brush
//                            		brush style is different from shape style for using 
//                            		individual ellipse shapes
                            	
                            	// When the mouse is pressed get x & y position
                            	
                            	drawStart = new Point(e.getX(), e.getY()); // *handling*
//                            	alternatif değil:
//                            	x1 = e.getX();
//                            	x2 = e.getY();
                            	
                            	drawEnd = drawStart; // NO EFFECT
                                repaint(); // NO EFFECT
                                
                            	}
                            	
                                
                                }
                            
//                            *mouseReleased olmasa çizilen şekil çizilmeyecekti*
                            public void mouseReleased(MouseEvent e)
                                {
                            	
                            	if(currentAction != 1){
                            	
                            	  // Create a shape using the starting x & y
                            	  // and finishing x & y positions
                            	// *aShape çizim şeklini modifiye eder ve altta liste eklenir*
                            	Shape aShape = null;
                            	
                            	if (currentAction == 2){
                            		aShape = drawLine(drawStart.x, drawStart.y,
                            				e.getX(), e.getY());
                            	} else 
                            	
                            	if (currentAction == 3){
                            		aShape = drawEllipse(drawStart.x, drawStart.y,
                            				e.getX(), e.getY());
                            		
//                            		alt:
//                            		drawEnd1 = new Point(e.getX(), e.getY());
//
//                            		aShape = drawEllipse(drawStart.x, drawStart.y,
//                            				drawEnd1.x, drawEnd1.y);
                            	} else 
                            	
                            	if (currentAction == 4) {
                            		
                            		// Create a new rectangle using x & y coordinates
                            		
                                    aShape = drawRectangle(drawStart.x, drawStart.y,
                                    		e.getX(), e.getY());
                            	}
                            	
                                  
                                  // Add shapes, fills and colors to there ArrayLists
                                  
                                  shapes.add(aShape);
                                  shapeFill.add(fillColor);
                                  shapeStroke.add(strokeColor);
                                  
                                  // Add transparency value to ArrayList
                                  
                                  transPercent.add(transparentVal);
                                  
                                  drawStart = null;
                                  drawEnd = null;
                                  
                                  // repaint the drawing area
                                  
                                  repaint();
                                  
                            	}
                                  
                                }
                          } );

                        this.addMouseMotionListener(new MouseMotionAdapter()
                        {
                        	
//                        	*this is where we create our brush*
                          public void mouseDragged(MouseEvent e)
                          {
                        	  
                        	  // If this is a brush have shapes go on the screen quickly
                        	  
                        	  if(currentAction == 1){
                      			
                      			int x = e.getX();
                      			int y = e.getY();
                      			
                      			Shape aShape = null;
//                      		field olarak yazılabilirdi DrawingBoard içerisinde
                      			
                      			// *Make stroke and fill equal to eliminate the fact that this is an ellipse*
//                      		*fillColor, strokeColor ve transparentVal diğer event handling lerden gelirken
//                      		aShape şimdiki event handling de oluşturuluyor*
                      			strokeColor = fillColor;
                      			
                      			aShape = drawBrush(x,y,5,5);
                      			
                      			shapes.add(aShape);
                                  shapeFill.add(fillColor);
                                  shapeStroke.add(strokeColor);
                                  
                                  // Add the transparency value
                                  
                                  transPercent.add(transparentVal);
                      		} 
                        	  
                        	// Get the final x & y position after the mouse is dragged
                        	  
                        	drawEnd = new Point(e.getX(), e.getY());
                            repaint();
                          }
                        } );
                }
                
//    			paint metodu component üstüne çizim yapmak için ayrılmış özel metod
//    			*It would be more correct to override the paintComponent method instead of paint.
//    			Java calls the paint method whenever needs to repaint the draw, and this method also 
//    			calls the methods: paintComponent, paintBorder and paintChildren. 
//    			So, if we overrode the paint method we would actually override the other two 
//    			and we don't need to do that.*
                public void paint(Graphics g)
                {
                		// Class used to define the shapes to be drawn
                	
                        graphSettings = (Graphics2D)g;

                        // Antialiasing cleans up the jagged lines and defines rendering rules
                        
                        graphSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
                        
                        // Defines the line width of the stroke
                        
                        graphSettings.setStroke(new BasicStroke(4));

                        // Iterators created to cycle through strokes and fills
                        Iterator<Color> strokeCounter = shapeStroke.iterator();
                        Iterator<Color> fillCounter = shapeFill.iterator();
                        
                        // Iterator for transparency
                        
                        Iterator<Float> transCounter = transPercent.iterator();
                        
                        for (Shape s : shapes)
                        {
                        	
                        	// Sets the shapes transparency value
                            
                            graphSettings.setComposite(AlphaComposite.getInstance(
                                    AlphaComposite.SRC_OVER, transCounter.next()));
                        	
                        	// Grabs the next (to be used) stroke from the color arraylist
                        	graphSettings.setPaint(strokeCounter.next());
                        	
//                        	draws the shape
                        	graphSettings.draw(s);
                        	
                        	// Grabs the next (to be used) fill from the color arraylist
                        	graphSettings.setPaint(fillCounter.next());
                        	
//                        	fills the shape
                        	graphSettings.fill(s);
                        }

//                        ***Guide shape used for drawing (2. bir çizim şekli tanımlıyoruz)
//                        brush ile ilgisi yok***
                        if (drawStart != null && drawEnd != null)
                        {
                        	// Makes the guide shape transparent
                            
                            graphSettings.setComposite(AlphaComposite.getInstance(
                                    AlphaComposite.SRC_OVER, 0.40f));
                        	
                            // Make guide shape gray for professional look
                            
                        	graphSettings.setPaint(Color.LIGHT_GRAY);
                        	
                        	Shape aShape = null;
//                  		field olarak yazılabilirdi DrawingBoard içerisinde

                        	
//                        	switch kullanılmalıydı veya başka bir metod
                        	if (currentAction == 2){
                        		aShape = drawLine(drawStart.x, drawStart.y,
                                		drawEnd.x, drawEnd.y);
                        	} else 
                        	
                        	if (currentAction == 3){
                        		aShape = drawEllipse(drawStart.x, drawStart.y,
                                		drawEnd.x, drawEnd.y);
                        	} else 
                        	
                        	if (currentAction == 4) {
                        		
                        		// Create a new rectangle using x & y coordinates
                        		
                                aShape = drawRectangle(drawStart.x, drawStart.y,
                                		drawEnd.x, drawEnd.y);
                        	}
                                
                                
                                graphSettings.draw(aShape);
                        }
                }

                private Rectangle2D.Float drawRectangle(
                        int x1, int y1, int x2, int y2)
                {
                	// Get the top left hand corner for the shape
                	// Math.min returns the points closest to 0
                	
                        int x = Math.min(x1, x2);
                        int y = Math.min(y1, y2);
                        
                        // Gets the difference between the coordinates and 
                        
                        int width = Math.abs(x1 - x2);
                        int height = Math.abs(y1 - y2);

                        return new Rectangle2D.Float(
                                x, y, width, height);
                }
                
                // The other shapes will work similarly
                // More on this in the next tutorial
                
                private Ellipse2D.Float drawEllipse(
                        int x1, int y1, int x2, int y2)
                {
                        int x = Math.min(x1, x2);
                        int y = Math.min(y1, y2);
                        int width = Math.abs(x1 - x2);
                        int height = Math.abs(y1 - y2);

                        return new Ellipse2D.Float(
                                x, y, width, height);
                }
                
                private Line2D.Float drawLine(
                        int x1, int y1, int x2, int y2)
                {

                        return new Line2D.Float(
                                x1, y1, x2, y2);
                }
                
//                brush is made of ellipse 
//                (but with different method than actual ellipse)
                private Ellipse2D.Float drawBrush(
                        int x1, int y1, int brushStrokeWidth, int brushStrokeHeight)
                {
                	
                	return new Ellipse2D.Float(
                            x1, y1, brushStrokeWidth, brushStrokeHeight);
                	
                }

        }
        
     // Implements ActionListener so it can react to events on components
        
        private class ListenForSlider implements ChangeListener{ // *event and handling*
        	
        	// *This method is called everytime the spinner is changed or touched*
        	
        	public void stateChanged(ChangeEvent e) { // *event*
        	
        		// Check if the source of the event was the button
        	
        		if(e.getSource() == transSlider){
        	
        			// Change the value for the label next to the spinner
        			// Use decimal format to make sure only 2 decimals are ever displayed
//        			transparanlığın jlabel'da dinamik okunması için
        			transLabel.setText("Transparent: " + dec.format(transSlider.getValue() * .01) ); // *handling*
        			
        			// Set the value for transparency for every shape drawn after
//        			transparanlığın çizilen şekillere etkimesi için
        			transparentVal = (float) (transSlider.getValue() * .01);
        			
        		}
        	
        	}
        	
        }
        
//        we stored the values in array so they could be redrawn, saved or for redoing / undoing etc.﻿   
        
//        In order to add undo button we could delete the last shape added to the array list. 
//        we could create another array list that holds boolean values that toggle the shape on and off. 
        
//        In order to save the drawing as a project we should just save the arraylist data to a file. 
//        Then load the array list from that file. Then you can save everything you draw.
        
//        To clear the board, we cud add a button that (from within a method) calls for the 
//        arraylist to be deleted, or at least its values
        
//        In order to change the background we could draw a square and fill it with a color
        
//        In order to implement the feature for editing the shape once we draw
//        we could create a system in which we can cycle back through each shape in the array list 
//        with the arrow keys or mouse click etc. Then as we cycle through the shapes we could highlight 
//        them in some way and then apply stroke / fill changes.
}
