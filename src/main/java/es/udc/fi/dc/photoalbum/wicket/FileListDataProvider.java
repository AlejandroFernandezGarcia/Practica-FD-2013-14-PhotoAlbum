package es.udc.fi.dc.photoalbum.wicket;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.wicket.models.FilesModelPaging;

@SuppressWarnings("serial")
public class FileListDataProvider implements IDataProvider<File> {

    @SpringBean
    private FileService fileService;
    private int size;
    private int id;

    public void detach() {
    }

    public FileListDataProvider(int size, int id) {
        this.size = size;
        this.id = id;
        Injector.get().inject(this);
    }

    public Iterator<File> iterator(long first, long count) {
        LoadableDetachableModel<List<File>> ldm = new FilesModelPaging(
                this.id, (int) first, (int) count);
        return ldm.getObject().iterator();
    }

    public long size() {
        return this.size;
    }

    public IModel<File> model(File object) {
        final Integer id = object.getId();
        return new LoadableDetachableModel<File>() {
            @Override
            protected File load() {
                return fileService.getById(id);
            }
        };
    }
}
