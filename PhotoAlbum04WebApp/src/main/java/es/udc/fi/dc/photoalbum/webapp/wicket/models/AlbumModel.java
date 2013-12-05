package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.spring.AlbumService;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;

/**
 * The model for an {@link Album}.
 */
@SuppressWarnings("serial")
public class AlbumModel extends LoadableDetachableModel<Album> {

    /**
     * @see AlbumService
     */
    @SpringBean
    private AlbumService albumService;

    /**
     * The name of the {@link Album}.
     */
    private String name;

    /**
     * Constructor for AlbumModel.
     * 
     * @param name
     *            The {@link Album}'s name.
     */
    public AlbumModel(String name) {
        this.name = name;
        Injector.get().inject(this);
    }

    /**
     * Method load.
     * 
     * 
     * @return {@link Album} Return an {@link Album} with name name.
     */
    protected Album load() {
        return this.albumService.getAlbum(name,
                ((MySession) Session.get()).getuId());
    }
}
