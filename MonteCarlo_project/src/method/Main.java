package method;

//http://phys.bspu.by/static/lib/inf/prg/java/iljava/glava8/index1.htm

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.Box;

class Main extends Frame {

	/**	Variables
	 * 	serialVersionUID - created automatically to solve warnings
	 * 	_wImg, _hImg - width and height of image;
	 * 	_img - Image instance
	 * 	_bi - BufferedImage instance
	 * 	X_GRAPHICS - x coordinate of top left corner to draw image from
	 * 	Y_GRAPHICS - y coordinate of top left corner to draw image from
	 * 	INFO_MESSAGE - information message
	 * 	INFO_MESSAGE_TITLE - information message title
	 * 	FONT_NAME - font name of objects
	 * 	fnt_menu - Font instance for menu
	 * 	fnt_components - Font instance for components inside of box
	 * 	COLOR_HIT - the color of pixel that placed inside of figure. Better make it 0xff000000
	 * 	COLOR_AFTER_HIT - the color of pixel that placed inside of figure to redraw this pixel
	 * 	COLOR_BACKGROUND - the color of pixel that placed on the background of image
	 * 	COLOR_BACKGROUND_AFTER_HIT - the color of pixel that placed on the background of image to redraw this pixel
	 */
	
	private static final long serialVersionUID = 1L; 				
	
	private int _wImg, _hImg;
	private Image _img;
	private BufferedImage _bi;
	private static final int X_GRAPHICS = 50;
	private static final int Y_GRAPHICS = 80;
	private static final String INFO_MESSAGE =
			"This programm gives you ability to calculate the area of any figure as pixels.\n"
			+ "To calculate you should get ready the image of figure."
			+ "Be sure that image that you load:\n"
			+ "> has black color with 100% opacity;\n"
			+ "> has no background;\n"
			+ "> has format *.bmp or *.png.\n\n\n\n\n\n"
			+ "Made by the Ural State Pedagogical University for the scientific title.\n"
			+ "Title link: \n\n"
			+ "Developer e-mail: akhaimov99@gmail.com";
	private static final String INFO_MESSAGE_TITLE =
			"Information message";
	private static final String FONT_NAME = "sans-serif";
	
	private Font fnt_menu =
			new Font(FONT_NAME, Font.PLAIN, 16);
	private Font fnt_components =
			new Font(FONT_NAME, Font.BOLD, 24);
	private static final int COLOR_HIT = 0xff000000;
	private static final int COLOR_AFTER_HIT = 0xff00ff00;
	private static final int COLOR_BACKGROUND = 0x00000000;
	private static final int COLOR_BACKGROUND_AFTER_HIT = 0xffff0000;
	
	/**	The Constructor
	 * 	This method describes window features.
	 * @param title a String instance to set window name
	 */
	Main(String title) {
		
		//	Getting title.
		super(title);
		
		//	Setting menu bar to frame.
		setMenuBar(	createMyMenuBar());
		
		//	Adding container which contains objects for interactive.
		add(	createBoxWithComponents(),
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
	
	/**	Menu method
	 * 	This method returns a MenuBar instance for frame menu. 
	 * @return mBar a MenuBar instance
	 */
	private MenuBar createMyMenuBar () {
		
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
												INFO_MESSAGE,
												INFO_MESSAGE_TITLE,
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
		mBar.setFont(fnt_menu);
		
		return mBar;
	}
	
	/**	Container method
	 * This method creates a box, which contains button and label with specified style and listeners. 	
	 * @return box a Box instance.
	 */
	private Box createBoxWithComponents() {
		
		//	Creating the box, the button and the label.
		Box box = Box.createHorizontalBox();
		Button calculate; 
		Label output;
		
		//	Creating font style.
		Font font = fnt_components;
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
						if (_bi.getRGB(xCoord, yCoord) == COLOR_HIT) {
						
							hits ++;
							_bi.setRGB(
										xCoord, yCoord,
										COLOR_AFTER_HIT);
							
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
							if (_bi.getRGB(xCoord, yCoord) == COLOR_AFTER_HIT) {
								
								hits ++;
								_bi.setRGB(	xCoord, yCoord,
											COLOR_HIT);
								
							}
						
						/*	if the color of pixel has 0% opacity,
						  	set color of pixel as red with 100% opacity;
						 */
						if (_bi.getRGB(xCoord, yCoord) == COLOR_BACKGROUND) {
							
							_bi.setRGB(	xCoord, yCoord,
										COLOR_BACKGROUND_AFTER_HIT);
							
						} else
							/*	else if the color of pixel has 100% opacity and it's red,
						  		set color of pixel with 0% opacity.
							 */
							if (_bi.getRGB(xCoord, yCoord) == COLOR_BACKGROUND_AFTER_HIT)
								_bi.setRGB(	xCoord, yCoord,
											COLOR_BACKGROUND);
						

						
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
	
	/**	Create image method
	 * 	This method allows to choose what image you want to draw.
	 * 	Also, if image is already exists the method fills the rectangle of drawing area and
	 * 	draws a new chosen image. 
	 */
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
			this.getGraphics().fillRect(	X_GRAPHICS, Y_GRAPHICS,
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
	
	/**	Paint method
	 * 	Overriding method paint of class Component
	 * 	@param g a Graphic instance we will draw on
	 */
	public void paint(Graphics g) {
		
		//	Creating the graphic context to draw in.
		Graphics2D gr = (Graphics2D) g;
		//	Draw image.
		gr.drawImage(	_bi,
						X_GRAPHICS, Y_GRAPHICS,
						this);
		
	}
	
/**	The main programm
 * 	main creates a new instnace of class Main
 * 	@param args is instance of arguments of command line
 */
	public static void main(String[] args) {
		//	Creating an Object of class Main with specified name from String.
		new Main("Monte-Carlo method");
		for (int i = 0; i < args.length; i ++)
		System.out.println(args[i]);
	} 
		
}
