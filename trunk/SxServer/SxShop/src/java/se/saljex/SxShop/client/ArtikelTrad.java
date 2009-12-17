/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import se.saljex.SxShop.client.rpcobject.ArtGrupp;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class ArtikelTrad extends Tree {

	private ArrayList<ArtGrupp> grupper;

	final AsyncCallback callback = new AsyncCallback() {
		public void onSuccess(Object result) {
			grupper = (ArrayList)result;
			fillTree(0);
		}

		public void onFailure(Throwable caught) {
			add(new Label("Fel vid kommunikation. Kan inte hämta träd."));
		}
	};

	public ArtikelTrad(SxShopRPCAsync service) {
		service.getArtikelTrad(callback);

	}


	private void fillTree(int rootGrp) {
		// Först, hämta alla rootgrupper
		for(ArtGrupp a : grupper) {
			if (a.prevgrpid==rootGrp) {
				TreeItem item = new TreeItem(a.rubrik);
				item.setUserObject(a);
				fillTreeNode(item, a.grpid);
				item.addStyleName("sx-arttrad-grp");
				addItem(item);
			}
		}
	}
	private void fillTreeNode(TreeItem parent, int grp) {
		// Metod för att iterera genom samtliga grupper och skapa nya noder för varje
		for(ArtGrupp a : grupper) {
			if (a.prevgrpid==grp) {
				TreeItem item = new TreeItem(a.rubrik);
				item.addStyleName("sx-arttrad-grp");
				item.setUserObject(new ArtTradUserObject(a));
				fillTreeNode(item, a.grpid);
				parent.addItem(item);
			}
		}
	}

	/*
  public void adjustSize(int windowWidth, int windowHeight) {
//    int scrollWidth = windowWidth - getAbsoluteLeft() - 9;
//    if (scrollWidth < 1) {
//      scrollWidth = 1;
//    }

    int scrollHeight = windowHeight - getAbsoluteTop() - 9;
    if (scrollHeight < 1) {
      scrollHeight = 1;
    }

    setPixelSize(200, scrollHeight);
  }
*/
}
