import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
//awt allows us to create interfaces and paint graphics
import java.awt.geom.*; // for drawing shapes
import java.util.*; // for arraylist

public class Lesson48 extends JFrame
{
	
		JButton brushBut, lineBut, ellipseBut, rectBut, strokeBut, fillBut;
		
		// Going to be used to monitor what shape to draw next
		
		int currentAction = 1;
		
		// Default stroke and fill colors
//		enum (?)
		Color strokeColor=Color.BLACK, fillColor=Color.BLACK;
	
        public static void main(String [] args)
        {
                new Lesson48();
        }

        public Lesson48()
        {
        	// Define the defaults for the JFrame
        	
            this.setSize(500, 500);
            this.setTitle("Java Paint");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
//            holds buttons in the bottom of the screen
            JPanel buttonPanel = new JPanel();
            
            // Swing box that will hold all the buttons
            
            Box theBox = Box.createHorizontalBox();
            
            // Make all the buttons in makeMeButtons by passing the
            // button icon. icons show up on buttons
//           numbers represent type of shape to be drawn
            
//          ***buttonlara her tıkladığımızda aşağıdaki metodların  
//          içerisindeki addActionListener çağırılır*** 
            brushBut = makeMeButtons("./src/brush.png", 1);
            lineBut = makeMeButtons("src/line.jpg", 2);
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
            
            // Add the box of buttons to the panel
            
            buttonPanel.add(theBox);

            // Position the buttons in the bottom of the frame
//            *frame->panel->box->button->addActionListener->event handling*
            this.add(buttonPanel, BorderLayout.SOUTH);
            
            // Make the drawing area (custom component) take up the rest of the frame
//            *frame->custom component(drawingboard)->addMouseListener->event handling*
            this.add(new DrawingBoard(), BorderLayout.CENTER);
//            ***frame'in üst kısmına tıklayınca event handling aktif hale geliyor 
//         ve paint metodu da çağırılıp şekiller renkleriyle çiziliyor.
//    		DrawStuff is our custom JComponent. 
//    		When it is added to the JFrame, the paint function is called.
//    		Java calls the paint method whenever needs to repaint the draw, ***
            
            // Show the frame
            
            this.setVisible(true);
        }
        
        // Spits out buttons based on the image supplied
        // actionNum represents each shape to be drawn
        
//        the reason why int is final is because any local variable that is 
//        used inside of the inner class but not declared inside of the inner class
//        must be declared as final
        
//        this method returns of type JButton.
        public JButton makeMeButtons(String iconFile, final int actionNum){
        	JButton theBut = new JButton();
            Icon butIcon = new ImageIcon(iconFile);
            theBut.setIcon(butIcon); 
            
            // Make the proper actionPerformed method execute when the
            // specific button is pressed
            
//          action listener için inner class veya class yerine anonymous inner  
//          class kullanmak tercih edilmiş
//            (böylece event handling addActionListener()'a metod içerisinde pass edilmiş)
            theBut.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					currentAction = actionNum;
					System.out.println("actionNum: " + actionNum);
					
				}
            });
            
            return theBut; 
//        *ref var return edilince otomatik olarak bu metodu çağıranı da etkiler*
        }
        
        // Spits out buttons based on the image supplied and
        // whether a stroke or fill is to be defined
        
        public JButton makeMeColorButton(String iconFile, final int actionNum, final boolean stroke){
        	JButton theBut = new JButton();
            Icon butIcon = new ImageIcon(iconFile);
            theBut.setIcon(butIcon);
            
            theBut.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					System.out.println("actionNum: " + actionNum);
					
					if(stroke){
						
						// JColorChooser is a popup that lets you pick a color
//						default color is black for pallet
						strokeColor = JColorChooser.showDialog(null,  "Pick a Stroke", Color.BLACK);
					} else {
						fillColor = JColorChooser.showDialog(null,  "Pick a Fill", Color.BLACK);
					}
					
				}
            });
            
            return theBut;  
        }

