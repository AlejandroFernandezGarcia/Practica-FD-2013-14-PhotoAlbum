package es.udc.fi.dc.photoalbum.webapp.wicket;

import java.util.Iterator;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.FileModelForNavigate;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.PublicFilesModel;

/**
 * Form for navigating back and forward through images that their
 * album has the searched tag.
 */
@SuppressWarnings("serial")
public class FilesOfAlbumTagNavigateForm<T> extends Form<T> {

    /**
     * 
     * @param path
     *            The id of the navigator element in the *.html.
     * @param albumId
     *            The id of the owner album's photos.
     * @param fileId
     *            Current displayed file id
     * @param cls
     *            Class of page image on
     * @param tag
     *            The tag that match the album with albumId
     * @param userId
     *            The user who is viewing the album.
     */
    public FilesOfAlbumTagNavigateForm(String path, final String tag,
            int albumId, int userId, int fileId,
            final Class<? extends IRequestablePage> cls) {
        super(path);
        PublicFilesModel ldm = new PublicFilesModel(albumId, userId);
        Iterator<File> iterator = ldm.getObject().iterator();
        boolean b = true;
        int i = -1;
        while (b) {
            i++;
            if (iterator.next().getId() == fileId) {
                b = false;
            }
        }
        final FileModelForNavigate previous = new FileModelForNavigate(
                -1);
        final FileModelForNavigate next = new FileModelForNavigate(-1);
        if ((i == 0) && (i + 1 == ldm.getObject().size())) {
            previous.setId(-1);
            next.setId(-1);
        } else if (i == 0) {
            next.setId(ldm.getObject().get(i + 1).getId());
            previous.setId(-1);
        } else if (i + 1 == ldm.getObject().size()) {
            previous.setId(ldm.getObject().get(i - 1).getId());
            next.setId(-1);
        } else {
            previous.setId(ldm.getObject().get(i - 1).getId());
            next.setId(ldm.getObject().get(i + 1).getId());
        }
        Button back = new Button("back") {
            @Override
            public void onSubmit() {
                PageParameters pars = new PageParameters();
                pars.add("albumId", previous.getObject().getAlbum()
                        .getId());
                pars.add("fid", previous.getObject().getId());
                pars.add("tag", tag);
                setResponsePage(cls, pars);
            }
        };
        Button forward = new Button("forward") {
            @Override
            public void onSubmit() {
                PageParameters pars = new PageParameters();
                pars.add("albumId", next.getObject().getAlbum()
                        .getId());
                pars.add("fid", next.getObject().getId());
                pars.add("tag", tag);
                setResponsePage(cls, pars);
            }
        };
        if (previous.getId() == -1) {
            back.setVisible(false);
        }
        if (next.getId() == -1) {
            forward.setVisible(false);
        }
        add(back);
        add(forward);
    }
}
