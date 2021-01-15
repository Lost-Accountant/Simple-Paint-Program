package paint;


// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

import paint.Command.AddLineCommand;
import paint.Command.AddPointCommand;
import paint.Command.AddShapeCommand;
import paint.Command.Command;
import paint.Line.LineComponent;
import paint.Shape.*;
import paint.Shape.Rectangle;
import paint.State.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

class PaintPanel extends JPanel implements Observer, MouseMotionListener, MouseListener {
	private int i=0;
	private PaintModel model; // slight departure from MVC, because of the way painting works
	private View view; // So we can talk to our parent or other components of the view
	private State currentState; // Set different behavior based on states.
	private Command commandCreated; // create command to sent to Paint Model.
	private Configuration configuration;

	public PaintPanel(PaintModel model, View view){
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(300,300));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		this.model = model;
		this.model.addObserver(this);

		this.view = view;

		// default
		this.configuration = new Configuration(Color.BLACK, 1, false);
		this.currentState = new CircleState(configuration);
		this.commandCreated = null;
	}

	/**
	 *  View aspect of this
	 */
	public void paintComponent(Graphics g) {
		// Use g to draw on the JPanel, lookup java.awt.Graphics in
		// the javadoc to see more of what this can do for you!!

		super.paintComponent(g); //paint background
		Graphics2D g2d = (Graphics2D) g; // lets use the advanced api
		// setBackground (Color.blue);
		// Origin is at the top left of the window 50 over, 75 down
		g2d.setColor(Color.black);
		g2d.drawString ("i="+i, 50, 75);
		i=i+1;

		// Draw Points
		this.paintAllPoints(g2d);

		// Draw Lines
		this.paintAllLines(g2d);

		// Draw Circles
		this.paintAllShapes(g2d);

		g2d.dispose();
	}

	/**
	 * Draw all existing points in PaintModel as tiny circles.
	 * @param g2d
	 */
	public void paintAllPoints(Graphics2D g2d){
		ArrayList<Point> existingPoints = this.model.getPoints();
		if(!existingPoints.isEmpty()) {
			for (Point p : existingPoints) {
				int x = p.getX();
				int y = p.getY();
				// set color
				g2d.setColor(p.getConfiguration().getColor());
				// set stroke
				g2d.setStroke(new BasicStroke(p.getConfiguration().getLineThickness()));

				// draw points by drawing tiny circles
				g2d.drawOval(x, y, 1, 1);
			}
		}
	}

	/**
	 * Drw all existing shapes in PaintModel depending on specific type
	 * @param g2d
	 */
	public void paintAllShapes(Graphics2D g2d){
		ArrayList<paint.Shape.Shape> existingShapes = this.model.getShapes();
		if(!existingShapes.isEmpty()) {
			for (paint.Shape.Shape s : existingShapes) {
				Point centre = s.getCentre();

				// set color
				g2d.setColor(s.getConfiguration().getColor());
				// set line thickness
				g2d.setStroke(new BasicStroke(s.getConfiguration().getLineThickness()));

				// if shape is circle
				if (s instanceof Circle) {
					if (!s.getConfiguration().isFilled()) {
						g2d.drawOval(centre.getX() - s.getWidth() / 2, centre.getY() - s.getHeight() / 2,
								s.getWidth(), s.getHeight());
					} else {
						g2d.fillOval(centre.getX() - s.getWidth() / 2, centre.getY() - s.getHeight() / 2,
								s.getWidth(), s.getHeight());
					}
				} // same way to draw rectangle and square
				else if (s instanceof Rectangle) {
					if (!s.getConfiguration().isFilled()) {
						g2d.drawRect(centre.getX() - s.getWidth() / 2, centre.getY() - s.getHeight() / 2,
								s.getWidth(), s.getHeight());
					} else {
						g2d.fillRect(centre.getX() - s.getWidth() / 2, centre.getY() - s.getHeight() / 2,
								s.getWidth(), s.getHeight());
					}
				}
			}
		}
	}

	public void paintAllLines(Graphics2D g2d){
		ArrayList<LineComponent> existingLines = this.model.getLines();
		if (!existingLines.isEmpty()) {
			for (LineComponent l : existingLines) {
				// for each LineComponent, connect all points within each.
				ArrayList<Point> points = l.getPoints();
				for (int i = 0; i < points.size() - 1; i++) {
					Point p1 = points.get(i);
					Point p2 = points.get(i + 1);

					// set color
					g2d.setColor(l.getConfiguration().getColor());
					// set line thickness
					g2d.setStroke(new BasicStroke(l.getConfiguration().getLineThickness()));

					g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// Not exactly how MVC works, but similar.
		this.repaint(); // Schedule a call to paintComponent
	}

	/**
	 * Controller aspect of this. Change the state
	 * based on string received.
	 *
	 * @param mode
	 */
	public void setCurrentState(String mode){
		switch(mode){
			case "Circle":
				currentState = new CircleState(configuration);
				break;
			case "Rectangle":
				currentState = new RectangleState(configuration);
				break;
			case "Square":
				currentState = new SquareState(configuration);
				break;
			case "Point":
				currentState = new PointState(configuration);
				break;
			case "Squiggle":
				currentState = new SquiggleState(configuration);
				break;
			case "Line":
				currentState = new LineState(configuration);
				break;
			case "Polyline":
				currentState = new PolyLineState(configuration);
				break;
		}
		System.out.println(mode + "selected");
	}

	public Command constructCommand(){
		if (currentState.getCreation() != null) {
			if (currentState instanceof ShapeState) {
				commandCreated = new AddShapeCommand(model, ((ShapeState) currentState).getShapeCreated());
			} else if (currentState instanceof PointState) {
				commandCreated = new AddPointCommand(model, ((PointState) currentState).getPointCreated());
			} else if (currentState instanceof LineComponentState) {
				commandCreated = new AddLineCommand(model, ((LineComponentState) currentState).getLineComponentCreated());
			}
			return commandCreated;
		} else {
			return null;
		}
	}

	// MouseMotionListener below
	@Override
	public void mouseMoved(MouseEvent e) {
		// encounter 2 situations
		// Situation A: the previous mouse movement made a completed creation
		if (currentState.isCompleted()){
			// reset the state to construct new creation.
			this.resetAfterCompletion();
		} // Situation B: the previous mouse movement did not make a completed creation
		else {
			// revoke previous command to set up new command later
			// unless the previous command is for a finished product.
			if (this.commandCreated != null) {
				this.model.revokeCommand();
			}
		}
		// do action based on current state
		currentState.mouseMoved(e);

		// construct and send command even if not done.
		// Because needs live update.
		this.model.invokeCommand(this.constructCommand());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// encounter 2 situations
		// Situation A: the previous mouse movement made a completed creation
		if (currentState.isCompleted()){
			// reset the state to construct new creation.
			this.resetAfterCompletion();
		} // Situation B: the previous mouse movement did not make a completed creation
		else {
			// revoke previous command to set up new command later
			// unless the previous command is for a finished product.
			if (this.commandCreated != null) {
				this.model.revokeCommand();
			}
		}
		// do action based on current state
		currentState.mouseDragged(e);

		// construct and send command even if not done.
		// Because needs live update.
		this.model.invokeCommand(this.constructCommand());

	}

	// MouseListener below
	@Override
	public void mouseClicked(MouseEvent e) {
		// encounter 2 situations
		// Situation A: the previous mouse movement made a completed creation
		if (currentState.isCompleted()){
			// reset the state to construct new creation.
			this.resetAfterCompletion();
		} // Situation B: the previous mouse movement did not make a completed creation
		else {
			// revoke previous command to set up new command later
			// unless the previous command is for a finished product.
			if (this.commandCreated != null) {
				this.model.revokeCommand();
			}
		}

		// do action based on current state
		currentState.mouseClicked(e);

		// construct and send command even if not done.
		// Because needs live update.
		this.model.invokeCommand(this.constructCommand());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// encounter 2 situations
		// Situation A: the previous mouse movement made a completed creation
		if (currentState.isCompleted()){
			// reset the state to construct new creation.
			this.resetAfterCompletion();
		} // Situation B: the previous mouse movement did not make a completed creation
		else {
			// revoke previous command to set up new command later
			// unless the previous command is for a finished product.
			if (this.commandCreated != null) {
				this.model.revokeCommand();
			}
		}

		// do action based on current state
		currentState.mousePressed(e);

		// construct and send command even if not done.
		// Because needs live update.
		this.model.invokeCommand(this.constructCommand());

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// encounter 2 situations
		// Situation A: the previous mouse movement made a completed creation
		if (currentState.isCompleted()){
			// reset the state to construct new creation.
			this.resetAfterCompletion();
		} // Situation B: the previous mouse movement did not make a completed creation
		else {
			// revoke previous command to set up new command later
			// unless the previous command is for a finished product.
			if (this.commandCreated != null) {
				this.model.revokeCommand();
			}
		}

		// do action based on current state
		currentState.mouseReleased(e);

		// construct and send command even if not done.
		// Because needs live update.
		this.model.invokeCommand(this.constructCommand());



	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// encounter 2 situations
		// Situation A: the previous mouse movement made a completed creation
		if (currentState.isCompleted()){
			// reset the state to construct new creation.
			this.resetAfterCompletion();
		} // Situation B: the previous mouse movement did not make a completed creation
		else {
			// revoke previous command to set up new command later
			// unless the previous command is for a finished product.
			if (this.commandCreated != null) {
				this.model.revokeCommand();
			}
		}

		// do action based on current state
		currentState.mouseEntered(e);

		// construct and send command even if not done.
		// Because needs live update.
		this.model.invokeCommand(this.constructCommand());



	}

	/**
	 * Can't change the order. Otherwise, when Poly line is finished by exiting,
	 * a mouse re-enter will keep revoking the command.
	 *
	 * maybe can do a double check here. Seems to work.
	 *
	 * @param e
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// encounter 2 situations
		// Situation A: the previous mouse movement made a completed creation
		if (currentState.isCompleted()){
			// reset the state to construct new creation.
			this.resetAfterCompletion();
		} // Situation B: the previous mouse movement did not make a completed creation
		else {
			// revoke previous command to set up new command later
			// unless the previous command is for a finished product.
			if (this.commandCreated != null) {
				this.model.revokeCommand();
			}
		}

		// do action based on current state
		currentState.mouseExited(e);

		// construct and send command even if not done.
		// Because needs live update.
		this.model.invokeCommand(this.constructCommand());

		// encounter 2 situations
		// Situation A: the previous mouse movement made a completed creation
		if (currentState.isCompleted()){
			// reset the state to construct new creation.
			this.resetAfterCompletion();
		} // Situation B: the previous mouse movement did not make a completed creation
		else {
			// revoke previous command to set up new command later
			// unless the previous command is for a finished product.
			if (this.commandCreated != null) {
				this.model.revokeCommand();
			}
		}

	}

	/**
	 * Activated each time a creation is completed.
	 */
	public void resetAfterCompletion(){
		this.currentState.reset();
		this.commandCreated = null;
		System.out.println("Status reset.");
	}


	// configuration related
	// when one configuration attribute changes, make a new one
	// so that old configuration was not referenced. (Class object referenced to memory location.
	public void setColor(Color color){
		this.configuration = new Configuration(color, configuration.getLineThickness(), configuration.isFilled());
		this.currentState.setConfiguration(configuration);
	}

	public void setLineThickness(int lineThcikness){
		this.configuration = new Configuration(configuration.getColor(), lineThcikness, configuration.isFilled());
		this.currentState.setConfiguration(configuration);
	}

	public void setIsFilled(boolean isFilled){
		this.configuration = new Configuration(configuration.getColor(), configuration.getLineThickness(), isFilled);
		this.currentState.setConfiguration(configuration);
	}


	// redo and undo
	public void undo(){
		model.undoCommand();
	}

	public void redo(){
		model.invokeNextCommand();
	}

	// save and load
	public void save(){
		System.out.println("Not yet implemented.");
	}

}
