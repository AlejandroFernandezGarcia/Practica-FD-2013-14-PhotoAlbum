package es.udc.fi.dc.photoalbum.wicket.pages.nonAuth;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.SignOut;

/**
 * The initial page of the aplication.
 */
@SuppressWarnings("serial")
public class BasePage extends WebPage {

    /**
     * @see UserService
     */
    @SpringBean
    private UserService userService;

    /**
     * Constructor for BasePage.
     * 
     * @param parameters
     *            The parameters of the page.
     */
    public BasePage(final PageParameters parameters) {
        super(parameters);
        add(new DebugBar("debug"));
        add(homePageLink("home").add(
                new Label("header", "Photo Album")));
        add(new Link<Void>("goEnglish") {
            public void onClick() {
                getSession().setLocale(Locale.US);
            }
        });
        add(new Link<Void>("goSpanish") {
            public void onClick() {
                getSession().setLocale(new Locale("es", "ES"));
            }
        });
        add(new BookmarkablePageLink<Void>("signout", SignOut.class,
                null) {
            @Override
            public boolean isVisible() {
                return ((MySession) Session.get()).isAuthenticated();
            }
        });
        if (((MySession) Session.get()).isAuthenticated()) {
            add(new Label("fullname", userService.getById(
                    ((MySession) Session.get()).getuId()).getEmail()));
        } else {
            add(new Label("fullname"));
        }
    }

    /**
     * Method renderHead, that allow render the Css.
     * 
     * @param response
     *            IHeaderResponse
     * 
     * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(IHeaderResponse)
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(
                        BasePage.class, "BasePage.css")));
    }
}
