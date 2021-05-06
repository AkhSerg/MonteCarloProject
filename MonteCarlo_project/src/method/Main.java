package method;

//http://phys.bspu.by/static/lib/inf/prg/java/iljava/glava8/index1.htm

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.Box;

class Main extends Frame {

	//	Creating automatically to solve warnings
	private static final long serialVersionUID = 1L; 				
	
	//	Initializing variables:
	private int _wImg, _hImg;	//	width and height of image;
	private Image _img;			//	image;
	private BufferedImage _bi;	//	container to place image data;
	private int xGraphics = 50;	//	x coordinate of top left corner to draw image from;
	private int yGraphics = 80;	//	y coordinate of top left corner to draw image from.
	
	Main(String title) {
		
		//	Getting title.
		super(title);
		
		//	Setting menu bar to frame.
		setMenuBar(	myMenuBar());
		
		//	Adding container which contains objects for interactive.
		add(	BoxWithComponents(),
				BorderLayout.SOUTH);
		
		//	Setting bound to frame.
		setBounds(	40, 40,
					1200, 680);
		
		//	Adding WindowListener to be able to close frame.
		addWindowListener(
				new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//	Setting icon image as specified image from String.
		setIconImage(getToolkit().getImage("Icon.png"));
		
		//	Setting frame visible.
		setVisible(true);
	}
	
	//	Specified menu bar for frame
	private MenuBar myMenuBar () {
		
		//	Creating menu bar
		MenuBar mBar =
				new MenuBar();
		
		//	Creating Objects of menu bar
		Menu mFile =
				new Menu("File");
		Menu mHelp =
				new Menu("Help");
		
		//	Creating items of menu Objects
		MenuItem open =
				new MenuItem(	"Open...",
								new MenuShortcut(KeyEvent.VK_O));
		MenuItem info =
				new MenuItem(	"Info",
								new MenuShortcut(KeyEvent.VK_H));
		
		//	Adding specified action listeners
		open.addActionListener(
				new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				
				createNewImg();
				
			}
		});
		info.addActionListener(
				new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(	getOwner(),
												"This programm gives you ability to calculate the area of any figure as pixels.\n"
												+ "To calculate you should get ready the image of figure."
												+ "Be sure that image that you load:\n"
												+ "> has black color with 100% opacity;\n"
												+ "> has no background;\n"
												+ "> has format *.bmp or *.png.\n\n\n\n\n\n"
												+ "Made by the Ural State Pedagogical University for the scientific title.\n"
												+ "Title link: \n\n"
												+ "Developer e-mail: akhaimov99@gmail.com",
												"Information message",
												
												JOptionPane.INFORMATION_MESSAGE);	
			}
		});
		
		//	Adding items to objects, objects to menu bar
		mFile.add(open);
		mFile.addSeparator();
		mHelp.add(info);
		mHelp.addSeparator();
		mBar.add(mFile);
		mBar.add(mHelp);
		
		//	Setting font style of menu
		mBar.setFont(
				new Font(	"sans-serif",
							Font.PLAIN,
							16));
		
		return mBar;
	}
	
	//	Specified box to place button and label
	private Box BoxWithComponents() {
		
		//	Creating the box, the button and the label.
		Box box = Box.createHorizontalBox();
		Button calculate; 
		Label output;
		
		//	Creating font style.
		Font font =
				new Font(	"sans-serif",
							Font.BOLD,
							24);
		//	Creating colors for text and background of components.
		Color text_color =
				new Color(0xD2691E);
		Color back_color =
				new Color(0x90EE90);
		
		//	Initializing button and setting style.
		calculate =
				new Button("Calculate");
		calculate.setFont(font);
		calculate.setForeground(text_color);
		calculate.setBackground(back_color);
		calculate.setFocusable(false);
		calculate.setMinimumSize(
				new Dimension(	220, 37));
		calculate.setMaximumSize(
				new Dimension(	300, 37));
		calculate.setCursor(
				new Cursor(Cursor.HAND_CURSOR));
		
		//	Initializing label and setting style.
		output =
				new Label("Result: ");
		output.setFont(font);
		output.setForeground(text_color);
		output.setBackground(back_color);

		//	Adding components to box.
		box.add(calculate);
		box.add(output);

		//	Adding listener to button.
		calculate.addMouseListener(
				new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				//	Initializing variables:
				int xCoord = 0, yCoord = 0;						//	coordinates of pixels;
				double hits = 0.;								//	pixels inside;
				double result = 0.;								//	result by formula;
				int N = (int) (Math.random()*(_wImg * _hImg));	//	total number of pixels;
				int north = _hImg, south = 0;					//	extremal coordinates of top and bottom; 
				int west = _wImg, east = 0;						//	extremal coordinates of left and right.
				
				output.setText("Calculating and painting...");
				
				//	For every pixel:
				for (int i = 0; i < N; i ++) {
					
					//	initializing coordinates ad random;
					xCoord = (int) (Math.random() * (_wImg));
					yCoord = (int) (Math.random() * (_hImg));
					
					//	if image exists
					if (_img != null) {
						
						/*	if the color of pixel has 100% opacity and it's black,
					  		increment hits,
					  		set color of pixel as green with 100% opacity;
					  	*/
						if (_bi.getRGB(xCoord, yCoord) == 0xff000000) {
						
							hits ++;
							_bi.setRGB(
										xCoord, yCoord,
										0xff00ff00);
							
							// find extremals;
							if (xCoord < west)
								west = xCoord;
							if (xCoord > east)
								east = xCoord;
							if (yCoord < north)
								north = yCoord;
							if (yCoord > south)
								south = yCoord;
							
						} else
							/*	else if the color of pixel has 100% opacity and it's green,
							  	increment hits,
							  	set color of pixel as black with 100% opacity;
							 */
							if (_bi.getRGB(xCoord, yCoord) == 0xff00ff00) {
								
								hits ++;
								_bi.setRGB(	xCoord, yCoord,
											0xff000000);
								
							}
						
						/*	if the color of pixel has 0% opacity,
						  	set color of pixel as red with 100% opacity;
						 */
						if (_bi.getRGB(xCoord, yCoord) == 0x00000000) {
							
							_bi.setRGB(	xCoord, yCoord,
										0xffff0000);		// устанавливаем цвет пикселя непрозрачный синий
							
						} else
							/*	else if the color of pixel has 100% opacity and it's red,
						  		set color of pixel with 0% opacity.
							 */
							if (_bi.getRGB(xCoord, yCoord) == 0xffff0000)
								_bi.setRGB(	xCoord, yCoord,
											0x00000000);
						

						
					}
					

				}
				//	Calculating area.
				result = _wImg * _hImg * hits / N;
				
				//	Repainting frame.
				repaint();
				
				//	Showing calculated information
				output.setText(
						+ N + " | " + (int) hits + " | "
						+ result + " | " 
						+ _wImg + "*" + _hImg + " | "
						+ (south - north) / 2. + "*"
						+ (east - west) / 2.  + " |"
						);					
				
				// Nullifying hits
				hits = 0;
			}
		});
		
		output.addMouseListener(
				new MouseAdapter() {
			String s;
			public void mouseEntered(MouseEvent e) {
				
				s = output.getText();
				output.setText("N; N*; result; width*height; wFig*hFig");
				
			}
			public void mouseExited(MouseEvent e) {
				
				output.setText(s);
				
			}
		});
		
		return box;
		
	}
	
	private void createNewImg () {
		
		// Creating and Opening frame to load image
		FileDialog fd =
				new FileDialog(	this,
								"Loading image",
								FileDialog.LOAD);
		fd.setVisible(true);
		
		// Getting the path folder of image file
		String file_path = fd.getDirectory() + fd.getFile();
		
		/*	If image is exists, fill the graphic context as fillRect with white color,
		 	set image as null and repaint frame.
		 */
		if (this._img != null) {
			
			this.getGraphics().setColor(	new Color(255, 255, 255));
			this.getGraphics().fillRect(	xGraphics, yGraphics,
											this._wImg, this._hImg);
			_img = null;
			repaint();
			
		}
		
		//	Getting an image by toolkit from file_path and placing at _img.
		_img = this.getToolkit().getImage(	file_path);
		try {
			//	Tracking the getting of image
			MediaTracker mt =
				new MediaTracker(this);
			mt.addImage(	_img, 0);
			mt.waitForID(	0);
			//	Getting image parameters	
			_wImg = _img.getWidth(this);
			_hImg = _img.getHeight(this);
			
		} catch (Exception exc) {}
		
		/*	
		 	If image is not null and image's width = _wImg and height = _hImg are greater 0, 
		 	creating an Object of BufferedImage that takes _wImg, _hImg and Type of color (TYPE_INT_ARGB) to place image inside.
			After that, creating the graphic context wich takes an image object,
			x coordinate and y coordinate where image will start drawing,
			and container to draw image inside.
		 */ 
		
		if (_wImg > 0 && _hImg > 0) {
			
			_bi =
				new BufferedImage(	_wImg, _hImg,
									BufferedImage.TYPE_INT_ARGB);
			Graphics2D big = _bi.createGraphics();
			big.drawImage(	_img,
							0, 0,
							this);
			
		}
		
		//	Repainting frame.
		repaint();
		
	}
	
	//	Overriding method paint of class Component
	public void paint(Graphics g) {
		
		//	Creating the graphic context to draw in.
		Graphics2D gr = (Graphics2D) g;
		//	Draw image.
		gr.drawImage(	_bi,
						xGraphics, yGraphics,
						this);
		
	}
	
//	The programm
	public static void main(String[] args) {
		//	Creating an Object of class Main with specified name from String.
		new Main("Monte-Carlo method");
	} 
		
}