//        ***default component: creates drawing area
//        custom component olan inner class'ın constructorı içerisinde 
//        1 mouse elementine ait 2 anonymous inner class 
//        ("new MouseAdapter() ile new MouseMotionAdapter()") ile 4 
//        event handling tanımlıyoruz
//        addMouseListener() ise event binder görevinde***
        private class DrawingBoard extends JComponent
        {
        	
        	// ArrayLists that contain each shape "drawn" along with
        	// that shapes stroke and fill
//          *we stored the values in array so they could be redrawn, 
//          saved or for redoing / undoing etc.﻿*
                ArrayList<Shape> shapes = new ArrayList<Shape>();
                ArrayList<Color> shapeFill = new ArrayList<Color>();
                ArrayList<Color> shapeStroke = new ArrayList<Color>();
                Point drawStart, drawEnd;
//                gets x and y position when clicked

                // Monitors events on the drawing area of the frame
//             	this method handles event handling
                public DrawingBoard()
                {
//                	***3 arraylist yukarıda tanımlandı. liste yerleştirilecek elemanlardan 
//                	2'si (fillColor ve strokeColor) yukarıdaki event handlerda tanımlanmıştı, 
//                	1 tanesi ise (aShape) aşağıdaki event handler'da tanımlanıyor. liste yerleştirme 
//                	işlemi event handler'da gerçekleşiyor. print ise paint metodunda oluyor.
//                	this ile drawingboard'a (jcomponent class'a) ait instance kastediliyor.
//					drawingboard (element), event ile ilişkilendiriliyor 
//                	(addMouseListener(), event binder görevinde)***
                        this.addMouseListener(new MouseAdapter()
//                        MouseAdapter is interface
//                        below methods are overridden
                          {
                            public void mousePressed(MouseEvent e)
                            {
                            	
                            	// When mouse is pressed get x & y position of mouse
                            	
                            	drawStart = new Point(e.getX(), e.getY());
                            	drawEnd = drawStart; // başlangıçta mouse tıklandığında (NO EFFECT)
                                repaint(); // NO EFFECT
                            }

//                          *mouseReleased olmasa şekil çizilmeyecekti*
                            public void mouseReleased(MouseEvent e)
                                {
                            	
                            	  // Create a shape using the starting x & y
                            	  // and finishing x & y positions
                            	 // e.getX() and e.getY() give us the position
                            	 // when mouse is released
                            	
                            	// *her çizimi dikdörtgen yapar ve altta liste eklenir*
                                  Shape aShape = drawRectangle(
                                		  drawStart.x, drawStart.y, // önceki çizilenin x ve y pozisyonunu alır
                                                      e.getX(), e.getY()); // yeni x ve y yaratır (son koordinat için)
//                                  alt: drawEnd, mouseDragged metodunda override edilmişti.
//                                  Shape aShape = drawRectangle(drawStart.x, drawStart.y,
//                        		  drawEnd.x, drawEnd.y);
                                  
                                  // Add shapes, fills and colors to there ArrayLists
//                                  ***(birbirine senkronize olur shape, fill ve stroke)***
                                  shapes.add(aShape);
                                  shapeFill.add(fillColor); 
                                  shapeStroke.add(strokeColor);
                               // event handling'de fillColor ve strokeColor oluşturulmuştu
                                  
//                               ***null yapmasaydık şekil çizildikten sonra da guide shape ortaya çıkacaktı***
                                  drawStart = null;
                                  drawEnd = null;
                                  
                                  // repaint the drawing area
//                                  (drawing occurs immediately,
//                                  mousePressed ve mouseReleased için 
//                                  sadece bu repaint metodu yeterli)
                                  repaint();
                                }
                          } ); // End of addMouseListener

//                        mouse drag edilince geçici şeklin gözükmesi için event handler
                        this.addMouseMotionListener(new MouseMotionAdapter()
                        {
                          public void mouseDragged(MouseEvent e)
                          {
                        	  
                        	// Get the final x & y position after the mouse is dragged
                        	  
                        	drawEnd = new Point(e.getX(), e.getY());
//                        	drawStart aynen devam
                            repaint();
                          }
                        } ); 
                }
                
//                this method handles setting up all different rules for drawing
                public void paint(Graphics g)
                {
                		// Class used to define the shapes to be drawn
                	
                        Graphics2D graphSettings = (Graphics2D)g;

                        // Antialiasing cleans up the jagged lines and defines rendering rules
                        
                        graphSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
                        
                        // Defines the line width of the stroke
                        
                        graphSettings.setStroke(new BasicStroke(2));

                        // Iterators created to cycle through strokes and fills
//                        alt of for each loop
                        
                        Iterator<Color> strokeCounter = shapeStroke.iterator();
                        Iterator<Color> fillCounter = shapeFill.iterator();
                        
                        // Eliminates transparent setting below
//                        for loop içerisinde olabilirdi
                        graphSettings.setComposite(AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 1.0f));
                        
//                        cycles through shapes and draws and fills them
                        for (Shape s : shapes)
                        {
                        	// Grabs the next (to be used) stroke from the color arraylist
//                        	paletten alınan stroke'un işe yaramasını sağlıyor
                        	graphSettings.setPaint(strokeCounter.next());
                        	
//                        	draws the shape
                        	graphSettings.draw(s);
                        	
                        	// Grabs the next fill from the color arraylist
//                        	paletten alınan fill'in işe yaramasını sağlıyor
                        	graphSettings.setPaint(fillCounter.next());
                        	
//                        	fills the shape
                        	graphSettings.fill(s);
                        }

                        // Guide shape used for drawing.
//                        event handler olan mouseDragged için 
//                        (yukarıdaki kısım mouse pressed ve released içindi)*
                        if (drawStart != null && drawEnd != null)
                        {
                        	// Makes the guide shape transparent
                            
                            graphSettings.setComposite(AlphaComposite.getInstance(
                                    AlphaComposite.SRC_OVER, 0.40f)); // float olduğu için f eklenmiş
                        	
                            // Make guide shape gray for professional look
                            
                        	graphSettings.setPaint(Color.LIGHT_GRAY);
//                        	yukarıda color paletten seçilmiş ve liste eklenmişti:
//                        	
//                        	Color strokeColor;
//                        	strokeColor = JColorChooser.showDialog(null,  "Pick a Stroke", Color.BLACK);
//                         shapeStroke.add(strokeColor);
//                         Iterator<Color> strokeCounter = shapeStroke.iterator();
//                        	graphSettings.setPaint(strokeCounter.next());
                        	
                        	// Create a new rectangle using existing x & y coordinates
                        	
                                Shape aShape = drawRectangle(drawStart.x, drawStart.y,
                                		drawEnd.x, drawEnd.y);
                                graphSettings.draw(aShape);
//                                yukarıda aShape liste eklenmişti:
//                                
//                                Shape aShape = drawRectangle(
//                                		  drawStart.x, drawStart.y,
//                                                      e.getX(), e.getY());
//                                
//                                  shapes.add(aShape);
//                                
//                                  for (Shape s : shapes)
//                                  {
//                                  	graphSettings.draw(s);
//                                  }
                                
//                              fill yok
                        }
                }

                private Rectangle2D.Float drawRectangle(
                        int x1, int y1, int x2, int y2)
//                x1 and y1 are initial coordinates
//                whereas x2 and y2 are final coordinates
                {
                	// Get the top left hand corner for the shape
                	// Math.min returns the points closest to 0
                	
                        int x = Math.min(x1, x2);
                        int y = Math.min(y1, y2);
                        
                        // Gets the difference between the coordinates and 
                        
                        int width = Math.abs(x1 - x2);
                        int height = Math.abs(y1 - y2);

            			// Draw rectangle by defining upper left x, y and width then height
                        return new Rectangle2D.Float(
                                x, y, width, height);
                }
                
                // The other shapes will work similarly
                // More on this in the next tutorial
                
                private Ellipse2D.Float makeEllipse(
                        int x1, int y1, int x2, int y2)
                {
                        int x = Math.min(x1, x2);
                        int y = Math.min(y1, y2);
                        int width = Math.abs(x1 - x2);
                        int height = Math.abs(y1 - y2);

                        return new Ellipse2D.Float(
                                x, y, width, height);
                }
        }
}
