package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.util.dto.FileDto;
import es.udc.fi.dc.photoalbum.webapp.restclient.RestClientSearchService;

/**
 * Model that return a list of the hottest {@link File files}.
 */
@SuppressWarnings("serial")
public class HottestFilesModel extends
        LoadableDetachableModel<List<FileDto>> {

    /**
     * Constructor for HottestFilesModel.
     */
    public HottestFilesModel() {
        Injector.get().inject(this);
    }

    /**
     * List of the hottest {@link File files}.
     * 
     * @return File list
     */
    @Override
    protected List<FileDto> load() {
        RestClientSearchService searchService = new RestClientSearchService();
        List<FileDto> list = searchService.getHottestPics();
        return list;
    }
}
