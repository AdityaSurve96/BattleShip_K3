package application;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class Shiptodrag extends Rectangle{

	double mouseX,mouseY,homeX,homeY;
	

	Shiptodrag(double x,double y,double width,double height){
		super(x,y,width,height);
		
	}
	
	EventHandler<MouseEvent> keyPressedEvent = new EventHandler<MouseEvent>() {

		
		@Override
		public void handle(MouseEvent e) {
			
			double mouseX= e.getSceneX();
			double mouseY= e.getSceneY();
			
			
			
			/*if(mouseY>=450 && mouseY<=480) {
				
			}else if(mouseY>=490 && mouseY<=520) {
				
			}else if(mouseY>=530 && mouseY<=560) {
			
			}else if(mouseY>=570 && mouseY<=600) {
				
			}else if(mouseY>=610 && mouseY<=640) {
				
			}*/
			
			
		}
	};
	
	EventHandler<MouseEvent> mouseEventDragged = new EventHandler<MouseEvent>() {

		
		@Override
		public void handle(MouseEvent e) {
			
			double movementX= e.getSceneX() ;
			double movementY= e.getSceneY();  
			
			
			
		}
	};
}
