package es.udc.fi.dc.photoalbum.webapp.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.Albums;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.ErrorPage404;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.Image;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.Profile;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.SignOut;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.Upload;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.pub.PublicAlbums;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.pub.PublicFiles;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.pub.PublicFilesBig;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.Share;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.SharedAlbums;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.SharedBig;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.SharedFiles;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.SharedUsers;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.BaseTags;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.FileTagBig;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.FilesOfAlbumTag;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.FilesOfAlbumTagBig;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.SearchByTag;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.nonAuth.ForgotPassword;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.nonAuth.Login;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.nonAuth.Register;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.nonAuth.RegistryCompleted;

/**
 * Class necessary to init a wicket application.
 */
public class WicketApp extends WebApplication {
    /**
     * Empty constructor of wicket application.
     */
    public WicketApp() {
    }

    /**
     * Method getHomePage.
     * 
     * @return Class<? extends Page> the homepage.
     */
    public Class<? extends Page> getHomePage() {
        return Login.class;
    }

    /**
     * Mount all the pages for the application.
     */
    @Override
    protected void init() {
        super.init();
        getComponentInstantiationListeners().add(
                new SpringComponentInjector(this));
        getSecuritySettings().setAuthorizationStrategy(
                new AuthorizationStrategy());
        mountPage("albums", Albums.class);
        mountPage("error404", ErrorPage404.class);
        mountPage("bigPic", Image.class);
        mountPage("register", Register.class);
        mountPage("registerCompleted", RegistryCompleted.class);
        mountPage("share", Share.class);
        mountPage("sharedAlbums", SharedAlbums.class);
        mountPage("sharedBig", SharedBig.class);
        mountPage("sharedFiles", SharedFiles.class);
        mountPage("sharedUsers", SharedUsers.class);
        mountPage("publicAlbums", PublicAlbums.class);
        mountPage("publicFiles", PublicFiles.class);
        mountPage("publicFilesBig", PublicFilesBig.class);
        mountPage("pics", Upload.class);
        mountPage("profile", Profile.class);
        mountPage("forgotPassword", ForgotPassword.class);
        mountPage("signOut", SignOut.class);
        mountPage("baseTags", BaseTags.class);
        mountPage("fileTagBig", FileTagBig.class);
        mountPage("filesOfAlbumTag", FilesOfAlbumTag.class);
        mountPage("filesOfAlbumTagBig", FilesOfAlbumTagBig.class);
        mountPage("searchByTag", SearchByTag.class);
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new MySession(request);
    }
}
