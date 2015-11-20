import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;

public class CreateMembershipEdges extends AbstractCySwingApp 
{
	public CreateMembershipEdges(CySwingAppAdapter adapter)
	{
		super(adapter);
		adapter.getCySwingApplication()
                    .addAction(new MenuAction(adapter));
	}
}
