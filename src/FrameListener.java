import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

class FrameListener implements InternalFrameListener {
	public void internalFrameClosed(InternalFrameEvent e) {
		System.out.println(" closed");
	}
	public void internalFrameClosing(InternalFrameEvent e) {
		System.out.println(e.getInternalFrame().getTitle() +" closing");
	}
	public void internalFrameActivated(InternalFrameEvent e) {
		System.out.println(" activated");
	}
	public void internalFrameDeactivated(InternalFrameEvent e) {
			//System.out.println(e.getInternalFrame().getTitle() +" deactivated");
			//DesktopManager dm = desktop.getDesktopManager();
			//dm.closeFrame(frames[j]);
		try{
  			inFrame.setClosed(false);
		}catch(Exception e){}

	}
	public void internalFrameDeiconified(InternalFrameEvent e) {
		System.out.println(e.getInternalFrame().getTitle() +" deiconified");
	}
	public void internalFrameIconified(InternalFrameEvent e) {
		System.out.println(e.getInternalFrame().getTitle() +" iconified");
	}
	public void internalFrameOpened(InternalFrameEvent e) {
		System.out.println(e.getInternalFrame().getTitle() +" opened");
	}
}