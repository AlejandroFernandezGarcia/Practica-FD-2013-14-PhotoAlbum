package es.udc.fi.dc.photoalbum.wicket.pages.nonAuth;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.springframework.dao.DataIntegrityViolationException;

import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.MD5;
import es.udc.fi.dc.photoalbum.wicket.MyAjaxButton;
import es.udc.fi.dc.photoalbum.wicket.PasswordPolicyValidator;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;

@SuppressWarnings("serial")
public class Register extends BasePage {
	@SpringBean
	private UserService userService;
	private static final int PASSWORD_MIN_LENGTH = 8;
	private FeedbackPanel feedback;

	public Register(final PageParameters parameters) {
		super(parameters);
		this.feedback = new FeedbackPanel("feedback");
		this.feedback.setOutputMarkupId(true);
		add(this.feedback);
		add(createFormRegister());
	}

	private Form<User> createFormRegister() {
		Form<User> form = new Form<User>("form",
				new CompoundPropertyModel<User>(new User())) {
			@Override
			protected void onSubmit() {
				User user = getModelObject();
				user.setEmail(user.getEmail().toLowerCase());
				user.setPassword(MD5.getHash(user.getPassword()));
				try {
					/*
					 * Mail mail = new Mail(user.getEmail());
					 * mail.sendRegister(Session.get().getLocale());
					 */
					userService.create(user);
					setResponsePage(RegistryCompleted.class);
					/*
					 * { catch (EmailException e){ error(new
					 * StringResourceModel("register.EmailError", this,
					 * null).getString());
					 */
				} catch (DataIntegrityViolationException e) {
					error(new StringResourceModel("register.alreadyExist",
							this, null).getString());
				}
			}
		};
		RequiredTextField<String> email = new RequiredTextField<String>("email");
		email.add(EmailAddressValidator.getInstance());
		email.setLabel(new StringResourceModel("register.emailField", this,
				null));
		PasswordTextField password = new PasswordTextField("password");
		password.add(StringValidator.minimumLength(PASSWORD_MIN_LENGTH));
		password.add(new PasswordPolicyValidator());
		password.setLabel(new StringResourceModel("register.passwordField",
				this, null));
		PasswordTextField passwordAgain = new PasswordTextField(
				"passwordAgain", Model.of(""));
		passwordAgain.setLabel(new StringResourceModel(
				"register.passwordRepeatField", this, null));
		form.add(new EqualPasswordInputValidator(password, passwordAgain));
		form.add(email);
		form.add(password);
		form.add(passwordAgain);
		form.add(new MyAjaxButton("ajax-button", form, this.feedback));
		return form;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(new CssResourceReference(
		        Register.class, "res/css/Register.css")));
	}
}
