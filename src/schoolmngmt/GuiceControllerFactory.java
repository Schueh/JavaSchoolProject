package schoolmngmt;

import com.google.inject.Injector;

import javafx.util.Callback;

/** 
 * This controller factory uses the Google Guice library to inject 
 * a dependency into a controller.
 * @author ManuelSidler
 *
 */
public class GuiceControllerFactory implements Callback<Class<?>, Object> {

	private final Injector injector;
	
	public GuiceControllerFactory(Injector injector) {
		this.injector = injector;
	}
	
	@Override
	public Object call(Class<?> aClass) {
		return injector.getInstance(aClass);
	}
}
