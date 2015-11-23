import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JPanel;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import javax.swing.JLabel;

public class CreateMembershipEdges extends AbstractCySwingApp 
{
	public CreateMembershipEdges(CySwingAppAdapter adapter)
	{
		super(adapter);
		adapter.getCySwingApplication().addAction(new MenuAction(adapter));

	}
}


