package es.udc.fi.dc.photoalbum.webapp.wicket;

import java.util.Iterator;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.FileModelForNavigate;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.FilesModel;

/**
 * Form for navigating back and forward through images.
 */
@SuppressWarnings("serial")
public class NavigateForm<T> extends Form<T> {

    /**
     * Constructor for NavigateForm.
     * 
     * @param path
     *            The id of the navigator element in the *.html.
     * @param albumId
     *            The id of the {@link Album} that contains the
     *            {@link File} with fileId
     * @param fileId
     *            The id of the current displayed {@link File}.
     * @param cls
     *            Class of page image on
     */
    public NavigateForm(String path, int albumId, int fileId,
            final Class<? extends IRequestablePage> cls) {
        this(path, albumId, fileId, PrivacyLevel.PRIVATE, cls);
    }

    /**
     * Constructor for NavigateForm.
     * 
     * @param path
     *            The id of the navigator element in the *.html.
     * @param albumId
     *            The id of the album that contains the {@link File}
     *            with fileId
     * @param fileId
     *            The id of the current displayed {@link File}.
     * @param minPrivacyLevel
     *            The privacy level of the
     * @param cls
     *            Class of page image on
     */
    public NavigateForm(String path, int albumId, int fileId,
            String minPrivacyLevel,
            final Class<? extends IRequestablePage> cls) {
        super(path);
        FilesModel ldm = new FilesModel(albumId);
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
                pars.add("album", previous.getObject().getAlbum()
                        .getName());
                pars.add("fid", previous.getObject().getId());
                setResponsePage(cls, pars);
            }
        };
        Button forward = new Button("forward") {
            @Override
            public void onSubmit() {
                PageParameters pars = new PageParameters();
                pars.add("album", next.getObject().getAlbum()
                        .getName());
                pars.add("fid", next.getObject().getId());
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
