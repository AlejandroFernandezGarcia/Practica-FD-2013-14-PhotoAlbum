package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.SharedUsers;

public class TestSharedUsersPage {
	private WicketApp wicketApp;
	private WicketTester tester;

	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
				UserService mock = new UserService() {
					public void create(User user) {	}
					public void delete(User user) { }
					public void update(User user) { }
					public User getUser(String email, String password) { return null; }
					public User getUser(User userEmail) { return null; }
					public User getById(Integer id) {
						return new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
					}
				};
				AlbumShareInformationService mockShare = new AlbumShareInformationService() {
					public void create(AlbumShareInformation shareInformation) { }
					public void delete(AlbumShareInformation shareInformation) {	}
					public List<AlbumShareInformation> getShares(User userShared, User userSharedTo) { return null; }
					public AlbumShareInformation getShare(String albumName, int userSharedToId, String userSharedEmail) {
						return new AlbumShareInformation(1, new Album(1, null, null, null, null, PrivacyLevel.SHAREABLE), null); 
					}
					public ArrayList<AlbumShareInformation> getAlbumShares(
							int albumId) { return null; }
					public ArrayList<AlbumShareInformation> getUserShares(int userId) {
						return new ArrayList<AlbumShareInformation>();
					}
				};
				context.putBean("userBean", mock);
				context.putBean("shareBean", mockShare);
				getComponentInstantiationListeners().add(new SpringComponentInjector(this, context));
			}
		};
	}

	@Before
	public void setUp() {
		this.tester = new WicketTester(this.wicketApp);
		((MySession) Session.get()).setuId(1);
		this.tester.startPage(SharedUsers.class);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(SharedUsers.class);
	}
}
